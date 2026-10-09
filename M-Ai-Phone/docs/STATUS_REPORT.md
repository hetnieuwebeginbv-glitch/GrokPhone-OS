# Manus Ai Phone Status Report

Datum: 2026-06-08

## Buildstatus

De lokale release-build is succesvol uitgevoerd met `./scripts/build-release.sh`.

Gebouwde APK's in `releases/`:

- `guardian-app-release.apk`
- `manus-launcher-release.apk`
- `glyph-guardian-release.apk`
- `manus-suite-release.apk`

Alle vier APK's zijn geverifieerd met `apksigner verify --verbose` en valideren met APK Signature Scheme v2 en 1 signer.

De APK-bestanden staan lokaal in `releases/`. Deze map is bedoeld als lokaal bouwresultaat en wordt niet standaard in Git gevolgd.

## Android Modules

### Guardian App

Module: `guardian-app`

Bevat de basis voor veiligheid, noodmodus, fall-detection service, accessibility service, fastbutton flow en Glyph-signalen richting de Glyph-app.

### Manus Launcher

Module: `manus-launcher`

Bevat de eigen launcher-laag voor de Manus Ai Phone met home/app-drawer basis en widgets.

### Manus AI Glyph

Module: `glyph-guardian`

Bevat de eigen Manus AI Glyph-service met signalen voor start, idle, alert, emergency, agent active, message, payment en browser risk.

Zonder officiele Nothing Glyph SDK/AAR blijft dit een voorbereid integratiepunt. Echte Nothing Glyph LED-aansturing vereist de officiele SDK of een ondersteunde device API.

### Manus Suite

Module: `manus-suite`

Bevat de basis-apps voor de Manus-laag:

- Admin Room
- Setup
- AI Boss Admin
- AI Boss Dashboard
- Command Queue
- AI medewerker agents
- Admin policies
- Audit Log
- Device & Flash Readiness
- Backend Console
- Store
- AI Chat
- Browser
- Messenger
- Pay
- Device Admin receiver

Dit is de eerste echte Manus-suite APK voor de telefoonlaag. Backend-koppelingen voor accounts, store, messenger, payments, cloud en agents moeten nog als serverdiensten worden gebouwd.

De AI Boss Admin Room staat in de app als beheerdersruimte. Deze legt de hoofdagent, medewerker-agents, parallelle taakverdeling, approval gates en auditlog-model vast. De huidige versie is de native telefoonbasis; echte cloud/orchestration uitvoering komt in de backendfase via `manus-boss-api`.

De Admin Room is uitgebreid met dashboard, command queue, audit log, device/flash readiness en backend console. Daarmee is de telefoon-app voorbereid op beheer vanuit een eigenaar/admin rol.

## Scripts

- `scripts/build-release.sh`: bouwt alle release-APK's en kopieert ze naar `releases/`.
- `scripts/install.sh`: installeert Guardian, Launcher, Manus AI Glyph en Manus Suite via ADB.
- `scripts/provision-device-owner.sh`: zet Manus Suite als device owner op een vers of gereset toestel.
- `scripts/flash-readiness.sh`: maakt een non-destructive readiness report voor APK-installatie, device-owner en latere ROM/flash-track.
- `scripts/uninstall.sh`: verwijdert de Manus-apps via ADB.

Alle scripts zijn gecontroleerd met `bash -n`.

## Documentatie

De documentatie is opgeschoond en opnieuw ingedeeld:

- `docs/README.md`: documentatie-index.
- `docs/product/`: productvisie, Manus OS roadmap, master phone scope, Glyph-plan en technische grenzen.
- `docs/ops/`: device-owner provisioning en release signing.
- `docs/strategy/`: OS-hunter en commerciele strategie.
- `docs/ARCHITECTURE.md`: technische architectuur.
- `docs/BUILD.md`: build-instructies.
- `docs/INSTALL.md`: installatie-instructies.

Dubbele workflow-kopieen buiten `.github/workflows/` zijn verwijderd. De actieve GitHub Actions staan alleen nog in `.github/workflows/`.

## Huidige Grenzen

Wat nu nog niet volledig kan zonder extra externe rechten, hardware of diensten:

- Geen volledige Nothing OS vervanging zonder ROM/OEM-unlock en eigen firmwaretraject.
- Geen eigen bootlogo/OTA/system Settings zonder ROM/OEM-rechten.
- Geen stille app-installaties zonder device-owner, MDM of privileged installer.
- Geen echte Nothing Glyph LED-aansturing zonder officiele Nothing Glyph SDK/AAR of ondersteunde API.
- Geen productie-keystore in de repo; release signing moet via veilige environment variables of secrets.
- Geen live telefoontest uitgevoerd zolang `adb devices` geen verbonden toestel toont.
- Geen ROM flash uitgevoerd; readiness is voorbereid, maar echte flash vereist geteste images, device tree, vendor blobs en rollbackplan.
- Geen productiebackend voor accounts, payments, messenger, AI agents, store, updates en cloud.

## Volgende Installatiestap

Zodra de Nothing Phone 3a via USB zichtbaar is in `adb devices`:

```bash
./scripts/install.sh
```

Voor device-owner provisioning op een vers of gereset toestel:

```bash
MANUS_ENABLE_DEVICE_OWNER=1 ./scripts/install.sh
```

Voor flash-readiness inventarisatie:

```bash
./scripts/flash-readiness.sh
```
