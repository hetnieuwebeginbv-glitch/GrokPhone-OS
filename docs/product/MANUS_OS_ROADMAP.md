# Manus OS Roadmap

## Doel

Manus OS wordt opgebouwd in drie realistische stappen. De eerste versie moet volledig kunnen draaien op een standaard Nothing Phone 3a zonder root of custom ROM. Daarna volgt een managed variant met meer controle. Pas als de basis commercieel en technisch werkt, starten we een echte OS/ROM-track.

## Definitie

**Manus OS** is niet meteen een eigen kernel of volledig Android-alternatief. In versie 1 is het een OS-ervaring bovenop Nothing OS:

- eigen launcher
- eigen Guardian/security laag
- eigen AI Chat
- Manus AI Boss orchestration
- eigen Messenger
- eigen Store/Updater
- eigen Payment/entitlement model
- eigen Setup/Settings companion
- eigen account, backend en agent-netwerk

De complete master scope staat in [MASTER_AI_MANUS_PHONE.md](MASTER_AI_MANUS_PHONE.md).

De huidige technische grenzen en hoe we die oplossen staan in [MANUS_AI_LIMITS_TO_ROADMAP.md](MANUS_AI_LIMITS_TO_ROADMAP.md).

## Fase 1: Manus OS Layer

**Doel:** een Nothing Phone 3a voelt en werkt als een Ai Manus Phone zonder systeemrechten.

### Scope

- Manus Launcher als standaard home screen.
- Guardian App als safety/security/privacy laag.
- Glyph Guardian als status-service met fallback zonder vendor SDK.
- Ai Chat als gebruikersinterface voor hulp, uitleg en acties.
- Manus Browser als veilige AI-browser.
- Manus Search als AI-zoeklaag.
- Manus AI Boss als server/laptop-side orchestrator voor specialistische agents.
- Manus Messenger, Pay, Mail, Drive, Photos, Contacts, Dialer, SMS, Notes en Vault als eigen kernapps in latere MVP-stappen.
- Setup checklist voor permissies, launcher-keuze, meldingen, accessibility, batterij-optimalisatie en noodcontacten.
- Installatie via ADB met `scripts/install.sh`.

### Deliverables

- Debug en release APKs voor Launcher, Guardian en Glyph Guardian.
- Werkend installatiescript.
- Documentatie voor build, install en productgrenzen.
- Eerste backend ontwerp voor account, device registry, update manifest en AI gateway.

### Acceptatie

- Fresh Nothing Phone 3a kan binnen 30 minuten worden omgezet.
- Launcher kan als standaard ingesteld worden.
- Guardian start en blijft zichtbaar/auditbaar.
- Install/uninstall flow is herhaalbaar.
- Geen claims dat Nothing OS volledig vervangen is.

## Fase 2: Manus Managed OS

**Doel:** meer controle over toestellen via Android device-owner/MDM zonder custom ROM.

### Scope

- Device-owner provisioning via QR/ADB voor test- en fleet-toestellen.
- Managed app installs en app policy waar Android dat toestaat.
- Ai Manus Store met catalogus, signed APK metadata, release rings en rollback.
- Ai Manus Account Sync voor voorkeuren, noodcontacten, Guardian instellingen en AI consent.
- Ai Manus Messenger beta met eigen accounts en E2EE-ontwerp.
- Ai Manus Pay voor abonnementen, entitlements, facturen en marketplace-aankopen via payment provider.
- Admin console voor devices, updates, support, logs en incidenten.

### Deliverables

- Setup/Settings companion app.
- Store/Updater app.
- Backend MVP: identity, device registry, catalog, update manifest, entitlement API.
- Managed provisioning handleiding.
- Beta pilot playbook.

### Acceptatie

- 5 interne toestellen kunnen managed worden.
- 50 beta-toestellen kunnen updates ontvangen via release rings.
- Admin kan zien welke versie en status elk toestel heeft.
- Messenger en Pay zijn duidelijk eigen producten, geen WhatsApp-kloon of bank/wallet zonder vergunning.

## Fase 3: Manus OS ROM Track

