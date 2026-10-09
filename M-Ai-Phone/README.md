# 📱 M-Ai-Phone — Ai Manus Phone Build

> **Transform your Nothing Phone 3a into the ultimate Ai Manus Phone by Stay4Safe Ai**

[![Build Guardian App](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-guardian.yml/badge.svg)](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-guardian.yml)
[![Build Launcher](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-launcher.yml/badge.svg)](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-launcher.yml)
[![Build Glyph Guardian](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-glyph.yml/badge.svg)](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/actions/workflows/build-glyph.yml)

---

## 🚀 Wat is dit?

Dit project transformeert een **Nothing Phone 3a** volledig naar een **Ai Manus Phone** via software. Geen hardware-aanpassingen nodig — alles draait via Android apps, een custom launcher en Glyph-herprogrammering.

## Manus OS richting

Dit project is de basis voor **Manus OS**. De juiste volgorde is:

1. **Manus OS Layer** — APK-laag op Nothing Phone 3a met Launcher, Guardian, Glyph, Ai Chat, Messenger, Store, Payments en Manus AI Boss.
2. **Manus Managed OS** — device-owner/MDM provisioning, eigen updatekanaal, managed installs, backend, accounts en fleet/family beheer.
3. **Manus OS ROM** — pas later een echte Android-fork/custom ROM met eigen systeemapps, signing keys en OTA server.

Zie [docs/product/MANUS_OS_ROADMAP.md](docs/product/MANUS_OS_ROADMAP.md) voor het volledige stappenplan.

Het volledige masterproduct staat in [docs/product/MASTER_AI_MANUS_PHONE.md](docs/product/MASTER_AI_MANUS_PHONE.md): AI Browser, Search, Messenger, Pay, Mail, Drive, Photos, Contacts, Dialer, SMS, Notes, Vault, Family, Fleet, Agent Store en Manus Cloud.

De huidige technische grenzen zijn omgezet naar een bouwroadmap in [docs/product/MANUS_AI_LIMITS_TO_ROADMAP.md](docs/product/MANUS_AI_LIMITS_TO_ROADMAP.md).

De actuele repo-, build- en installatiestatus staat in [docs/STATUS_REPORT.md](docs/STATUS_REPORT.md).

### Wat wordt er geïnstalleerd?

| Component | Beschrijving | APK |
|---|---|---|
| **Stay4S Guardian** | AI-beschermingsapp met scam-detectie, valdetectie, Fastbutton | `guardian-app-release.apk` |
| **Manus Launcher** | Custom home screen — vervangt de Nothing Launcher | `manus-launcher-release.apk` |
| **Manus AI Glyph** | AI/Glyph statuslaag voor Guardian, Browser, Messenger, Pay en agents | `glyph-guardian-release.apk` |
| **Manus Suite** | Setup, Store, Ai Chat, Browser, Messenger, Pay en device-owner basis | `manus-suite-release.apk` |

### Manus AI Boss companion architectuur

Naast de telefoonapps kan een **Manus AI Boss** companion orchestrator draaien als controller/API-laag. Deze laag delegeert werk aan specialistische assistenten voor Guardian, Launcher, Glyph en Build/Install taken, en verzamelt status terug voor dashboards of companion-clients.

Dit is bedoeld als begeleidende besturing rond lokale Android-services, ADB-installatie en expliciete API-integraties. Het belooft geen zelfstandige cloud-autonomie: acties blijven gekoppeld aan gebruikerstoestemming, lokale permissies en controleerbare uitvoer.

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
# Alles bouwen en naar releases/ kopieren
./scripts/build-release.sh

# Guardian App bouwen
cd guardian-app && ./gradlew assembleRelease

# Manus Launcher bouwen  
cd manus-launcher && ./gradlew assembleRelease

# Glyph Guardian bouwen
cd glyph-guardian && ./gradlew assembleRelease

# Manus Suite bouwen
cd manus-suite && gradle :app:assembleRelease
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
│   ├── README.md                   ← Documentatie-index
│   ├── INSTALL.md                  ← Installatie handleiding
│   ├── BUILD.md                    ← Build instructies
│   ├── ARCHITECTURE.md             ← Technische architectuur
│   ├── product/                    ← Product en Manus OS scope
│   ├── ops/                        ← Signing, provisioning, release
│   └── strategy/                   ← Commercie en OS-hunter strategie
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
- **Rapportage**: Zie [docs/README.md](docs/README.md)
