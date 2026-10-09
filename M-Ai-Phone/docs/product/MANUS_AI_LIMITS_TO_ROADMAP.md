# Manus AI Limits To Roadmap

## Doel

Deze lijst zet de huidige technische grenzen om naar concrete Manus AI productfasen. Niets hiervan wordt genegeerd. Elke beperking wordt een bouwspoor met een realistische oplossing.

## 1. Geen Volledige Eigen OS/ROM Vervanging

### Huidige grens

Op een standaard Nothing Phone 3a vervangen we Nothing OS niet volledig. We draaien eerst als APK-laag bovenop Android/Nothing OS.

### Manus AI aanpak

We noemen de eerste versie **Manus OS Layer**:

- Manus Launcher
- Guardian
- Manus AI Glyph
- Ai Chat
- Messenger
- Store
- Pay
- Browser
- Setup/Settings companion

### Volgende stap

Start later een aparte **Manus OS ROM Track**:

- AOSP/LineageOS haalbaarheid
- kernel/device tree/vendor blobs
- ROM build server
- eigen signing keys
- OTA server
- testplan voor modem, camera, eSIM, VoLTE, NFC, fingerprint, Glyph en emergency calling

## 2. Geen Eigen Bootlogo, OTA Of System Settings Zonder ROM/OEM

### Huidige grens

Bootlogo, system Settings, OTA en diepe systeemonderdelen zijn niet volledig aanpasbaar met normale APKs.

### Manus AI aanpak

In fase 1 bouwen we:

- eigen Setup/Settings companion
- eigen Store/Updater voor Manus apps
- eigen launcher branding
- eigen boot-ervaring na unlock
- eigen wallpaper/icon pack/theme

### Volgende stap

Voor echte bootlogo/OTA/system Settings:

- OEM/privileged build onderzoeken
- of Manus OS ROM track starten
- eigen OTA manifest en rollback policy ontwerpen

## 3. Geen Stille App-Installaties Zonder Device-Owner

### Huidige grens

Normale Android-apps mogen niet stil apps installeren. De gebruiker moet installaties bevestigen.

### Manus AI aanpak

Fase 1:

- install via ADB voor ombouw
- Store/Updater met user-confirmed installs
- duidelijke update prompts

Fase 2:

- **Manus Managed OS** via device-owner/MDM
- managed installs
- release rings
- app policies
- fleet/family provisioning

## 4. Geen Echte Glyph LED-Aansturing Zonder Nothing Glyph SDK/AAR

### Huidige grens

De officiële Nothing Glyph SDK/AAR ontbreekt nog. Daarom is echte LED-aansturing nog niet actief.

### Manus AI aanpak

We hebben nu **Manus AI Glyph** als stabiele fallback-service:

- appnaam en statuslaag zijn eigen Manus AI product
- actions voor Guardian, Browser, Messenger, Pay en agents
- foreground service
- boot receiver
- release APK

### Volgende stap

Zodra de SDK beschikbaar is:

- `GlyphSDK.aar` toevoegen aan `glyph-guardian/libs/`
- driverlaag bouwen: `ManusGlyphDriver`
- signalen vertalen naar LED-patronen
- fallback houden als SDK niet beschikbaar is

## 5. Nog Geen Echte Browser, Messenger, Pay, Store, Cloud Of Ai Chat

### Huidige grens

Deze onderdelen staan nu in masterplan/roadmap, maar zijn nog geen Android modules.

### Manus AI aanpak

We maken ze in deze volgorde:

1. `manus-suite` als eerste echte APK voor Setup, Store, Chat, Browser, Messenger en Pay.
2. Daarna modules splitsen zodra iedere productlijn genoeg eigen code heeft.
3. `manus-cloud` backend skeleton.

### Productprincipe

Elke module moet eerst klein maar echt zijn:

- installeerbaar
- eigen package name
- eigen appnaam
- gekoppeld aan Manus Launcher
- geen nepfeatures zonder duidelijke fallback

## 6. Nog Geen Backend Voor Accounts, Updates, Payments, Messenger Of Agents

### Huidige grens

De telefoonlaag kan installeren, maar er is nog geen Manus Cloud.

### Manus AI aanpak

Backend MVP:

- identity/account API
- device registry
- update manifest API
- app catalog API
- entitlement/payment API
- message relay API
- AI gateway
- Manus AI Boss orchestration API
- audit logs

### Volgende stap

Eerst lokaal/mockbaar ontwerpen, daarna deploybaar maken.

## 7. Device-Owner/MDM Provisioning

### Huidige grens

Er is nu een eerste managed provisioning basis: `manus-suite` bevat `ManusDeviceAdminReceiver`, en `scripts/provision-device-owner.sh` voert `dpm set-device-owner` uit op een schoon toestel.

### Manus AI aanpak

Fase 2 wordt **Manus Managed OS**:

- QR provisioning
- device-owner enrollment
- app allow/block lists
- managed installs
- DNS/VPN/cert policies
- family/fleet admin console

### Volgende stap

Verder bouwen:

- QR provisioning
- admin console
- managed install policy
- fleet backend

Zie [../ops/DEVICE_OWNER_PROVISIONING.md](../ops/DEVICE_OWNER_PROVISIONING.md).

## 8. Nog Geen Productie-Keystore

### Huidige grens

De lokale release APKs zijn installbaar, maar nog niet onder een officiële productie-keystore gesigned.

### Manus AI aanpak

Voor laptop-ombouw gebruiken we lokale installable release APKs.

Voor productie:

- aparte offline keystore
- key rotation policy
- CI secrets
- release signing flow
- checksum/SBOM per release
- recovery procedure als key lekt

### Volgende stap

Maak `docs/ops/RELEASE_SIGNING.md` en genereer pas daarna een echte productie-keystore.

## 9. Nog Geen Telefoon Getest

### Huidige grens

Er hing nog geen Nothing Phone 3a aan ADB.

### Manus AI aanpak

Zodra de telefoon aangesloten is:

```bash
cd /Users/mitfcg-/M-Ai-Phone
./scripts/install.sh
```

### Acceptatie Op Echte Telefoon

- ADB ziet device
- APKs installeren
- Manus Launcher kan standaard home worden
- Guardian start
- Manus AI Glyph foreground service draait
- Uninstall werkt

## Samenvatting

Deze grenzen vormen de echte Manus AI route:

1. Manus OS Layer werkend maken op Nothing Phone 3a.
2. Eigen kernapps bouwen: Setup, Store, Chat, Browser, Messenger, Pay.
3. Manus Cloud bouwen voor accounts, updates, payments, agents en messaging.
4. Device-owner/MDM toevoegen voor managed installs.
5. Productie-signing en release governance opzetten.
6. Daarna pas ROM/OEM spoor starten.
