# Ai Manus Phone Expert Implementation Plan

## Goal

Turn a stock Nothing Phone 3a into a branded **Ai Manus Phone** with Samsung-like core experiences under the Ai Manus/Stay4Safe name, using realistic Android delivery paths first and deeper system control only where legally and technically available.

This is not a full Android fork in the first phases. The practical path is:

- branded launcher, guardian, assistant, messenger, updater, and setup apps
- device-owner provisioning for managed devices where possible
- backend services for accounts, app catalog, sync, messaging, AI, updates, and telemetry
- optional OEM/private-signing/custom-ROM track only after product-market and compliance validation

## Hard Constraints

- **Nothing Phone 3a remains the base device.** Hardware, modem, Play services, Android permissions, and Nothing OS update behavior stay controlled by Nothing/Google unless an OEM agreement or custom ROM exists.
- **System Settings cannot be fully replaced by a normal APK.** Provide a branded Settings companion and guided provisioning. True Settings replacement requires privileged/system app access.
- **App store installation is constrained by Android policy.** A private catalog can distribute APKs, but installs require user consent unless using device-owner/MDM, enterprise provisioning, or privileged installer rights.
- **WhatsApp compatibility must be official.** Use WhatsApp Business Platform APIs for approved business messaging only. Do not scrape, automate, or impersonate WhatsApp. Consumer chat must be handled by a separate Ai Manus Messenger.
- **Safety features must be explainable and overridable.** Emergency calling, consent, audit logs, and privacy controls are mandatory product requirements.

## Target Product Surface

### 1. Ai Manus Launcher

- Default HOME app with branded home screen, app drawer, search, widgets, wallpapers, Glyph status, guardian status, and AI entry point.
- Include Samsung-like basics under own naming: quick actions, smart folders, device search, lock/home wallpaper picker, emergency shortcut, and setup checklist.
- Integrate with Guardian and Assistant through local Android intents/services.

### 2. Ai Manus Settings and Provisioning

- Branded setup wizard for first run: account login, permissions, default launcher, notification access, accessibility access, battery optimization exemptions, emergency contacts, AI consent, and update channel.
- Settings companion app for Ai Manus features: account, privacy, guardian, AI, messenger, store, updates, family/guardian controls, backups, device diagnostics.
- Device-owner mode for fleet/beta devices to reduce manual setup and allow managed installs, restrictions, certificates, VPN/DNS settings, and kiosk-like policies where needed.

### 3. Ai Manus Store and Backend

- Private app catalog with signed APK metadata, versions, changelogs, screenshots, compatibility flags, rollout rings, and revoke/disable controls.
- Backend services:
  - account and identity
  - device registry
  - app catalog API
  - APK artifact storage
  - update manifest API
  - entitlement/license checks
  - audit logs and admin console
- Start with user-confirmed APK installs. Add managed silent installs only for enrolled device-owner devices.

### 4. Ai Manus Account Sync

- Account service for profile, device list, emergency contacts, preferences, guardian rules, AI settings, and encrypted backup metadata.
- Use Android AccountManager/WorkManager where appropriate.
- Sync must be conflict-aware, resumable, and local-first for safety-critical settings.
- Sensitive data should use end-to-end encryption where server access is not needed.

### 5. Ai Manus Messenger

- Own secure messenger for Ai Manus users: one-to-one chat, groups, attachments, voice notes, read receipts, contact discovery with consent, blocking/reporting, and emergency contact channel.
- Product position: this is the Ai Manus alternative to WhatsApp under the Ai Manus name, with its own accounts, push service, contact graph, encrypted media storage, desktop/web companion path, and business inbox option.
- Use modern end-to-end encryption protocol design; do not invent cryptography.
- WhatsApp path:
  - Business notifications/support through official WhatsApp Business Platform APIs only.
  - No unofficial WhatsApp client, scraping, reverse engineering, or message bridging.
  - Consumer secure chat remains Ai Manus Messenger.

### 5b. Ai Manus Pay

- Own payment model for subscriptions, device services, store purchases, premium AI usage, family/caregiver plans, business inboxes, and managed-device support.
- Start with a compliant payment provider integration rather than storing card data directly. The backend owns plans, invoices, entitlements, refunds, VAT/tax metadata, fraud checks, and audit logs.
- Android app payments must respect the channel: Play-distributed digital goods follow Play billing rules; private/managed distribution can use the Ai Manus account billing flow where legally allowed.
- Wallet-like or peer-to-peer payments are a separate regulated product track and should not be mixed into the MVP until licensing, KYC/AML, chargeback, and risk operations are solved.

### 6. Ai Manus AI Chat and Assistant

- In-app assistant for device help, safety explanations, scam analysis, message drafting, search, and controlled actions.
- Separate mode: **Ai Chat** is the user-facing conversation product. **Manus AI Boss** is the parallel orchestrator that delegates operational tasks to specialist assistants. The chat can ask the orchestrator for help, but they remain separate control surfaces.
- Use clear permission boundaries: the assistant may suggest actions, but high-impact actions require confirmation.
- Architecture:
  - local app context and rules engine
  - cloud LLM gateway with policy filters
  - optional on-device models for lightweight classification
  - audit trail for safety interventions

### 7. Guardian Safety

