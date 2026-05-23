# Technische Architectuur — Ai Manus Phone

## Overzicht

De Ai Manus Phone bestaat uit drie lagen die samenwerken op een standaard Nothing Phone 3a:

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

### Manus Launcher
- **Type**: Android Launcher (HOME activity)
- **Taal**: Kotlin + Jetpack Compose
- **Design**: Cybernetic Noir (zwart + teal glassmorphism)
- **Functies**: Custom home screen, app drawer, Guardian widget, klok

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
