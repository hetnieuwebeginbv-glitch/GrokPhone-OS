# 📱 M-Ai-Phone — Ai Manus Phone Build

> **Transform your Nothing Phone 3a into the ultimate Ai Manus Phone by Stay4Safe Ai**

[![Build Guardian App](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-guardian.yml/badge.svg)](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-guardian.yml)
[![Build Launcher](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-launcher.yml/badge.svg)](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-launcher.yml)
[![Build Glyph Guardian](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-glyph.yml/badge.svg)](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-glyph.yml)

---

## 🚀 Wat is dit?

Dit project transformeert een **Nothing Phone 3a** volledig naar een **Ai Manus Phone** via software. Geen hardware-aanpassingen nodig — alles draait via Android apps, een custom launcher en Glyph-herprogrammering.

### Wat wordt er geïnstalleerd?

| Component | Beschrijving | APK |
|---|---|---|
| **Stay4S Guardian** | AI-beschermingsapp met scam-detectie, valdetectie, Fastbutton | `guardian-app-release.apk` |
| **Manus Launcher** | Custom home screen — vervangt de Nothing Launcher | `manus-launcher-release.apk` |
| **Glyph Guardian** | Herprogrammeert de Glyph LEDs als Guardian AI status-indicator | `glyph-guardian-release.apk` |

---

## 📋 Vereisten

- Nothing Phone 3a (model A059) met **Nothing OS 4.0** of hoger
- **ADB** geïnstalleerd op je computer ([download](https://developer.android.com/tools/releases/platform-tools))
- USB-kabel (data-overdracht)
- **Developer Mode** ingeschakeld op de telefoon

---

## ⚡ Snelle installatie (3 stappen)

```bash
# 1. Clone de repo
git clone https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone.git
cd M-Ai-Phone

# 2. Verbind je Nothing Phone 3a via USB en voer uit:
./scripts/install.sh

# 3. Klaar — je telefoon is nu een Ai Manus Phone!
```

---

## 🛠️ Handmatige installatie

### Stap 1: Developer Mode inschakelen
1. Ga naar **Instellingen → Over de telefoon → Build-nummer**
2. Tik 7x op **Build-nummer**
3. Ga naar **Instellingen → Ontwikkelaarsopties**
4. Schakel **USB-foutopsporing** in

### Stap 2: APKs installeren via ADB
```bash
# Guardian AI app
adb install -r releases/guardian-app-release.apk

# Manus Launcher
adb install -r releases/manus-launcher-release.apk

# Glyph Guardian
adb install -r releases/glyph-guardian-release.apk
```

### Stap 3: Launcher instellen
```bash
# Stel Manus Launcher in als standaard
adb shell cmd package set-home-activity ai.stay4safe.launcher/.ui.home.HomeActivity
```

### Stap 4: Glyph Guardian activeren
```bash
# Activeer Glyph debug mode (vereist voor SDK)
adb shell settings put global nt_glyph_interface_debug_enable 1

# Start Glyph Guardian service
adb shell am start -n ai.stay4safe.glyph/.service.GlyphGuardianService
```

---

## 🏗️ Zelf bouwen

### Vereisten
- Android Studio Hedgehog (2023.1.1) of nieuwer
- JDK 17
- Android SDK 35 (Android 15)
- Kotlin 1.9+

### Build commando's
```bash
# Guardian App bouwen
cd guardian-app && ./gradlew assembleRelease

# Manus Launcher bouwen  
cd manus-launcher && ./gradlew assembleRelease

# Glyph Guardian bouwen
cd glyph-guardian && ./gradlew assembleRelease
```

---

## 🤖 GitHub Actions — Automatische Build

Elke push naar `main` triggert automatisch:
1. **Build** alle drie APKs
2. **Sign** de APKs met de release keystore
3. **Upload** als GitHub Release artifacts
4. **Notificeer** via webhook

Download de laatste builds via: [Releases](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/releases)

---

## 📁 Project Structuur

```
M-Ai-Phone/
├── .github/
│   └── workflows/
│       ├── build-guardian.yml      ← CI voor Guardian App
│       ├── build-launcher.yml      ← CI voor Manus Launcher
│       ├── build-glyph.yml         ← CI voor Glyph Guardian
│       └── release.yml             ← Automatische release pipeline
├── guardian-app/                   ← Stay4S Guardian AI app (Kotlin)
│   └── app/src/main/java/ai/stay4safe/guardian/
│       ├── ui/                     ← Jetpack Compose UI
│       ├── service/                ← Background AI service
│       ├── glyph/                  ← Glyph SDK integratie
│       ├── ai/                     ← Guardian AI engine
│       └── model/                  ← Data models
├── manus-launcher/                 ← Custom Android Launcher (Kotlin)
│   └── app/src/main/java/ai/stay4safe/launcher/
│       ├── ui/home/                ← Home screen UI
│       ├── ui/widgets/             ← Custom widgets
│       └── service/                ← Launcher services
├── glyph-guardian/                 ← Glyph LED herprogrammering
│   └── app/src/main/java/ai/stay4safe/glyph/
│       ├── service/                ← Glyph background service
│       └── patterns/               ← Guardian LED patronen
├── docs/                           ← Documentatie
│   ├── INSTALL.md                  ← Installatie handleiding
│   ├── BUILD.md                    ← Build instructies
│   └── ARCHITECTURE.md             ← Technische architectuur
├── scripts/
│   ├── install.sh                  ← Automatisch installatiescript
│   └── uninstall.sh                ← Verwijder script
└── assets/
    ├── wallpapers/                 ← Ai Manus Phone wallpapers
    ├── boot-animation/             ← Custom boot animatie
    └── icons/                      ← Stay4Safe Ai iconen
```

---

## 🔒 Licentie

Apache 2.0 — Stay4Safe Ai © 2026

---

## 🌐 Links

- **Website**: [aimanusphone-nkuvn9sn.manus.space](https://aimanusphone-nkuvn9sn.manus.space)
- **Stay4Safe Ai**: Stay4Safe Ai Telecom
- **Rapportage**: Zie `docs/rapportage.md`
