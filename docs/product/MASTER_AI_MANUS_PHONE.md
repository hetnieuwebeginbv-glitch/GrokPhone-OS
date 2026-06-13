# Master Ai Manus Phone

## Missie

De **Master Ai Manus Phone** is een eigen telefoonervaring bovenop de Nothing Phone 3a, met een route naar een managed OS en later een echte Manus OS ROM. Het doel is niet alleen een launcher maken, maar een volledig ecosysteem onder eigen naam:

- eigen OS-laag
- eigen AI-browser
- eigen AI-chat
- eigen messenger
- eigen payments
- eigen appstore
- eigen cloud
- eigen agent-netwerk
- eigen family/fleet beheer
- later eigen ROM/OS-track

Alles moet eerst kunnen draaien op een standaard Nothing Phone 3a zonder root. Wat meer rechten nodig heeft, komt later via device-owner, OEM-samenwerking of ROM.

De huidige grenzen zijn geen eindpunt maar bouwsporen. Zie [MANUS_AI_LIMITS_TO_ROADMAP.md](MANUS_AI_LIMITS_TO_ROADMAP.md).

## Master Stack

### 1. Manus Launcher

De standaard home experience.

- Home screen
- App drawer
- Smart folders
- Search
- Widgets
- Guardian status
- Ai Chat entry point
- Store entry point
- Messenger entry point
- Pay entry point
- Browser entry point

Status: eerste module bestaat en bouwt.

### 2. Stay4S Guardian

De safety/security/privacy laag.

- Scam call detectie
- Phishing/SMS waarschuwingen
- Valdetectie
- Privacy monitor
- Noodcontacten
- Audit log
- Guardian status
- Family/caregiver signalen

Status: eerste module bestaat en bouwt met fallback Glyph service.

### 3. Manus AI Glyph

Nothing Phone 3a statuslaag via Glyph.

- Guardian status
- Alert patronen
- Emergency patronen
- Messenger/Pay/Browser waarschuwingen
- AI Boss en agent-activity signalen
- Fallback-service zonder SDK
- Echte LED-aansturing zodra Nothing Glyph SDK beschikbaar is

Status: eerste module bestaat en bouwt als fallback.

### 4. Manus Setup & Settings

Eigen setup wizard en instellingen-app.

- Eerste installatie
- Account login
- Default launcher begeleiding
- Permissies
- Accessibility begeleiding
- Batterij-optimalisatie begeleiding
- Noodcontacten
- Guardian instellingen
- AI consent
- Store/update instellingen
- Family/fleet mode

Module: `setup-settings-app`

### 5. Manus Store & Updater

Eigen appstore en updatekanaal.

- App catalogus
- Signed APK metadata
- Versies en changelog
- Release rings: internal, alpha, beta, stable, hotfix
- User-confirmed installs
- Managed installs via device-owner
- Rollback en revoke
- Developer/agent marketplace basis

Module: `manus-store`

### 6. Manus Browser

Eigen AI-browser en veilige weblaag.

- AI samenvattingen
- Manus Search
- Tracker/ad blocking
- Guardian phishing/scam checks
- Veilige betaalmodus
- Private browsing standaard
- Family/senior veilige modus
- Lees-voor modus
- Prijsvergelijking
- Betrouwbaarheidscontrole
- Agent-acties op webpagina's

Module: `manus-browser`

### 7. Manus Search

Eigen AI-zoeklaag, eerst als metasearch en later als eigen index waar zinvol.

- AI antwoorden met bronnen
- Web, apps, contacts, settings en store zoeken
- Safe search profielen
- Scam/verkeerde-site waarschuwingen
- Search widgets in launcher en browser

Backend: `search-api`

### 8. Ai Chat

De gebruikerschat op de telefoon.

- Device help
- Guardian uitleg
- Scam/SMS/link analyse
- Berichtconcepten
- Samenvattingen
- Zoeken in instellingen
- Acties voorbereiden met bevestiging
- On-device light classifiers waar mogelijk
- Cloud LLM gateway waar nodig

Module: `manus-chat`

### 9. Manus AI Boss

De parallelle orchestrator die specialistische agents aanstuurt.

