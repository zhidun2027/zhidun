# Third-Party Notices

zhidun itself is released under the MIT License (see [LICENSE](./LICENSE)). It
includes or depends on the third-party components listed below. Each remains
under its own license.

## Bundled in the shell payload (`config.dll` / `config.dll.decrypted.zip`)

- **Android Support Library — annotations** (`android.support.annotation.*`)
  — Apache License 2.0 (https://www.apache.org/licenses/LICENSE-2.0).
  These annotation classes are part of the runtime shell.

## Runtime dependencies (resolved at build time via Gradle; bundled into the `*-all.jar`)

- **FlatLaf** — `com.formdev:flatlaf`, `flatlaf-extras`, `flatlaf-fonts-*` — Apache License 2.0
- **apksig** — `com.android.tools.build:apksig` — Apache License 2.0
- **Conscrypt** — `org.conscrypt:conscrypt-openjdk-uber` — Apache License 2.0
- **JLayer** — `javazoom:jlayer` — LGPL-2.1. The full buildable source is
  provided, so this component can be replaced or relinked.
- **IBM Plex** fonts (used by the web pages, served from Google Fonts) —
  SIL Open Font License 1.1

## External tools invoked at runtime (not bundled, user-supplied)

- **Apktool** — Apache License 2.0
- **zipalign**, **aapt2** (Android SDK build-tools) — Apache License 2.0
