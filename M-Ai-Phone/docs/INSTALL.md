# Installatie Handleiding — Ai Manus Phone

> **Transformeer je Nothing Phone 3a naar de Ai Manus Phone door Stay4Safe Ai**

---

## Inhoudsopgave

1. [Vereisten](#vereisten)
2. [Snelle installatie](#snelle-installatie)
3. [Handmatige installatie](#handmatige-installatie)
4. [Eerste gebruik](#eerste-gebruik)
5. [Problemen oplossen](#problemen-oplossen)
6. [Verwijderen](#verwijderen)

---

## Vereisten

### Telefoon
- **Model**: Nothing Phone 3a (A059)
- **OS**: Nothing OS 4.0 of hoger
- **Android**: 14 of hoger
- **Opslag**: Minimaal 500 MB vrij

### Computer
- **ADB** (Android Debug Bridge) — [download hier](https://developer.android.com/tools/releases/platform-tools)
- **USB-kabel** (data-overdracht, niet alleen opladen)
- **OS**: Windows 10+, macOS 12+, of Ubuntu 20.04+

### Telefooninstellingen
Developer Mode moet ingeschakeld zijn:
1. Ga naar **Instellingen → Over de telefoon**
2. Tik 7x op **Build-nummer**
3. Ga naar **Instellingen → Ontwikkelaarsopties**
4. Schakel **USB-foutopsporing** in

---

## Snelle installatie

```bash
# 1. Download het installatiepakket
wget https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/releases/latest/download/M-Ai-Phone-latest.zip

# 2. Pak uit
unzip M-Ai-Phone-latest.zip
cd release-package

# 3. Verbind je telefoon en voer het script uit
chmod +x install.sh
./install.sh
```

Het script doet automatisch:
- Detecteert je Nothing Phone 3a
- Installeert alle drie APKs
- Activeert Glyph debug mode
- Stelt Manus Launcher in als standaard
- Start Guardian AI service

---

## Handmatige installatie

### Stap 1: APKs downloaden

Download de laatste versie van de [Releases pagina](https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/releases):
- `guardian-app-release.apk`
- `manus-launcher-release.apk`
- `glyph-guardian-release.apk`

### Stap 2: ADB verbinding testen

```bash
adb devices
# Output: List of devices attached
#         XXXXXXXX    device
```

Als je telefoon niet verschijnt: controleer USB-kabel en USB-debugging instelling.

### Stap 3: Apps installeren

```bash
# Stay4S Guardian AI
adb install -r guardian-app-release.apk

# Manus Launcher
adb install -r manus-launcher-release.apk

# Glyph Guardian
adb install -r glyph-guardian-release.apk
```

### Stap 4: Glyph activeren

```bash
# Activeer Glyph SDK debug mode (nodig zonder officiële API key)
adb shell settings put global nt_glyph_interface_debug_enable 1
```

> **Opmerking**: Debug mode wordt automatisch na 48 uur uitgeschakeld door Nothing OS. Voer het commando opnieuw uit als de Glyph LEDs stoppen met reageren.

### Stap 5: Launcher instellen

Na installatie:
1. Druk op de **Home-knop**
2. Kies **Manus Launcher**
3. Selecteer **Altijd** om het als standaard in te stellen

Of via ADB:
```bash
adb shell cmd package set-home-activity ai.stay4safe.launcher/.ui.home.HomeActivity
```

---

## Eerste gebruik

### Guardian AI instellen

1. Open de **Stay4S Guardian** app
2. Verleen alle gevraagde machtigingen:
   - Telefoon (voor scam-detectie)
   - Locatie (voor noodgevallen)
   - Sensoren (voor valdetectie)
   - Toegankelijkheid (voor scam-detectie in oproepen)
3. Voeg **noodcontacten** toe
4. Test de **Fastbutton** door er 3 seconden op te drukken

### Glyph Guardian patronen begrijpen

| Glyph Patroon | Betekenis |
|---|---|
| C-ring langzame puls | Guardian actief — alles rustig |
| A-strip scan | Guardian scant inkomende oproep |
| A+B strips knipperend | Scam-oproep gedetecteerd! |
| C-ring SOS patroon | Val gedetecteerd — reageer! |
| Alle LEDs snel | Noodprotocol actief |

### Manus Launcher gebruiken

- **Swipe omhoog**: App drawer openen
- **Swipe links/rechts**: Tussen home-pagina's wisselen
- **Lang indrukken**: Widget/app-opties
- **Guardian widget**: Toont real-time beschermingsstatus

---

## Problemen oplossen

### Glyph LEDs reageren niet
```bash
# Reset debug mode
adb shell settings put global nt_glyph_interface_debug_enable 0
adb shell settings put global nt_glyph_interface_debug_enable 1
```

### Guardian start niet automatisch op
```bash
# Handmatig starten
adb shell am start-foreground-service -n ai.stay4safe.guardian/.service.GuardianService
```

### Manus Launcher crasht
```bash
# App data wissen
adb shell pm clear ai.stay4safe.launcher
```

### APK installatie mislukt (INSTALL_FAILED_VERIFICATION_FAILURE)
1. Ga naar **Instellingen → Beveiliging**
2. Schakel **Installatie van onbekende bronnen** in voor ADB

---

## Verwijderen

```bash
# Automatisch
./uninstall.sh

# Of handmatig
adb uninstall ai.stay4safe.guardian
adb uninstall ai.stay4safe.launcher
adb uninstall ai.stay4safe.glyph

# Glyph debug mode uitzetten
adb shell settings put global nt_glyph_interface_debug_enable 0
```

Na verwijdering is de Nothing Launcher automatisch weer actief.

---

## Technische architectuur

```
Nothing Phone 3a (Nothing OS 4.0)
├── Manus Launcher (vervangt Nothing Launcher)
│   ├── Cybernetic Noir home screen
│   ├── Guardian status widget
│   └── App drawer
├── Stay4S Guardian AI (achtergrondservice)
│   ├── Laag 1: Safety (valdetectie, Fastbutton)
│   ├── Laag 2: Security (scam calls, phishing)
│   ├── Laag 3: Privacy (sensor monitoring)
│   └── Laag 4: Support (AI assistent)
└── Glyph Guardian (LED herprogrammering)
    ├── Idle: C-ring puls
    ├── Scan: A-strip sweep
    ├── Alert: A+B knipperend
    └── Emergency: Alle LEDs SOS
```

---

*Stay4Safe Ai Telecom © 2026 — Gebouwd met ❤️ en Kotlin*