- Eigen beheerdersruimte voor de eigenaar/admin
- Een hoofdagent die taken beoordeelt, verdeelt en terugrapporteert
- Command queue voor opdrachten vanuit chat, launcher, browser, store en beheer
- Approval gate voor acties met risico of kosten
- Audit log voor elke agentactie
- Parallel mode voor gelijktijdig onderzoek, voorbereiding en controles
- Guardian Agent
- Device Agent
- Browser Agent
- Search Agent
- Messenger Agent
- Payments Agent
- Store Agent
- Fleet/Admin Agent
- Build/Install Agent

Belangrijk: Ai Chat praat met de gebruiker. Manus AI Boss coordineert acties en agents. Dit blijven gescheiden rollen.

App basis: `manus-suite` met `BossAdminActivity`, `BossAgentsActivity` en `BossPolicyActivity`

Backend: `manus-boss-api`

#### AI Boss command model

1. De gebruiker of eigenaar geeft een opdracht.
2. Manus AI Boss classificeert doel, risico, benodigde permissies en commerciele waarde.
3. De Boss splitst de opdracht in subtaken voor specialistische agents.
4. Agents voeren onderzoek, voorbereiding of controles parallel uit.
5. De Boss verzamelt resultaten, dedupliceert conflicten en maakt een besluitvoorstel.
6. Bij installaties, betalingen, accountwijzigingen, datadeling of device-owner acties vraagt de Boss expliciete goedkeuring.
7. Na uitvoering schrijft de Boss een auditregel met agent, opdracht, bron, actie en resultaat.

#### AI medewerker agents

- **Guardian Agent**: veiligheid, scams, noodsituaties, privacy en risicosignalen.
- **Device Agent**: instellingen, permissies, batterij, launcher, device-owner en ADB-provisioning.
- **Browser Agent**: webcontrole, phishing, samenvattingen, bronnen en veilige betaalmodus.
- **Messenger Agent**: berichten, groepen, business inbox, vertaling en samenvatting.
- **Payments Agent**: abonnementen, entitlements, facturen, refunds en fraudeflags.
- **Store Agent**: catalogus, updates, release rings, rollback en revoke.
- **Fleet/Admin Agent**: family, beheer, policies, toestellen en auditrapporten.
- **Build/Install Agent**: APK-builds, signingchecks, installatiestatus en release readiness.
- **Growth Agent**: nieuwe productmiddelen, commerciele kansen en ecosysteem-uitbreidingen.

#### Beheersregels

- Autopilot laag 1: lezen, samenvatten, controleren en voorstellen.
- Autopilot laag 2: lage-risico device acties alleen na vooraf ingestelde policy.
- Approval verplicht: betalen, installeren, verwijderen, accountwijzigingen, datadeling en device-owner acties.
- Gewone Android toestellen: geen stille installaties.
- Managed toestellen: device-owner policies en managed installs zodra provisioning actief is.

### 10. Manus Messenger

Eigen secure messenger onder eigen naam.

- 1-op-1 chat
- Groepen
- Media
- Voice notes
- Read receipts
- E2EE met bewezen protocol
- Contact discovery met toestemming
- Blocking/reporting
- Family/caregiver channel
- Business inbox
- AI drafting, vertaling en samenvatting

Geen WhatsApp-kloon. WhatsApp alleen via officiele WhatsApp Business Platform APIs voor toegestane business messaging.

Module: `manus-messenger`

### 11. Manus Pay

Eigen betalings- en entitlementlaag.

- Abonnementen
- Premium AI
- Family plans
- Enterprise/fleet licenses
- Marketplace purchases
- Facturen
- Refunds
- VAT/tax metadata
- Fraud checks
- Entitlements

Start met payment provider. Geen eigen wallet of peer-to-peer payments in MVP zonder KYC/AML/licentieonderzoek.

Module: `manus-pay`

### 12. Manus Mail

Eigen mail-app met AI filtering.

- Unified inbox
- Scam/phishing mail detectie
- AI samenvattingen
- Prioriteit inbox
- Family/fleet policies
- Secure attachment handling
- Search via Manus Search

Module: `manus-mail`

### 13. Manus Calendar

Agenda en planning.

- AI planning
- Family/fleet calendars
- Travel/time reminders
- Meeting summaries
- Guardian safety reminders

Module: `manus-calendar`

### 14. Manus Drive

Eigen cloudopslag en backups.

- Device backup metadata
- App settings backup
- Documents
- Encrypted folders
- Family shared folders
- Fleet document distribution
- Recovery codes

Backend: `manus-drive-api`

### 15. Manus Photos

Eigen gallery en private AI zoeklaag.

