# Technische Architectuur — Ai Manus Phone

## Overzicht

De Ai Manus Phone is fase 1 van Manus OS. De eerste versie is een **Manus OS Layer** bovenop Nothing OS op een standaard Nothing Phone 3a. Latere fases kunnen doorgroeien naar **Manus Managed OS** en daarna eventueel een echte **Manus OS ROM**.

Zie [product/MANUS_OS_ROADMAP.md](product/MANUS_OS_ROADMAP.md) voor het volledige stappenplan.

De huidige software bestaat uit drie lagen die samenwerken op een standaard Nothing Phone 3a:

```
┌─────────────────────────────────────────────────────────┐
│                    GEBRUIKERSLAAG                        │
│  Manus Launcher  │  Guardian UI  │  Glyph Indicators    │
├─────────────────────────────────────────────────────────┤
│                  APPLICATIELAAG                          │
│  GuardianService │ ScamDefender  │ FallDetection        │
│  PrivacyMonitor  │ PersonaEngine │ AuditLog             │
├─────────────────────────────────────────────────────────┤
│                  HARDWARE LAAG                           │
│  Nothing OS 4.0  │ Glyph SDK     │ Sensoren             │
│  Snapdragon 7s   │ Telefonie     │ Accelerometer        │
└─────────────────────────────────────────────────────────┘
```

## Component Beschrijvingen

### Manus OS Layer
- **Type**: APK/device-owner-ready softwarelaag bovenop Nothing OS
- **Doel**: De telefoon laten voelen als een eigen Ai Manus Phone zonder root of custom ROM
- **Huidige modules**: Manus Launcher, Stay4S Guardian, Glyph Guardian
- **Volgende modules**: Setup/Settings, Store/Updater, Ai Chat, Messenger, Payments
- **Grenzen**: Geen volledige vervanging van kernel, modem, OTA, system Settings of privileged installer zonder OEM/ROM-track

### Manus Launcher
- **Type**: Android Launcher (HOME activity)
- **Taal**: Kotlin + Jetpack Compose
- **Design**: Cybernetic Noir (zwart + teal glassmorphism)
- **Functies**: Custom home screen, app drawer, Guardian widget, klok

### Manus AI Boss Companion Orchestrator
- **Type**: Companion controller/API-laag naast de telefoonapps
- **Rol**: Coördineert taken tussen de telefooncomponenten en specialistische assistenten
- **Scope**: Geeft opdrachten, verzamelt status en bewaakt beleidsregels; voert geen autonome cloud-acties uit buiten expliciete API-integraties of gebruikersopdrachten
- **Delegatie**:
  - Guardian Assistant: veiligheid, privacy-signalen, risico-uitleg
  - Launcher Assistant: home screen intenties, widgets, snelle acties
  - Glyph Assistant: LED-status, waarschuwingen, feedbackpatronen
  - Build/Install Assistant: build-artifacts, ADB-installatie, versiecontrole
- **Interfaces**: Lokale intents/services op Android, ADB/install tooling tijdens setup, en optionele controller-API voor dashboard- of companion-clients

### Stay4S Guardian AI
- **Type**: Foreground Service (altijd actief)
- **Taal**: Kotlin + Coroutines
- **Architectuur**: MVVM + Hilt DI + Room
- **Vier lagen**:
  - Safety: Valdetectie via accelerometer (threshold: 2 m/s² vrije val + 25 m/s² impact)
  - Security: Scam-detectie via lokale database + patroonanalyse
  - Privacy: Camera/microfoon toegang monitoring
  - Support: Context-bewuste AI assistent

### Glyph Guardian
- **Type**: Background Service
- **SDK**: Nothing Glyph SDK (com.nothing.ketchum)
- **Telefoon**: Nothing Phone 3a (DEVICE_24111)
- **Glyph layout**:
  - A1-A11 (indices 20-30): Bovenste strip — scan animaties
  - B1-B5 (indices 31-35): Rechter strip — threat level indicator
  - C1-C20 (indices 0-19): Grote ring — idle puls + SOS

## Onveranderlijke Regels (hardcoded)

```kotlin
// Deze regels kunnen NOOIT worden uitgeschakeld via instellingen
object ImmutableRules {
    const val R1_EMERGENCY_ALWAYS_REACHABLE = true  // 112 altijd bereikbaar
    const val R2_NO_STEALTH_DATA_SHARING = true      // Nooit stiekem data delen
    const val R3_LOCAL_FIRST = true                  // Lokaal verwerken waar mogelijk
    const val R4_ALWAYS_EXPLAIN = true               // Altijd uitleg bij ingrijpen
    const val R5_USER_CAN_OVERRIDE = true            // Altijd handmatig te overrulen
    const val R6_NO_MANIPULATION = true              // Nooit gebruiker manipuleren
}
```

## Data Flow

```
Inkomende oproep
       │
       ▼
ScamCallService.analyzeIncomingCall()
       │
       ├── Lokale database check
       ├── Prefix analyse
       ├── Patroon matching
       │
       ▼
CallAnalysis (ScamRisk: NONE/LOW/MEDIUM/HIGH)
       │
       ├── NONE/LOW → Normaal doorbellen
       ├── MEDIUM   → Waarschuwing tonen + Glyph amber
       └── HIGH     → Oproep blokkeren + Glyph rood + Notificatie
```

## Companion Orchestration Flow

```
Gebruiker / Companion API
       │
       ▼
Manus AI Boss Orchestrator
       │
       ├── Guardian Assistant      → safety/privacy/security advies
       ├── Launcher Assistant      → UI intents en snelle acties
       ├── Glyph Assistant         → statuspatronen en feedback
       └── Build/Install Assistant → APK build, release en ADB installatie
       │
       ▼
Telefoonapps voeren lokaal uit met gebruikerstoestemming en auditbare status
```

## Build Pipeline

```yaml
Push naar main
       │
       ▼
GitHub Actions triggert
       │
       ├── build-guardian.yml  → Guardian APK
       ├── build-launcher.yml  → Launcher APK
       └── build-glyph.yml     → Glyph APK
                │
                ▼
           release.yml (bij tag v*.*.*)
                │
                ▼
           GitHub Release
           ├── guardian-app-release.apk
           ├── manus-launcher-release.apk
           ├── glyph-guardian-release.apk
           └── M-Ai-Phone-v*.*.*.zip
```
