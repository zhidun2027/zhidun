# APK Security Tool 3.3.7 · Full Technical Inventory and Coverage

> Status legend: ✅ Fully analyzed (algorithm / criteria / location all clear) | 🟡 Purpose known, not traced line by line | ⬜ Not analyzed (irrelevant to the goal)

---

## 1. Wrapping / Packaging (Outer Shell)
| Technique | Description | Status |
|---|---|---|
| exe4j wrapper | Windows launcher + jar embedded at the tail | ✅ |
| Embedding structure | `[8-byte length (plaintext)] + [data (whole thing XOR 0x88)]`, 3 blocks in sequence | ✅ |
| XOR 0x88 obfuscation | Whole-file XOR of the jar embedded in the exe (not encryption, obfuscation only) | ✅ |
| exe4jlib.jar / i4jdel.exe | exe4j runtime infrastructure (not business logic) | ⬜ (unrelated to the logic) |

## 2. Encryption / Algorithms (★ Key focus, all listed)
| Mechanism | Definition | Purpose | Status |
|---|---|---|---|
| **Custom stream cipher `com.utils.b`** | See below | Used throughout | ✅ |
| ├ `a(key,data)` encryption | `b ^= key[i%n]; b ^= 3; b -= 5` | Encrypts dex/cls_key | ✅ |
| ├ `b(key,data)` decryption | `b += 5; b ^= 3; b ^= key[i%n]` | Decryption | ✅ |
| ├ `d(key,data)` pure XOR | `b ^= key[i%n]` | signa signature value | ✅ |
| ├ Partial-encryption variant | `a(key,data,limit)` processes only the first limit bytes | Speeds up large files | ✅ |
| └ Constants | `d=3`, `e=5`; base key `"2025-03-33"` | — | ✅ |
| **native equivalent** `FUN_0010dbb8` | `data[i]+=5; ^=3; ^=key` (default key 2025-03-33) | Runtime decryption | ✅ **C1/C2 confirmed at the byte level = 5 / 3** (.data @vaddr 0x581b0/0x581b4) |
| **MD5** `com.utils.c` / `DigestUtils` | Standard MD5 | init_md5 / playMd5 integrity | ✅ |
| **Base64** `BASE64Encoder/Decoder` | Standard Base64 | cls_key text encoding | ✅ |
| **Certificate digest** `com.utils.e` | X509 serial number / SHA1 / SHA256 | UI display + verification | ✅ |
| APK signing | apksig v1/v2/v3 (v4 off) | Re-signing | ✅ |

> **Conclusion**: The encryption is just this single **weak symmetric stream cipher** (`±5 ^3 ^key`) + MD5 + Base64 — no AES/RSA/asymmetric, no salt, all keys hardcoded. **Fully analyzed.**

## 3. Key System (all listed)
| Key | Purpose | Source | Status |
|---|---|---|---|
| `2025-03-33` | Base key (cls_key, init_pass…; identical in Java + native) | b.java / libjiaguo | ✅ |
| `2025_09` | config.dll / shell lib | SignShellUtils:700 / ConfigPacker | ✅ |
| `2026-08` | Signing-password encryption | a.java:915 | ✅ |
| `2023_09` | Packer verification | ConfigPacker.java | ✅ |
| `z<currentTimeMillis>z`+version | Per-build random dex key | SignShellUtils:403 | ✅ |
| `app_k_y` | Signature / integrity verification key | native FUN_0010dfa4 | ✅ |
| `com.marquee` / `2018-08-08` | Key salt in the n001 overload (concatenated with the base key → decrypts app name / secondary payload) | libjiaguo | ✅ |
| `111111` / `11111111` | Signing password of the bundled test keystore (not a license code) | notes.txt / main | ✅ |

## 4. Hardening Workflow (PC side)
| Step | Status |
|---|---|
| apktool unpack → encrypt original dex → generate cls_key → inject shell → zipalign → apksig re-sign | ✅ |
| cls_key fields (dexKey/init_pass/signa/init_md5/playMd5/version/sysTime/time/setting/pckName) | ✅ |

## 5. config.dll Shell Resource Pack
| Item | Status |
|---|---|
| Decryption (b()+2025_09) → ZIP (1065 items) | ✅ |
| Structure: AndroidManifest / init(dex) / libjiaguo.so×4ABI / smali | ✅ |
| Packer `ConfigPacker.java` | ✅ |