- Local-first gallery
- Private AI tags
- Duplicate cleanup
- Sensitive media vault
- Family albums
- Backup naar Manus Drive

Module: `manus-photos`

### 16. Manus Contacts

Eigen trusted contacts laag.

- Contacts
- Trusted circles
- Emergency contacts
- Family/caregiver groups
- Business/fleet directory
- Messenger identity koppeling

Module: `manus-contacts`

### 17. Manus Dialer

Eigen telefoon-app, waar Android/default app policy dat toestaat.

- Scam call warning
- Caller trust score
- Emergency flows
- Call notes
- Business/fleet call policies

Module: `manus-dialer`

### 18. Manus Messages/SMS

Eigen SMS-app, waar Android/default app policy dat toestaat.

- Phishing filter
- OTP herkenning
- Scam explanations
- Safe links
- Family/senior warnings

Module: `manus-sms`

### 19. Manus Notes

Notities en AI memory.

- Notes
- Tasks
- Voice notes
- AI summaries
- Personal memory with consent
- Search via Manus Search

Module: `manus-notes`

### 20. Manus Vault

Veilige kluis.

- Passwords
- Documents
- Recovery codes
- Identity documents
- Payment recovery data
- Encrypted local storage
- Optional cloud backup via Manus Drive

Module: `manus-vault`

### 21. Manus Family

Family/caregiver dashboard.

- Consent-based family controls
- Senior support
- Child-safe mode
- Emergency contacts
- Location sharing only with explicit consent
- Guardian alerts
- App usage policies for managed devices

Module: `manus-family`

### 22. Manus Fleet

Zakelijke device management laag.

- Device registry
- Managed provisioning
- App policies
- Update rings
- Compliance status
- Lost device actions
- Fleet support logs

Backend/admin: `manus-fleet-console`

### 23. Manus Agent Store

Marketplace voor AI agents.

- Agent catalogus
- Permissions manifest
- Pricing
- Reviews
- Safety review
- Kill switch
- Developer payout
- Runtime audit logs

Backend: onderdeel van Store + Boss API.

### 24. Manus Cloud

De backend-laag.

- Identity
- Device registry
- Sync
- Update manifests
- App catalog
- Messaging service
- Payment/entitlements
- AI gateway
- Search API
- Drive API
- Fleet console
- Audit logs
- Admin console

## Bouwvolgorde

### Sprint 1: Fundament

1. Huidige drie APKs buildbaar houden.
2. Setup/Settings app maken.
3. Store/Updater shell maken.
4. Ai Chat shell maken.
5. Backend skeleton: identity, device registry, update manifest.

### Sprint 2: Dagelijkse Gebruik

1. Manus Browser MVP.
2. Manus Search MVP.
3. Manus Messenger MVP.
4. Manus Contacts MVP.
5. Guardian integreren met Browser/Messenger/SMS.

### Sprint 3: Geld en Ecosysteem

1. Manus Pay entitlement MVP.
2. Store betaalde items en subscriptions.
3. Agent Store manifest model.
4. Developer onboarding docs.
5. Admin console basis.

### Sprint 4: Family en Fleet

1. Manus Family dashboard.
2. Manus Fleet console.
3. Device-owner provisioning.
4. Managed app updates.
5. Pilot met 5 tot 50 toestellen.

### Sprint 5: Eigen OS Track

1. ROM feasibility voor Nothing Phone 3a.
2. Device tree/vendor/kernel inventarisatie.
3. OTA server ontwerp.
4. Systeemapp-selectie.
5. Eerste testimage alleen als haalbaarheid klopt.

## Commercieel Model

- Device setup fee
- Guardian subscription
- Family plan
- Premium AI
- Manus Pay transaction/entitlement fees waar toegestaan
- Store marketplace commissie
- Agent marketplace commissie
- Enterprise/fleet licenses
- Managed support contracts
- Branded preconfigured phones

## Belangrijkste Grenzen

Op een standaard Nothing Phone 3a kunnen we veel als APK-laag, maar niet alles:

- geen volledige vervanging van Nothing OS zonder ROM/OEM
- geen stille installs zonder device-owner/privileged installer
- geen volledige Settings-vervanging zonder system privileges
- geen modem/baseband/eSIM/VoLTE controle
- geen OS OTA controle zonder ROM/OEM
- geen unofficial WhatsApp bridge

Daarom bouwen we eerst de OS-ervaring en het ecosysteem, daarna pas dieper OS-eigenaarschap.