**Doel:** onderzoeken of een echte Android-fork/custom ROM voor Nothing Phone 3a haalbaar is.

### Scope

- AOSP/LineageOS haalbaarheidsstudie voor Nothing Phone 3a.
- Device tree, kernel sources, vendor blobs, firmware afhankelijkheden en bootloader-status inventariseren.
- Build server voor ROM images.
- Eigen signing keys en OTA update infrastructuur.
- Systeemapps: Setup Wizard, Launcher, Settings panels, Store/Updater, Guardian, Assistant.
- Testplan voor camera, modem, VoLTE, eSIM, fingerprint, NFC, Bluetooth, Wi-Fi, sensors, Glyph en emergency calling.

### Deliverables

- ROM feasibility report.
- Eerste engineering build als testimage, alleen voor testtoestellen.
- OTA proof of concept.
- Security patch en rollback procedure.

### Acceptatie

- Testtoestel boot betrouwbaar.
- Basisfuncties werken: bellen, data, camera, Wi-Fi, Bluetooth, fingerprint, emergency call.
- OTA update kan gecontroleerd uitgerold en teruggedraaid worden.
- Er is een onderhoudsplan voor maandelijkse security patches.

## Productlijnen

### Ai Manus Messenger

Eigen secure messenger onder Ai Manus naam:

- 1-op-1 chat, groepen, media, voice notes en read receipts.
- E2EE met bewezen protocolkeuze.
- Contact discovery met toestemming.
- Family/caregiver channels.
- Business inbox voor support en fleets.
- AI drafting, vertaling, samenvatting en scam-uitleg.

WhatsApp-koppeling blijft alleen via officiele WhatsApp Business Platform APIs voor toegestane business messaging. Geen scraping, reverse engineering of consumer-message bridging.

### Ai Manus Pay

Eigen betaal- en entitlementlaag:

- abonnementen
- premium AI
- family plans
- enterprise/fleet licenses
- marketplace purchases
- invoices, refunds, VAT/tax metadata en fraud checks

Begin met een payment provider. Geen eigen wallet, stored value of peer-to-peer payments in MVP zonder KYC/AML/licentieonderzoek.

### Ai Chat en Manus AI Boss

Ai Chat is de gebruiker-facing chatmodus.

Manus AI Boss is de parallelle orchestrator die taken verdeelt naar agents:

- Guardian Agent
- Device Agent
- Messenger Agent
- Store Agent
- Payments Agent
- Build/Install Agent
- Fleet/Admin Agent

Belangrijke acties vereisen bevestiging of vooraf ingestelde managed policy.

## Wat Ik Zou Bouwen Als Telefoon

Als ik de telefoon vanaf nul zou ontwerpen:

- hardware privacy switches voor mic/camera/radio
- dedicated AI button
- secure element voor device identity en payments
- sterke batterij en thermals voor on-device AI
- LED/status surface zoals Glyph, maar met open SDK
- recovery partition met veilige rollback
- AI-first setup wizard
- private app/agent marketplace
- family/fleet mode vanaf dag een
- open developer SDK voor agents, widgets en device actions

Voor nu bouwen we dit eerst als softwarelaag op de Nothing Phone 3a, omdat dat snel testbaar en commercieel realistischer is.

## Directe Volgende Stappen

1. Houd `manus-launcher`, `guardian-app` en `glyph-guardian` buildbaar.
2. Maak een `setup-settings-app` module voor onboarding en permissies.
3. Maak een `manus-store` module voor catalogus en update checks.
4. Maak een `manus-chat` module als eerste Ai Chat shell.
5. Maak een `manus-browser` module als veilige AI-browser shell.
6. Maak een `manus-messenger` module als eigen secure chat shell.
7. Maak een `manus-pay` module als entitlement/billing shell.
8. Maak backend skeleton: identity, device registry, update manifest, entitlement API.
9. Voeg device-owner provisioning documentatie toe.
10. Test op een echte Nothing Phone 3a via `./scripts/install.sh`.
11. Beslis daarna of ROM feasibility onderzoek start.