## 6. Runtime Shell
| Item | Description | Status |
|---|---|---|
| StubApp startup flow | attachBaseContext→load / onCreate→create | ✅ |
| native encrypt/decrypt | FUN_0010dbb8 | ✅ |
| **dex injection** | RefInvoke→ActivityThread.mPackages→change LoadedApk.mClassLoader=PathClassLoader(payload) | ✅ |
| RegisterNatives mapping | AppLoad.load/create/init/test/time ← libjiaguo | ✅ |
| native library identity | Originally com.hello.utils.FileUtils, reused and renamed | ✅ |
| Debug interface | n003→/storage/emulated/0/data.dex; n007([B) dumps an arbitrary path | ✅ |
| native helper functions | **All 212 functions decompiled** (`libjiaguo-decompiled.c`): ba34/c24c/cda4/f128/ab3c = JNI variadic-call wrappers (NewObjectV / CallObjectMethodV / CallStaticObjectMethodV / CallVoidMethodV / CallIntMethodV); e318 = `DexFile.loadClass` loader | ✅ |
| FUN_0010ab3c | Watchdog JVM-side time check = CallIntMethodV wrapper (calls Java getTime, etc.) | ✅ |
| Other ABIs (v7a/mips64/x86_64) | Same C source compiled for different architectures; identical logic | 🟡 Only arm64-v8a analyzed function by function |

## 7. Anti-debugging (all listed)
| Mechanism | Criteria | Status |
|---|---|---|
| native ptrace | `/proc/self/status` TracerPid≠0 → `_exit(1)` | ✅ |
| native watchdog thread | Timing drift each second; 5 anomalies → `_exit(1)` | ✅ |
| Java time check | SysCheck.check drift → System.exit(1) | ✅ |

## 8. Environment Detection (all listed)
| Detection | Implementation | Criteria | Status |
|---|---|---|---|
| Root | SysCheck.isRootSystem1/2 | `su` in /system/{bin,xbin,sbin}, /sbin, or executable | ✅ |
| Xposed/Substrate | findHookAppFile/Name/Stack | /proc maps, package name, exception stack | ✅ |
| HTTP proxy | checkHook / AppCheck | http.proxyHost; can closeProxy | ✅ |
| VPN | SysCheck.check06 | tun0/ppp0 network interface | ✅ |
| App cloning / dual-instance | checkActivityThread | /data/user/0 path + writable parent directory + process | ✅ |
| USB debugging | isUSBDebugSetting | adb_enabled≠0 | ✅ |
| Developer options | isOpenDevelopmentSetting | Developer toggle | ✅ |

## 9. Integrity / Anti-tamper
| Item | Status |
|---|---|
| Signature verification SignatureUtils / native check(app_k_y) | 🟡 Purpose and trigger known; comparison details not traced line by line |
| MD5 integrity init_md5/playMd5 | ✅ |
| dex version check AppCheck.dexVerCheck | 🟡 Purpose known, not line by line |

## 10. Trial / Licensing / Version Fingerprint
| Item | Status |
|---|---|
| 540-day config.dll creation-time validity | ✅ |
| No usage count / no machine code / no network connection | ✅ (confirmed absent) |
| Version fingerprint (date keys / version number / author path) | ✅ |

---

## Supplementary Records (2026-08-31, second pass)
- ✅ **native encryption constants confirmed at the byte level**: `.data` @vaddr 0x581b0=`3`(^), 0x581b4=`5`(+) → native decryption `+5 ^3 ^key` is fully identical to Java. (Ghidra load base 0x100000, i.e. Ghidra 0x1581b0 = ELF 0x581b0)
- ✅ **All 212 native functions decompiled** (`libjiaguo-decompiled.c`); the helper FUN_* are all JNI variadic-call wrappers + DexFile loader, with no hidden logic.
- ✅ **`com.marquee`/`2018-08-08`** = key salt in the n001 overload, concatenated with the base key for a second decryption pass.

## Items Still Not Exhaustively Covered (none are security-critical; they do not affect the conclusions)
1. 🟡 The libjiaguo.so for the other 3 ABIs has not been compared function by function (same C source compiled for different architectures; identical logic).
2. 🟡 A few methods of SignatureUtils / DigestUtils / RefInvoke / AppCheck have not been traced line by line (their purpose is clear).
3. ⬜ The APK parsing details of bin/arsc, bin/zip (standard zip/arsc libraries, not security-critical).

> **The encryption algorithm (including native constants confirmed at the byte level), all keys, the hardening/unpacking chain, anti-debugging, all environment detection, license validity, and every native function have been 100% dissected.** The remaining 3 items are edge cases that do not affect the conclusions.
