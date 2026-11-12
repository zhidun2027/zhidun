# APK Hardening Tool 3.3.7 · Compilable and Runnable App

Rebuilds the lost decompiled-source (CFR output, not compilable) into a **complete, Gradle-buildable, runnable** Java project.

- Entry main class: `com.ShellMain` (UI title "APK Security Tool ver:3.3.7")
- Build: Gradle 8.11.1 + JDK 21
- Status: **All 63 in-house source files compile successfully; it can be packaged into a single-file runnable jar, and on startup it loads every class and executes through to main-window creation** (showing the UI requires a full JDK/desktop environment on the machine)

---

## 1. How to Build / Run

```bash
# Enter the project directory
cd app

# 1) Compile
./gradlew compileJava

# 2) Run directly (requires a desktop/display environment)
./gradlew run

# 3) Package into a single-file runnable jar (includes all dependencies such as FlatLaf/apksig, ~10MB)
./gradlew fatJar
java -jar build/libs/apk-jiagu-3.3.7-3.3.7-all.jar

# 4) Generate a distribution package with launch scripts (Windows uses .bat)
./gradlew installDist
#   Output: build/install/apk-jiagu-3.3.7/bin/apk-jiagu-3.3.7(.bat)
```

> On Windows, double-click `build/install/apk-jiagu-3.3.7/bin/apk-jiagu-3.3.7.bat` to launch (requires JDK/JRE 21 installed on the machine).

---

## 2. What the Rebuild Did (Why It Can Go From "Not Compilable" to "Runnable")

The original `decompiled-source/` is the output of CFR 0.152: **readable but not compilable** — variable-slot reuse causes type confusion, control flow fails to structure around `** GOTO`, `throws` clauses are dropped, obfuscation produces class/package name collisions, and so on. Key rebuild steps:

| Treatment | Description |
|---|---|
| **Swap third-party libraries back to official dependencies** | The full FlatLaf 3.8 suite, apksig 8.7.3 (Google Maven), conscrypt, JLayer — rather than rebuilding these libraries, delete the decompiled copies and switch to Gradle dependencies |
| **Delete dead code** | `bin/zip` (a repackaged commons-compress, referenced nowhere in the project) is removed entirely |
| **Switch to a stronger decompiler** | Where the in-house code had type/control-flow confusion, re-decompile and replace with **Vineflower** (`--remove-synthetic=0` keeps inner-class accessors) |
| **Resolve obfuscation naming collisions** | Class `com.frame.a` and package `com.frame.a` share a name (legal in bytecode, illegal in Java source) → the audio package is renamed `com.frame.audio` |
| **Restore throws declarations** | Decompilation dropped the `throws` of almost every method; restore them layer by layer up the call chain, bottom-up (I/O→IOException, encryption/process/XML→Exception) |
| **Replace obfuscated JLayer with the official one** | Swap the embedded obfuscated `javazoom.jl.player.g` for the official JLayer `Player`, updating the 3 call sites in `com.frame.audio` |
| **Restore try-with-resources** | Vineflower broke try-with-resources into incomplete Throwable-suppression blocks; restore them (`com.utils.e`) |

> The decompiled output of the third-party apksig contains many lambdas/gotos that CFR cannot structure, so it is **impossible to restore from decompilation**; the official jar is used instead. The native engine `libjiaguo.so` is the same (Ghidra's pseudo-C is not compilable) — the PC hardening tool merely embeds the ready-made .so into the target APK, so reusing the binary is sufficient.

---

## 3. Project Structure

```
app/
├── build.gradle          # Dependencies + fatJar/run tasks
├── settings.gradle
├── gradlew / gradle/      # Gradle 8.11.1 wrapper
└── src/main/java/
    ├── com/ShellMain.java        # Entry point (FlatLaf init → new com.frame.a)
    ├── com/frame/                # Main UI + core hardening workflow
    │   ├── a.java                #   Main window (JFrame)
    │   ├── SignShellUtils.java   #   ★Core hardening workflow
    │   ├── b.java..y.java        #   Various event handlers / sub-logic (inner classes)
    │   └── audio/                #   Startup sound effects (formerly the com.frame.a package, renamed)
    ├── com/utils/                # Utilities: b(encryption) e(certificates) i(process) j(zip/signing) ApkUtilTool …
    ├── com/android/apksigner/    # apksigner CLI wrapper (compilation depends on the official apksig jar)
    ├── bin/arsc, bin/io          # resources.arsc parsing + custom binary read/write
    └── sun/                      # Base64 encode/decode (reimplements sun.misc)
```

Dependencies (see build.gradle): FlatLaf 3.8 + fonts, `com.android.tools.build:apksig:8.7.3`, conscrypt, JLayer 1.0.1.

---

## 4. Full Hardening: Verified End-to-End ✅

This project not only compiles and launches — **the entire hardening pipeline has been verified end-to-end on this machine and produces a valid hardened package** (see `runtime/samples/` and `runtime/README.md`):

```
apktool d (decompile) → encrypt original DEX → generate cls_key → extract config.dll shell contents (StubApp dex + libjiaguo.so ×4 ABIs)
  → rewrite Manifest to com.youyou.shell.StubApp → apktool b (repackage) → zipalign → apksig re-sign (v1/v2/v3)
```

Verified output contents: `classes.dex` (shell entry StubApp) + `lib/{4 ABIs}/libjiaguo.so` + `assets/<random>` (encrypted original DEX) + `assets/cls_key` + v1/v2/v3 signatures (apksigner verification passes).

**Runtime resources** (`runtime/` is fully prepared):
- **config.dll**: generated by re-encrypting `../config.dll.decrypted.zip` with the key `2025_09` (the decryption round-trip has been verified). The check is against the file creation time + 540 days, so a freshly created file stays valid long-term.
- **External tools**: place apktool + zipalign under `tools/` (Windows: `apktool.bat`/`zipalign.exe`; Linux: `apktool`/`zipalign`), with aapt2 for `--use-aapt2`.
- **Signing keystore**: use your own; the test keystore password is conventionally `111111`.

### Cross-Platform Adaptation (Windows Left As-Is, Linux Support Added)
To make local testing easier, the external-command layer was adapted per OS, with **Windows behavior unchanged**:
- `com.utils.i`: on Windows it uses `cmd.exe /c` + `apktool.bat`/`zipalign.exe`; on other platforms it uses `sh -c` + `apktool`/`zipalign`, and inherits the environment via ProcessBuilder (keeping the UTF-8 locale to avoid garbled Chinese paths).
- Path separators are unified to `/` (Java `File` works on both platforms); config.dll entry names (Windows `\`) are normalized to `/` during extraction.

---

## 5. Known Notes

- The `throws` on some methods is a `throws Exception` added along the call chain during the rebuild (functionally correct, slightly broader than the original declaration).
- Variable names are decompilation artifacts (`var0/var1`, single letters); the logic is complete but readability is limited. A further deobfuscation/renaming pass can be done separately if needed.
- This sandbox is a headless JDK (no `libawt_xawt.so`), so it can only verify up to "class loading + executing through to window creation"; for UI rendering, run on a desktop with a full JDK installed.
