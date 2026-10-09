# Build Handleiding — Ai Manus Phone

> **Complete gids voor het bouwen van Guardian App, Manus Launcher en Glyph Guardian**

---

## Inhoudsopgave

1. [Build Vereisten](#build-vereisten)
2. [Ontwikkelaarsomgeving instellen](#ontwikkelaarsomgeving-instellen)
3. [Zelf bouwen — Snelle start](#zelf-bouwen--snelle-start)
4. [Gedetailleerde build instructies](#gedetailleerde-build-instructies)
5. [Release APKs signing](#release-apks-signing)
6. [GitHub Actions setup](#github-actions-setup)
7. [Build troubleshooting](#build-troubleshooting)

---

## Build Vereisten

### Minimale vereisten

| Component | Versie | Download |
|---|---|---|
| **JDK** | 17 LTS | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17) of [OpenJDK](https://openjdk.java.net/) |
| **Android SDK** | API 35 (Android 15) | Android Studio |
| **Gradle** | 8.9+ | Automatisch via wrapper |
| **Kotlin** | 1.9+ | Via Gradle |
| **Android Studio** | Hedgehog (2023.1.1)+ | [Downloaden](https://developer.android.com/studio) |

### Systeem vereisten

| OS | RAM | Disk | Nota |
|---|---|---|---|
| **Windows** | 8 GB min, 16 GB recommended | 20 GB | Windows 10+ |
| **macOS** | 8 GB min, 16 GB recommended | 20 GB | macOS 12+ (Intel/Apple Silicon) |
| **Linux** | 8 GB min, 16 GB recommended | 20 GB | Ubuntu 20.04+, Fedora 34+ |

---

## Ontwikkelaarsomgeving instellen

### Stap 1: JDK 17 installeren

**Windows:**
```bash
# Download Oracle JDK 17 of gebruik Chocolatey
choco install openjdk17

# Verificatie
java -version
```

**macOS:**
```bash
# Via Homebrew
brew install openjdk@17

# Link naar /usr/local/opt
sudo ln -sfn /usr/local/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk

# Verificatie
java -version
```

### Stap 2: Android Studio installeren

1. Download van [developer.android.com/studio](https://developer.android.com/studio)
2. Voer installer uit
3. Volg setup wizard
4. Bij "SDK Components Setup" → selecteer:
   - Android SDK (API 35)
   - Android SDK Platform-Tools
   - Android Emulator (optioneel)

### Stap 3: Environment variables instellen

**Windows (PowerShell als Admin):**
```powershell
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-17.0.x", "User")
[Environment]::SetEnvironmentVariable("ANDROID_HOME", "$env:USERPROFILE\AppData\Local\Android\Sdk", "User")
$env:Path += ";$env:ANDROID_HOME\platform-tools"
```

**macOS/Linux:**
```bash
# In ~/.zshrc of ~/.bash_profile
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
```

### Stap 4: Verificatie

```bash
# JDK check
java -version
# Output: openjdk version "17.0.x"

# Android SDK check
adb --version
# Output: Android Debug Bridge version ...

# Gradle check (in project directory)
./gradlew --version
# Output: Gradle 8.9+
```

---

## Zelf bouwen — Snelle start

### Option A: Alle modules in één keer (Release)

```bash
# 1. Clone repository
git clone https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone.git
cd M-Ai-Phone

# 2. Build alles
./gradlew clean assembleRelease -p guardian-app
./gradlew clean assembleRelease -p manus-launcher
./gradlew clean assembleRelease -p glyph-guardian

# 3. APKs bevinden zich in:
# guardian-app/app/build/outputs/apk/release/app-release.apk
# manus-launcher/app/build/outputs/apk/release/app-release.apk
# glyph-guardian/app/build/outputs/apk/release/app-release.apk
```

### Option B: Debug builds (sneller, voor testen)

```bash
# Guardian App
cd guardian-app && ./gradlew clean assembleDebug

# Manus Launcher
cd manus-launcher && ./gradlew clean assembleDebug

# Glyph Guardian
cd glyph-guardian && ./gradlew clean assembleDebug

# Installeer direct op telefoon
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## Gedetailleerde build instructies

### Guardian App bouwen

```bash
cd guardian-app

# Option 1: Debug build (snel)
./gradlew clean assembleDebug

# Option 2: Release build (production)
./gradlew clean assembleRelease

# Option 3: Met lokale module signing
./gradlew assembleRelease \
  -Pandroid.injected.signing.store.file=$(pwd)/../keystore.jks \
  -Pandroid.injected.signing.store.password=$KEYSTORE_PASSWORD \
  -Pandroid.injected.signing.key.alias=$KEY_ALIAS \
  -Pandroid.injected.signing.key.password=$KEY_PASSWORD

# Build output:
# Debug:   app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk
```

**Build flags:**
```bash
--no-daemon          # Daemon proces uitzetten (sneller)
--stacktrace         # Gedetailleerde error logs
--debug              # Debug logging
-x                   # Stop op eerste error
```

**Voorbeeld met flags:**
```bash
./gradlew clean assembleRelease --no-daemon --stacktrace
```

### Manus Launcher bouwen

```bash
cd manus-launcher

# Debug
./gradlew clean assembleDebug

# Release
./gradlew clean assembleRelease

# Test
./gradlew test --no-daemon

# Code quality
./gradlew lint

# Build output:
# app/build/outputs/apk/release/app-release.apk
```

### Glyph Guardian bouwen

```bash
cd glyph-guardian

# **BELANGRIJK**: Glyph SDK stub moet aanwezig zijn!
# GitHub Actions creert dit automatisch, lokaal:
mkdir -p libs
# Plaats GlyphSDK.aar in glyph-guardian/libs/

# Debug
./gradlew clean assembleDebug

# Release
./gradlew clean assembleRelease

# Build output:
# app/build/outputs/apk/release/app-release.apk
```

**Stub GlyphSDK.aar maken (voor CI/local development):**
```bash
# Maak een minimale stub
mkdir -p /tmp/glyph-stub/res
cat > /tmp/glyph-stub/AndroidManifest.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<manifest package="com.nothing.ketchum"/>
EOF

cd /tmp/glyph-stub
zip -r GlyphSDK.aar AndroidManifest.xml res/
cp GlyphSDK.aar /path/to/glyph-guardian/libs/
```

---

## Release APKs signing

### Voorbereiding: Keystore aanmaken

```bash
# Eenmalig keystore aanmaken (production key)
keytool -genkey -v \
  -keystore M-Ai-Phone.keystore \
  -keyalg RSA \
  -keysize 4096 \
  -validity 10000 \
  -alias m-ai-phone-key

# Vragen die gesteld worden:
# Keystore password: [STERK WACHTWOORD]
# Key password: [STERK WACHTWOORD]
# CN (Common Name): Stay4Safe Ai
# OU (Org Unit): Mobile Security
# O (Organization): Stay4Safe Ai Telecom
# L (Locality): Amsterdam
# ST (State): Netherlands
# C (Country): NL

# Resultaat: M-Ai-Phone.keystore (24 KB)
```

### Signing configuratie in gradle

**guardian-app/build.gradle.kts:**
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE_FILE") ?: "keystore.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### Environment variables instellen

**Linux/macOS (.bashrc / .zshrc):**
```bash
export KEYSTORE_FILE="/path/to/M-Ai-Phone.keystore"
export KEYSTORE_PASSWORD="your-strong-password"
export KEY_ALIAS="m-ai-phone-key"
export KEY_PASSWORD="your-strong-password"
```

**Windows (PowerShell):**
```powershell
$env:KEYSTORE_FILE = "C:\path\to\M-Ai-Phone.keystore"
$env:KEYSTORE_PASSWORD = "your-strong-password"
$env:KEY_ALIAS = "m-ai-phone-key"
$env:KEY_PASSWORD = "your-strong-password"
```

### Build met signing

```bash
# Release build automatisch signed (env vars nodig)
./gradlew clean assembleRelease

# Verificatie: APK signatureverifiëren
jarsigner -verify -verbose guardian-app/app/build/outputs/apk/release/app-release.apk

# Output:
# s = signature was verified
# - Certificate chain length: 1
# - Certificate is self-signed
```

---

## GitHub Actions setup

### Secrets toevoegen (voor automatische builds)

1. Ga naar **GitHub repo → Settings → Secrets and variables → Actions**
2. Voeg toe:

| Secret naam | Waarde |
|---|---|
| `KEYSTORE_FILE` | Base64 gecodeerde keystore (zie onder) |
| `KEYSTORE_PASSWORD` | Keystore wachtwoord |
| `KEY_ALIAS` | `m-ai-phone-key` |
| `KEY_PASSWORD` | Key wachtwoord |

### Keystore als Base64 Secret

```bash
# Maak base64 versie aan
base64 -i M-Ai-Phone.keystore -o keystore.b64

# Kopieëer inhoud naar GitHub Secret KEYSTORE_FILE
cat keystore.b64 | xclip -selection clipboard
```

### GitHub Actions workflow

De workflows zijn al ingesteld in `.github/workflows/`:

- **build-guardian.yml** — Bouwt Guardian App bij push naar main/develop
- **build-launcher.yml** — Bouwt Manus Launcher
- **build-glyph.yml** — Bouwt Glyph Guardian (met SDK stub)
- **release.yml** — Triggered op `v*.*.*` tag → publiceert alle APKs

**Workflow starten:**
```bash
# Automatisch: push code naar main/develop
git push origin main

# Release: maak tag aan
git tag v1.0.0
git push origin v1.0.0
```

---

## Build troubleshooting

### Error: "SDK location not found"

```bash
# Fix: SDK path instellen
echo "sdk.dir=/Users/you/Library/Android/Sdk" > local.properties
# OF
export ANDROID_HOME="/path/to/android/sdk"
```

### Error: "GlyphSDK.aar not found"

```bash
# Glyph Guardian has privé SDK — create stub voor lokale builds
mkdir -p glyph-guardian/libs

# Download stub of genereer:
python3 - << 'EOF'
import zipfile
import os

os.makedirs("/tmp/glyph-stub/res", exist_ok=True)
with open("/tmp/glyph-stub/AndroidManifest.xml", "w") as f:
    f.write('<?xml version="1.0" encoding="utf-8"?>\n<manifest package="com.nothing.ketchum"/>')

with zipfile.ZipFile("/tmp/GlyphSDK.aar", "w") as z:
    z.write("/tmp/glyph-stub/AndroidManifest.xml")

import shutil
shutil.copy("/tmp/GlyphSDK.aar", "glyph-guardian/libs/")
EOF
```

### Error: "Gradle out of memory"

```bash
# Verhoog heap size in gradle.properties
echo "org.gradle.jvmargs=-Xmx4096m" >> gradle.properties
```

### Error: "Unsupported class-file format"

```bash
# Verkeerde JDK versie — check:
java -version

# Fix: set JAVA_HOME
export JAVA_HOME=/usr/libexec/java_home -v 17
```

### APK verification mislukt

```bash
# Verificeer signing
jarsigner -verify -certs -verbose app-release.apk

# Test op device
adb install -r app-release.apk

# Check logs
adb logcat | grep -i "install"
```

### Build hangt of is traag

```bash
# Daemon uitzetten (beter voor CI)
./gradlew assembleRelease --no-daemon

# Parallel builds
./gradlew assembleRelease --parallel

# Profiling
./gradlew assembleRelease --profile
# Zie: build/reports/profile/
```

---

## Build Output Locaties

```
M-Ai-Phone/
├── guardian-app/
│   └── app/build/outputs/apk/
│       ├── debug/app-debug.apk
│       └── release/app-release.apk (SIGNED)
├── manus-launcher/
│   └── app/build/outputs/apk/
│       ├── debug/app-debug.apk
│       └── release/app-release.apk (SIGNED)
└── glyph-guardian/
    └── app/build/outputs/apk/
        ├── debug/app-debug.apk
        └── release/app-release.apk (SIGNED)
```

---

## Best Practices

✅ **DO:**
- Clean build voor production releases: `./gradlew clean assembleRelease`
- Verificeer APK signing: `jarsigner -verify -certs`
- Test op echte device (Nothing Phone 3a)
- Use `--no-daemon` in CI/CD pipelines
- Commit `gradle-wrapper.jar` voor reproducible builds

❌ **DON'T:**
- Commit keystore naar repository
- Hardcode keystore wachtwoorden in code
- Gebruik debug signing voor release builds
- Skip Gradle cache in CI
- Merge zonder build en unit tests

---

*Stay4Safe Ai Telecom © 2026 — Gebouwd met ❤️ en Kotlin*
