# Stay4S GrokPhone — Custom ROM Master Plan (LineageOS 22.1 / Android 15)

**Project**: Full custom ROM for Nothing Phone (asteroids)  
**Codename**: stay4s_grok_edition_asteroids  
**Base**: LineageOS 22.1 (Android 15)  
**Goal**: One integrated custom OS with `com.xai.grok` as privileged system app, own launcher as default, deep system integration, own boot experience.

**Date**: June 2026  
**Working Tree**: `C:\Users\Gebruiker\GrokPhone` (the clean custom ROM repo)  
**Primary GitHub Repo**: https://github.com/miesdevries/Stay4s-grokrom  
**Build Machine Target**: GrokPhone_Build fast drive

---

## 1. Current State — What We Already Have

### Device Tree (minimal but present)
- `device/nothing/asteroids/` basic structure
- `BoardConfig.mk`
- `device.mk` (basic)
- `stay4s_asteroids.mk` (early Grok product file)
- Basic SELinux: `sepolicy/grok.te`
- Basic permissions: `grok/privapp-permissions-grok.xml`
- `packages/apps/Grok/` skeleton (Android.bp + AndroidManifest.xml)
- `README.md` and `scripts/` folder (mostly empty)

### Previous Work
- Some privileged app configuration
- Early launcher files mentioned in history
- Experience from previous integration attempts in other trees (Stay4S_Rom, GrokPhone_Integrated)

### Hardware & Build
- Dedicated fast build drive: `GrokPhone_Build`
- Low-RAM laptop (builds take ~4 days)
- Nothing Phone (asteroids) hardware available for testing

---

## 2. What We Still Need to Do (Concrete Work Items)

### 1. Full Device Tree Optimization for asteroids (Grok Edition)
- Proper `AndroidProducts.mk` with lunch target `stay4s_grok_edition_asteroids-userdebug`
- Strong `stay4s_grok_edition.mk` that inherits LineageOS + device.mk and adds all Grok features
- Clean separation between base device and Grok Edition overlay
- Add Grok-specific overlays (framework, SystemUI, Settings, etc.)
- Default home launcher configuration

### 2. Deep Integration of com.xai.grok as Privileged System App
- Update `packages/apps/Grok/AndroidManifest.xml` for:
  - Package `com.xai.grok`
  - `android:process=":agent"`
  - `privileged="true"`
  - Full telecom rights (`BIND_TELECOM_CONNECTION_SERVICE`, etc.)
  - `FOREGROUND_SERVICE_SPECIAL_USE`
  - AccessibilityService for ToolUse
  - Boot receiver
- Proper `Android.bp` with platform certificate + privileged flag
- Product makefile that installs it correctly

### 3. Extensive SELinux Policy
- Expand `sepolicy/grok.te` with full rules for:
  - `:agent` process
  - Guardian daily tasks
  - Vault access
  - Meshmatic / LoRa
  - System property access
  - File contexts
- Add `file_contexts`, `seapp_contexts`, `property_contexts`
- Create `grok_agent.te` (isolated domain)

### 4. Custom Grok Launcher as Default Home Screen
- Make Grok the default launcher via product config (`PRODUCT_PACKAGE_OVERLAYS` or `PRODUCT_DEFAULT_DEV_CERTIFICATE` + settings)
- Add `overlay/` for frameworks/base and packages/apps/Launcher3
- Register `GrokLauncherActivity` with HOME category
- Boot-time default launcher setting

### 5. Build Configuration & Optimization
- ccache setup script
- Proper lunch target configuration
- `m otapackage` with correct flags for low-RAM machine
- Build logging and artifact collection
- Pre-build validation checks

### 6. First Real OTA Build Preparation
- Full tree copy to `GrokPhone_Build`
- Environment setup scripts (PowerShell + bash)
- Post-build validation checklist
- Fastboot / OTA flashing instructions
- First hardware validation steps

---

## 3. Phased Execution Plan

**Phase A — Foundation (Now)**
- Create proper product makefiles and lunch target
- Update Grok app manifest + bp for real privileged + :agent + telecom
- Basic SELinux expansion
- Custom launcher registration

**Phase B — Deep Integration**
- Full SELinux policy (grok_agent domain)
- Init scripts (`init.grok.rc`)
- Boot experience + early agent start
- Default launcher enforcement

**Phase C — Build & Validation**
- Build scripts for fast drive
- First `otapackage` build on GrokPhone_Build
- Hardware flash + validation
- Iterate on errors

---

## 4. Progress (as of latest session)

**Recent concrete deliverables ("allemaal" + GitHub focus):**
- `scripts/sync_to_correct_repo.ps1` — Kant-en-klaar synchronisatie script naar de officiële repo
- `scripts/force_grok_launcher.sh` — Post-boot script om Grok als default launcher te forceren
- `scripts/full_build_on_fast_drive.sh` — Volledig geoptimaliseerd build script met ccache
- Sterke `device.mk` + `stay4s_grok_edition.mk` met deep telecom, privileged app en launcher forcering
- `device/nothing/asteroids/grok/force_launcher.mk` — Include voor het bundelen van het force script
- Alle vorige deliverables (SELinux, init, manifest, etc.) blijven van kracht

Alles is nu gericht op één sterke, toekomstbestendige custom ROM in de repo https://github.com/miesdevries/Stay4s-grokrom
```

The foundation for a real custom ROM build is now in place.

**Next recommended actions:**
- Copy tree to fast drive and run first build
- Flash and validate on hardware
- Iterate on SELinux denials and missing permissions

---

**This document is the living master plan for the Stay4S GrokPhone custom ROM.**

From this point forward, all work is focused exclusively on making one integrated custom OS in the `GrokPhone` tree.

No more loose pieces. One ROM. One Grok. One covenant.