- Safety modules: fall detection, emergency contacts, scam call/SMS warnings, privacy monitor, suspicious link warnings, location sharing in emergency flows, and caregiver/family dashboard.
- Must include:
  - emergency services remain reachable
  - no hidden recording or stealth sharing
  - visible status and explanations
  - manual override
  - consent-based family controls

### 8. Updates

- App updates through Ai Manus Store/Updater for Ai Manus APKs.
- Nothing OS and security patches remain through Nothing/Android unless OEM/custom-ROM track is created.
- Release rings: internal, alpha, beta, stable, emergency hotfix.
- Every update needs signed artifacts, rollback policy, compatibility checks, staged rollout, and release notes.

### 9. Install Flow

- MVP install: ADB script installs APKs, sets launcher, starts services, and opens setup wizard.
- Beta install: guided desktop installer plus QR/NFC provisioning for device-owner enrollment where supported.
- Production install:
  - consumer path: download installer, user-approved APK updates, Play Store where accepted
  - managed path: zero-touch/QR provisioning, device-owner mode, managed app installs
  - retail path: pre-provisioned phones prepared before shipment

## Architecture

```
Phone APKs
  Ai Manus Launcher
  Ai Manus Setup/Settings
  Ai Manus Guardian
  Ai Manus Messenger
  Ai Manus Assistant
  Ai Manus Messenger
  Ai Manus Pay
  Ai Manus Store/Updater
        |
        v
Backend Platform
  Identity + Account Sync
  Device Registry
  App Catalog + Update Manifests
  Messaging Service
  Payments + Entitlements
  AI Gateway
  Guardian Events + Admin Console
        |
        v
Operations
  Signing keys
  CI/CD releases
  Rollout rings
  Monitoring
  Compliance review
```

## Phased Delivery

### Phase 1: MVP Conversion Kit

Objective: make one Nothing Phone 3a feel like an Ai Manus Phone without system privileges.

- Ship launcher, guardian, glyph integration, setup wizard, assistant entry point, and updater shell.
- Add branded settings companion for Ai Manus features.
- Implement manual/ADB install flow and permission checklist.
- Create minimal backend: account login, device registration, update manifest, app catalog metadata.
- Messenger MVP can be local prototype or closed-network chat; no WhatsApp integration except documented official API boundary.
- Acceptance:
  - fresh phone can be converted in under 30 minutes
  - launcher is default
  - guardian runs reliably after reboot
  - update check works
  - account/device appears in admin console

### Phase 2: Beta Managed Phone

Objective: support real beta users and reduce fragile manual setup.

- Add QR/device-owner provisioning path for test devices.
- Build private app store with signed APK download, staged rollout, changelog, and user-confirmed installs.
- Add account sync for preferences, emergency contacts, guardian settings, and assistant consent.
- Build secure messenger beta with E2EE, push notifications, recovery policy, abuse controls, and admin-safe metadata only.
- Add AI assistant backend gateway, rate limits, safety filters, tool/action confirmation, and audit logs.
- Add guardian dashboard for family/caregiver use with consent.
- Acceptance:
  - 50-200 beta devices can be enrolled, updated, monitored, and recovered
  - staged rollout and rollback process is tested
  - messenger passes basic security review
  - privacy and consent flows are documented and visible in-app

### Phase 3: Production Branded Phone

Objective: operate Ai Manus Phone as a supportable product.

- Harden backend for multi-region availability, backups, incident response, key rotation, fraud controls, and observability.
- Finalize production signing, release governance, support tooling, crash analytics, and update SLAs.
- Publish eligible apps through Play Store where policy-compliant; keep private store for managed or proprietary components.
- Establish retail provisioning process for preconfigured phones.
- Complete legal/compliance work: privacy policy, terms, data processing agreements, child/guardian consent rules where relevant, security disclosure process.
- Decide on OEM/custom-ROM track:
  - continue APK/device-owner approach for speed and compatibility
  - or negotiate OEM/privileged access for deeper Settings, installer, dialer, system update, and boot branding control
- Acceptance:
  - production devices can be shipped, restored, updated, and supported
  - emergency/safety flows are tested under real-world conditions
  - backend has operational runbooks and monitoring
  - security review and privacy review are complete before public launch

## Workstreams

- Android: launcher, setup/settings, guardian, assistant, messenger, store/updater, shared design system.
- Backend: identity, device registry, sync, catalog, update manifests, messaging, AI gateway, admin console.
- Security: app signing, E2EE, secrets, device attestation where available, abuse handling, audit logs.
- Operations: CI/CD, release rings, crash reporting, observability, backup/restore, support tools.
- Compliance: privacy, consent, emergency behavior, WhatsApp API compliance, Play policy, data retention.
- Product: branded UX, onboarding, support docs, beta feedback loop, retail install process.

## Immediate Next Steps

1. Freeze the MVP scope to six APKs: Launcher, Setup/Settings, Guardian, Assistant, Messenger, Store/Updater.
2. Define package names, signing strategy, release rings, and backend environments.
3. Build the setup wizard as the main conversion flow instead of relying only on scripts.
4. Stand up minimal backend APIs for account, device registration, catalog, and update manifest.
5. Document the WhatsApp boundary in product copy: official WhatsApp Business API for business messaging, Ai Manus Messenger for secure user chat.
6. Run a 5-device internal pilot, then a 50-device beta with device-owner provisioning.
