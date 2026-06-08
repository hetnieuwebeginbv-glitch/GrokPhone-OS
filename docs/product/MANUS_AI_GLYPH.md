# Manus AI Glyph

## Doel

**Manus AI Glyph** maakt de Nothing Phone 3a Glyph-interface onderdeel van de Manus AI Phone. Het is de visuele statuslaag voor Guardian, Ai Chat, Manus AI Boss, Messenger, Pay, Browser en toekomstige agents.

## Wat Nu Werkt

De huidige module `glyph-guardian` bouwt als stabiele Android foreground service:

- eigen appnaam: Manus AI Glyph
- autostart receiver na boot
- foreground notification
- acties voor AI/Guardian signalen
- fallback zonder Nothing Glyph SDK
- release APK in `releases/glyph-guardian-release.apk`

Dit is bewust stabiel gehouden zodat de telefoonombouw niet afhankelijk is van een ontbrekende vendor SDK.

## Signalen

Manus AI Glyph kent deze acties:

| Actie | Betekenis |
|---|---|
| `ai.stay4safe.glyph.IDLE` | Rustige Manus status |
| `ai.stay4safe.glyph.ALERT` | Guardian waarschuwing |
| `ai.stay4safe.glyph.EMERGENCY` | Noodstatus |
| `ai.stay4safe.glyph.AGENT_ACTIVE` | Manus AI Boss of agent werkt |
| `ai.stay4safe.glyph.MESSAGE` | Messenger-signaal |
| `ai.stay4safe.glyph.PAYMENT` | Pay/checkout-signaal |
| `ai.stay4safe.glyph.BROWSER_RISK` | Browser/scam/phishing risico |

Optioneel extra veld:

```text
reason
```

## ADB Voorbeelden

```bash
adb shell am start-foreground-service \
  -n ai.stay4safe.glyph/ai.stay4safe.glyph.GlyphGuardianService \
  -a ai.stay4safe.glyph.AGENT_ACTIVE \
  --es reason "Manus AI Boss is bezig"

adb shell am start-foreground-service \
  -n ai.stay4safe.glyph/ai.stay4safe.glyph.GlyphGuardianService \
  -a ai.stay4safe.glyph.BROWSER_RISK \
  --es reason "Verdachte betaalpagina"
```

## Echte Glyph LED-Aansturing

Voor echte LED-patronen is de officiële Nothing Glyph SDK nodig. Zodra die beschikbaar is:

1. plaats `GlyphSDK.aar` in `glyph-guardian/libs/`
2. voeg de dependency terug toe in `glyph-guardian/app/build.gradle.kts`
3. implementeer een `ManusGlyphDriver` die `ManusGlyphMode` vertaalt naar LED-patronen
4. behoud fallback logging als SDK niet beschikbaar is

## Patroonontwerp

| Modus | LED-idee |
|---|---|
| IDLE | zachte ademende ring |
| ALERT | korte dubbele puls |
| EMERGENCY | snelle volledige flits |
| AGENT_ACTIVE | lopende scanlijn |
| MESSAGE | korte zachte ping |
| PAYMENT | bevestigingspuls |
| BROWSER_RISK | amber/rood waarschuwing |

## Productrol

Manus AI Glyph is geen losse gimmick. Het is de status-taal van Manus OS:

- gebruiker ziet wanneer AI actief is
- Guardian waarschuwingen worden zichtbaar zonder scherm
- Messenger/Pay/Browser krijgen herkenbare feedback
- toekomstige agents kunnen veilig en auditbaar signalen tonen
