#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
build-config-dll.py — reproduce / verify config.dll with no magic.

config.dll is NOT a mystery blob: it is simply the ZIP `config.dll.decrypted.zip`
(whose contents are also extracted under `shell-contents/`) run through
zhidun's stream cipher with the key "2025_09".

    config.dll = encrypt("2025_09", config.dll.decrypted.zip)

The cipher is trivial and shown in full below (per byte):
    encrypt:  x = ((x ^ key[i % len]) ^ 3) - 5
    decrypt:  x = ((x + 5) ^ 3) ^ key[i % len]
This mirrors com/utils/b.java (d=3, e=5) in the Java source.

Usage:
    python3 build-config-dll.py build    # config.dll.decrypted.zip -> config.dll
    python3 build-config-dll.py verify   # decrypt config.dll, must equal the ZIP
    python3 build-config-dll.py roundtrip # self-check the cipher, no files needed
"""
import sys, os

KEY = b"2025_09"
ZIP = "config.dll.decrypted.zip"
DLL = "config.dll"


def encrypt(data: bytes) -> bytes:
    k, n, out = KEY, len(KEY), bytearray(data)
    for i in range(len(out)):
        x = out[i]
        x = (x ^ k[i % n]) & 0xFF
        x = (x ^ 3) & 0xFF
        x = (x - 5) & 0xFF
        out[i] = x
    return bytes(out)


def decrypt(data: bytes) -> bytes:
    k, n, out = KEY, len(KEY), bytearray(data)
    for i in range(len(out)):
        x = out[i]
        x = (x + 5) & 0xFF
        x = (x ^ 3) & 0xFF
        x = (x ^ k[i % n]) & 0xFF
        out[i] = x
    return bytes(out)


def main():
    here = os.path.dirname(os.path.abspath(__file__))
    mode = sys.argv[1] if len(sys.argv) > 1 else "build"

    if mode == "roundtrip":
        import os as _os
        sample = _os.urandom(5000)
        ok = decrypt(encrypt(sample)) == sample
        print("cipher round-trip:", "OK" if ok else "FAILED")
        sys.exit(0 if ok else 1)

    zip_path = os.path.join(here, ZIP)
    dll_path = os.path.join(here, DLL)

    if mode == "build":
        with open(zip_path, "rb") as f:
            data = f.read()
        with open(dll_path, "wb") as f:
            f.write(encrypt(data))
        print("built %s  (%d bytes)  from %s" % (DLL, os.path.getsize(dll_path), ZIP))
        print("verify it decrypts back to a valid ZIP:  python3 %s verify" % os.path.basename(__file__))

    elif mode == "verify":
        with open(dll_path, "rb") as f:
            back = decrypt(f.read())
        with open(zip_path, "rb") as f:
            orig = f.read()
        is_zip = back[:4] == b"PK\x03\x04"
        same = back == orig
        print("decrypted config.dll starts with PK\\x03\\x04 (valid ZIP):", is_zip)
        print("decrypted config.dll == %s:" % ZIP, same)
        sys.exit(0 if (is_zip and same) else 1)

    else:
        print(__doc__)
        sys.exit(2)


if __name__ == "__main__":
    main()
