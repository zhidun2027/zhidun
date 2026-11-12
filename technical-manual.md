# APK Security Tool 3.3.7 · Reverse-Engineering Technical Manual

> **Analysis target**: `APK-tool.exe` (UI name "APK Security Tool ver:3.3.7") and its hardened output
> **Analysis method**: exe4j unpacking → CFR to decompile the Java → dex2jar+CFR to decompile the shell dex → Ghidra to decompile the native code
> **Document role**: This file is the **master index / manual**; every conclusion is tagged with `file:line` and can be cross-checked against the directories in this repo.


## Contents

- [Part 1 · Background & Overview](#part-1)
- [Part 2 · PC-Side Hardening Tool (Java)](#part-2)
- [Part 3 · config.dll Shell Resource Pack](#part-3)
- [Part 4 · Runtime Shell (the Hardened App)](#part-4)
- [Part 5 · Encryption Algorithm Specification](#part-5)
- [Part 6 · Version Fingerprint](#part-6)
- [Part 7 · Conclusions & Security Assessment](#part-7)
- [Appendix A~E](#appendix)

---

<a id="part-1"></a>
## Part 1 · Background & Overview


| Layer | Carrier | Language | Responsibility |
|---|---|---|---|
| ① PC hardening tool | `original/apk-tool-3.3.7.jar` | Java/Swing | Unpack, encrypt the original dex, generate cls_key, re-sign |
| ② Shell resource pack | `config.dll` (encrypted ZIP) | Resources | dex/native/manifest templates for installing the shell |
| ③ Runtime shell | `libjiaguo.so` + `init` dex | native+Java | Anti-debugging, decrypt and restore the dex, swap the Application via reflection |

**All three layers share the same custom stream cipher and the same set of date keys** (see [Part 5](#part-5) and [Part 6](#part-6)).

### 1.4 Data-Flow Overview
```
[Packaging · PC]
 target APK --apktool--> extract original dex
   original dex --com.utils.b encrypt (random key)--> hidden inside the shell
   restore info (dexKey, etc.) --serialize + encrypt--> assets/cls_key
   shell (config.dll decrypted) + re-sign (apksig v1/2/3) --> hardened APK

[Runtime · phone]
 system launches hardened APK --> StubApp (shell Application) runs first
   loadLibrary("jiaguo") --> anti-debug / environment detection (exit on hit)
   read assets/cls_key --decrypt--> dexKey
   decrypt and restore original dex --> PathClassLoader injection
   RefInvoke reflection --> swap StubApp for the real Application --> original app starts transparently
```

<a id="part-2"></a>
## Part 2 · PC-Side Hardening Tool (Java)

### 2.1 Program Form
- **exe4j wrapper**: at the tail of the exe, 3 files are stored in sequence as `[8-byte length (plaintext)] + [data (whole thing XOR 0x88)]`: `exe4jlib.jar`, `i4jdel.exe`, `original/apk-tool-3.3.7.jar`. The embedded region begins at 0xaa2d4 after the PE body, with a 28-byte magic `e8 e4 13 d5` at the end.
- **Entry point**: `com.ShellMain` → main window `com.frame.a`.
- **Version number**: `com/frame/a.java:75` → `static String a = "3.3.7"`.
- **External dependencies**: `tools/apktool.jar`, `tools/zipalign.exe`; plus the built-in Google `com/android/apksig/*` signing library.

### 2.2 Complete Hardening Flow (`com/frame/SignShellUtils.java`)
1. **Unpack**: `apktool` decompiles the target APK into `source/`.
2. **Encrypt the original dex**: the key is randomized each time as `"z"+System.currentTimeMillis()+"z"`, concatenated with the version number; after encryption with `com.utils.b` it is stored as a resource inside the shell (`:403 / :409`).
3. **Generate the key table**: the restore information is placed into a HashMap → serialized + encrypted → written to `assets/cls_key` (`:499–514`).
4. **Inject the shell**: the shell loader / native code comes from `config.dll`, where `lib` is decrypted with the key `"2025_09"` (`:698–700`).
5. **Alignment**: `zipalign` performs 4-byte alignment.
6. **Re-signing**: `apksig` v1/v2/v3 all enabled, v4 disabled (`:621–632`).

### 2.3 The `assets/cls_key` Key Table
| Field | Meaning |
|---|---|
| `dexKey` | Decryption key for the original dex (random string + version number) |
| `init_pass` | Initialization password |
| `signa` | Signature check value (`com.utils.b.d()`, pure XOR) |
| `init_md5` / `playMd5` | MD5 integrity check |
| `version` | = `com.frame.a.a` = 3.3.7 |
| `sysTime` / `time` | Packaging timestamp |
| `setting` | Protection toggle configuration |
| `pckName` | Package name |

Written to `…\assets\cls_key`; the whole thing is then encrypted with another layer using the base key `"2025-03-33"` (`:513–514`).

### 2.4 Trial / Validity Mechanism ★
**No usage count, no machine code, no network check. The only restriction**: the `config.dll` file's **creation time + 540 days**.
```java
// SignShellUtils.java:298–305
l2 = FileUtils.b(config.dll);              // = BasicFileAttributes.creationTime() (FileUtils.java:110-114)
l3 = System.currentTimeMillis() - l2;
if (l3/1000/60/60/24 > 540) {              // over 540 days
    popup("config.dll configuration file has expired; please re-download"); return null;
}
```
Swapping in a fresh config.dll (whose creation time is reset) renews it.

### 2.5 Custom Encryption `com/utils/b.java`
See [Part 5](#part-5).

---

<a id="part-3"></a>
## Part 3 · config.dll Shell Resource Pack

### 3.1 Encryption & Decryption
- Generator: `ConfigPacker.java`, a default-package class in the same jar (author build path `D:\dev\project\2026\hardening\Config`).
- Encryption: `b.a(config.zip, "2025_09")` → `config.dll` (ciphertext header `5c 73 2d 2d…`).
- **Decryption**: `b("2025_09", config.dll)` (`+5 ^3 ^key`) → a valid ZIP (**1065 entries**). The output is in `config.dll.decrypted.zip`.

### 3.2 Structure
| Entry | Description |
|---|---|
| `AndroidManifest.xml` | Shell manifest: `package=com.shell`, Application=`com.youyou.shell.StubApp` |
| `init` | Shell loader dex (contains `com.youyou.shell.*`) |
| `lib/{arm64-v8a,armeabi-v7a,mips64,x86_64}/libjiaguo.so` | native hardening engine (jiaguo = hardening), already stripped |
| `smali/com/youyou/…` | The shell's own code (merged into the target APK) |
| `smali/android/support/…` | The support libraries it depends on (the majority) |

### 3.3 Shell Class List (`com.youyou.shell`)
| Class | Role |
|---|---|
| `StubApp` | The shell's Application entry point; runs before the real program |
| `AppLoad` | native bridge: `loadLibrary("jiaguo")` + native methods |
| `shell.utils.SysCheck` | System detection (root/hook/debugging/VPN/app cloning) |
| `shell.utils.AppCheck` | Application detection (proxy/VPN) + cls_key parsing |
| `shell.utils.SignatureUtils` | Signature check (anti-repackaging) |
| `shell.utils.RefInvoke` | Reflection utility (swap the Application / modify fields) |
| `shell.utils.DigestUtils` | Digest / hash |
| `utils.base64.*` | Base64 |

For the decompiled source, see `shell-contents/shell-classes-decompiled/`.

---

<a id="part-4"></a>
## Part 4 · Runtime Shell (How the Hardened App Starts)

### 4.1 StubApp Startup Flow (`smali/com/youyou/shell/StubApp.smali`)
1. `attachBaseContext(Context)`: `super` → `AppLoad.time(this, currentTimeMillis)` (time check) → `AppLoad.load(classLoader, this)` (decrypt + load the original dex).
2. `onCreate()`: `AppLoad.create(classLoader, this)` (swap in the real Application via reflection) → `super.onCreate()`.

### 4.2 Native Engine `libjiaguo.so` (Ghidra decompilation; see `libjiaguo-decompiled-partial.c`)

**4.2.1 Identity**: the original class is `com.hello.utils.FileUtils` (its JNI static exports `n001~n009`, and `decrypt`/`encrypt`, are empty stubs that `return 0`); inside `JNI_OnLoad`, `RegisterNatives` binds `create/init/load/test/time` to `com.youyou.AppLoad` — **the library has been reused and renamed**.

**4.2.2 Native encryption/decryption** `FUN_0010dbb8`: **structurally identical** to Java `com.utils.b` (`data[i]+=C1(5); ^=C2(3); ^=key[i%n]`, default key `"2025-03-33"`, with the constants initialized at runtime in .bss). Keys observed: `2025-03-33`, `com.marquee`+base, `2018-08-08com.marquee`.

**4.2.3 dex injection** (`n004`/`n007`): `RefInvoke` → `ActivityThread.currentActivityThread()` → `mPackages` → the target `LoadedApk` → create a new `PathClassLoader(/data/user/0/<pkg>/app_payload_odex/payload, lib/arm64)` → `setFieldObject(LoadedApk.mClassLoader)`.

**4.2.4 Native method mapping & backdoor**: `AppLoad.load/create/init/test/time` ← RegisterNatives; ⚠ `n003` writes the decrypted dex to `/storage/emulated/0/data.dex`, and `n007([B)` can dump to an arbitrary path → **the original dex can be recovered by unpacking**.

### 4.3 Anti-Debugging & Environment Detection (Detailed)

**Anti-debugging** (three places; triggering any one exits the process)
- native `FUN_0010a80c`: reads `TracerPid` from `/proc/self/status`; nonzero → `_exit(1)`.
- native `FUN_0010a8d4` (a watchdog thread started by JNI_OnLoad): each second it calls `gettimeofday` to check for timing drift (breakpoints/single-stepping give it away); after 5 cumulative hits → `_exit(1)`.
- Java `SysCheck.check()`: `currentTimeMillis` drift, `l-l2-2000>1000` → `System.exit(1)`.

**Root** (`SysCheck`)
- `isRootSystem1()`: whether `su` exists in `/system/bin/`, `/system/xbin/`, `/system/sbin/`, or `/sbin/`.
- `isRootSystem2()`: attempts to execute `su`.

**Hook / Xposed / Substrate** (`SysCheck`)
- `findHookAppFile()`: scans the `/proc/` maps looking for `XposedBridge.jar` and `com.saurik.substrate`.
- `findHookAppName()`: checks installed packages `de.robv.android.xposed.installer` and `com.saurik.substrate`.
- `findHookStack()`: deliberately throws an exception and checks whether the stack contains `de.robv.android.xposed.XposedBridge` or `com.saurik.substrate.MS$2`.

**Proxy** (`SysCheck.checkHook` / `AppCheck`): `System.getProperty("http.proxyHost"/"http.proxyPort"/…)`; `closeProxy` can forcibly clear the proxy.

**VPN** (`SysCheck.check06`): iterates over `NetworkInterface`; if `tun0` or `ppp0` appears → hit.

**App cloning** (`SysCheck.checkActivityThread`): whether the `getFilesDir()` path is `/data/user/0/<pkg>`, whether the parent directory `/..` is writable, plus iterating over running processes and comparing them — detecting virtual environments / clones.

**Debug switches** (`SysCheck`): `isUSBDebugSetting()` = `Settings.Secure.adb_enabled != 0`; `isOpenDevelopmentSetting()` = developer options.

**Signature check**: `SignatureUtils` + native `FUN_0010dfa4` → Java `check(String,byte[])` (key `app_k_y`), anti-repackaging.

### 4.4 Detection Item Reference Table
| Detection item | Implementation | Criterion | Consequence on hit |
|---|---|---|---|
| Debugger (ptrace) | native FUN_0010a80c | `/proc/self/status` TracerPid≠0 | `_exit(1)` |
| Debugger (timing) | native FUN_0010a8d4 / SysCheck.check | timing drift exceeds threshold | `_exit(1)`/`System.exit(1)` |
| Root | SysCheck.isRootSystem1/2 | `su` exists/executable | report/exit |
| Xposed/Substrate | SysCheck.findHook* | file/package-name/stack signatures | report/exit |
| HTTP proxy | SysCheck.checkHook / AppCheck | http.proxyHost non-empty | can closeProxy |
| VPN | SysCheck.check06 | network interface tun0/ppp0 | report |
| App cloning | SysCheck.checkActivityThread | abnormal /data/user/0 path | report |
| USB debugging | SysCheck.isUSBDebugSetting | adb_enabled≠0 | report |
| Repackaging | SignatureUtils / native check | signature mismatch | refuse to run |

---

<a id="part-5"></a>
## Part 5 · Encryption Algorithm Specification (`com/utils/b.java`)

**Type**: a homemade "repeating-key stream cipher"; symmetric, unsalted, obfuscation-grade strength. Global constants `d=3`, `e=5`; base key `a="2025-03-33"`.

| Method | Purpose | Per-byte operation (key cycles as `key[i%len]`) |
|---|---|---|
| `a(key,data)` | Encrypt | `b ^= key[i]; b ^= 3; b -= 5` |
| `b(key,data)` | Decrypt (inverse of a) | `b += 5; b ^= 3; b ^= key[i]` |
| `d(key,data)` | Pure XOR (signa) | `b ^= key[i]` |

**Pseudocode (decryption)**
```
def decrypt(key, data):
    for i in range(len(data)):
        data[i] = ((data[i] + 5) ^ 3) ^ key[i % len(key)]
    return data
```
The native-layer `FUN_0010dbb8` is an equivalent implementation (its default key is likewise `2025-03-33`).

**Key list**
| Key | Purpose |
|---|---|
| `2025-03-33` | Base key (cls_key, init_pass, etc.) |
| `2025_09` | config.dll / shell lib |
| `2026-08` | Signing-password encryption (a.java:915) |
| `2023_09` | Packer verification |
| Random `z<time>z` + version | The dex key for each packaging run |
| `com.marquee` / `2018-08-08` | Other key fragments inside the native code |

---

<a id="part-6"></a>
## Part 6 · Version Fingerprint (Identifying Which Version This Is)

| Fingerprint | Value | Location |
|---|---|---|
| Version number | `3.3.7` | com/frame/a.java:75 |
| config.dll / shell lib key | `2025_09` | SignShellUtils:700 / ConfigPacker |
| Signing-password key | `2026-08` | com/frame/a.java:915–916 |
| Packer verification key | `2023_09` | ConfigPacker.java |
| Encryption base key | `2025-03-33` | com/utils/b.java (Java + native match) |
| Validity threshold | `540` days | SignShellUtils:302 |
| Author build path | `D:\dev\project\2026\hardening` | ConfigPacker.java |
| Author username / test path | `<user>`, `…\hardened-with-errors\…` | FileUtils:119 / SignShellUtils:646 |
| native original identity / other keys | `com.hello.utils.FileUtils`, `com.marquee`, `2018-08-08` | libjiaguo.so |

> **Key points for identifying the version**: focus on comparing the three date keys `2025_09 / 2026-08 / 2023_09` against the base key `2025-03-33`; different values mean a different batch/month's build.

---

<a id="part-7"></a>
## Part 7 · Conclusions & Security Assessment

### 7.1 Strengths
- Three-layer separation (PC / config.dll / native), with the critical logic pushed down into native code.
- Fairly comprehensive anti-debugging / environment detection (ptrace + timing drift + root/hook/proxy/VPN/app cloning/adb).
- dex-extraction-style hardening + runtime ClassLoader replacement, so the original code is not directly visible statically.

### 7.2 Weaknesses / Weak Points
- **The encryption is a custom weak stream cipher** (`^key ^3 ±5`), not AES; the keys are hardcoded in the code and can be recovered directly.
- **Authorization is extremely light**: only the 540-day file validity of config.dll, with no network check / machine code / usage count — out of step with any proper licensing system.
- The native library is an **off-the-shelf component that was reused and renamed** (original identity `com.hello`), not fully in-house.

### 7.3 Feasibility of Unpacking
- The runtime shell writes the **decrypted original dex** to `/storage/emulated/0/data.dex` (the `n003` backdoor) or injects it into `app_payload_odex/payload`; once triggered, the original dex can be retrieved directly.
- Anti-debugging is only ptrace + timing, so ordinary anti-anti-debugging is enough to bypass it and then dump dynamically.

---

<a id="appendix"></a>
## Appendix

### A. Source Directory File List (``)
| File / Directory | Contents |
|---|---|
| `technical-manual.md` | **This file** (master index) |
| `site/index.html` | Technical-principles web page (dual theme, hostable) |
| `shell-internals.md` | Early text-version summary |
| `original/apk-tool-3.3.7.jar` | Main program jar (address-modified version) |
| `original/extracted/` | Main program .class files + resources |
| `decompiled-source/` | The .java for the entire main-program jar (CFR, 1127+ classes) |
| `exe4jlib.jar` / `i4jdel.exe` | The other two files embedded in the exe |
| `config.dll.decrypted.zip` | The decrypted ZIP of config.dll (shell resources) |
| `shell-contents/` | Extracted shell payload: manifest/init/libjiaguo×4/smali/shell-class java |
| `libjiaguo-decompiled-partial.c` | Ghidra-decompiled C of the native engine (24 functions) |

### B. Master Table of Keys / Constants
See the [Part 5 key list](#part-5) + the [Part 6 fingerprint table](#part-6). Constants: `d=3`, `e=5`, validity `540` days, XOR obfuscation `0x88` (exe embedded region).

### C. Key Class / Function Index
- **PC (Java)**: `com.ShellMain`, `com.frame.a`, `com.frame.SignShellUtils` (hardening core), `com.frame.r` (author "About" redirect), `com.utils.b` (encryption/decryption), `com.utils.{c,e,i,j}`, `com.utils.FileUtils`, `ConfigPacker` (config.dll packer).
- **Shell (Java)**: `com.youyou.shell.StubApp`, `com.youyou.AppLoad`, `com.youyou.shell.utils.{SysCheck,AppCheck,SignatureUtils,RefInvoke,DigestUtils}`.
- **native**: `JNI_OnLoad`, `FUN_0010dbb8` (cipher), `FUN_0010a80c` (TracerPid), `FUN_0010a8d4` (watchdog), `FUN_0010dfa4` (signature check), `n001~n009` (the FileUtils series).

### D. Glossary
| Term | Meaning |
|---|---|
| Hardening / packing | A protection technique that encrypts and hides the original APK code and restores it at runtime |
| Extraction-style hardening | Extracts the entire original dex and encrypts it, then decrypts and loads it at runtime |
| StubApp | The shell's stand-in Application; runs first and is responsible for decrypting and restoring, then swapping back to the real App |
| cls_key | The encrypted key table stored in assets |
| RegisterNatives | JNI dynamic binding of Java native methods to C functions |
| TracerPid | The debugger PID in /proc/self/status, used for anti-debugging |

### E. Reproduction Steps (How These Were Derived)
1. **jar embedded in the exe**: locate the `[len8][XOR0x88 data]` chain (3 blocks); block 3 XOR 0x88 = `original/apk-tool-3.3.7.jar`.
2. **jar → java**: `java -jar cfr.jar <jar> --outputdir decompiled-source` (the Chinese default-package classes in the root directory must be decompiled separately).
3. **config.dll → zip**: `b("2025_09", config.dll)` (byte by byte `+5 ^3 ^key`) → a valid ZIP.
4. **init dex → java**: `dex2jar init` → `cfr` decompilation.
5. **libjiaguo.so → C**: Ghidra `analyzeHeadless` import + a decompilation script to export the C.
