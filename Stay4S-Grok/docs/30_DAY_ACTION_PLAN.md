# 30-Day Action Plan – Stay4S GrokPhone Custom ROM

**Based on**: FULL_PROJECT_AUDIT_REPORT (31 May 2026)  
**Overall Goal**: Bring the project from ~2/10 maturity to a **first buildable and flashable custom ROM** that actually feels like a Grok-native OS.

**Core Problems to Solve**:
- Package naming chaos (com.xai.grok vs com.stay4s.grok)
- AI source code (Parallel Brain + Guardian) not in the build
- GitHub repo is almost empty and structurally broken
- Too much stock LineageOS/AOSP still present
- Many declared components do not exist

---

## Week 1: Critical Foundations (Fix the Blockers)

**Goal**: Make the project structurally consistent so it can actually compile something meaningful.

### Day 1–2: Package Naming Unification (Highest Priority)
- **Decision**: Standardize on `com.xai.grok` everywhere (recommended, because all device tree, overlays, SELinux, manifests and docs already use this).
- **Actions**:
  - Rename/move all Kotlin sources from `agent/` and `packages/apps/Grok/src/com/stay4s/grok/` to `packages/apps/Grok/src/com/xai/grok/`
  - Update every `package com.stay4s.grok` declaration to `package com.xai.grok`
  - Update all imports across the agent code
  - Update `AndroidManifest.xml` (already correct) and cross-check against `Android.bp`
- **Files to touch**:
  - All files under `agent/parallel/`, `agent/guardian/`, `agent/tools/`, etc.
  - `packages/apps/Grok/AndroidManifest.xml` (verify)
  - Any remaining references in docs

### Day 3–4: Fix GitHub Sync & Repo Structure
- **Deprecate** `sync_rom_to_repo.ps1` and the `grokphone-rom/` subfolder approach (it is actively harmful).
- Make `sync_to_correct_repo.ps1` the single source of truth and improve it:
  - Add automatic pull before sync
  - Add validation step (check for key files)
  - Sync the full relevant tree to root (not subfolder)
- Clean up the local `grokphone-rom/` folder or mark it as deprecated.
- Perform a clean publish of the current best state to `main` on GitHub.

### Day 5–7: Make the Grok App Actually Build
- Move real AI sources into `packages/apps/Grok/src/com/xai/grok/`
- Create missing stub classes where needed so the module compiles:
  - `GrokApplication.kt`
  - `GrokLauncherActivity.kt` (minimal working version)
  - `GrokBootReceiver.kt`
- Update `Android.bp` if necessary
- Add a basic `res/values/strings.xml` and launcher icon

**Deliverable end of Week 1**: The Grok app module compiles and installs as a privileged app. Package naming is 100% consistent.

---

## Week 2: Buildability & Minimal Bootable Image

**Goal**: Reach a state where `lunch stay4s_grok_edition_asteroids-userdebug && m -j4 otapackage` succeeds without fatal errors.

### Day 8–10: Device Tree Hardening
- Adopt or create a realistic `BoardConfig.mk` for asteroids (or inherit properly from upstream)
- Add proper partition sizes, kernel command line, bootloader settings
- Create or integrate `extract-files.py` + `proprietary-files.txt`
- Ensure `device.mk` properly inherits necessary hardware features

### Day 11–12: Stop Breaking the UI
- Remove or comment out the dangerous `SystemUI` removal in `grok/systemui/grok_systemui.mk`
- Replace with strong overlays instead of removal
- Create initial `grok/systemui/overlay/` with basic customizations

### Day 13–14: First Real Build Attempt
- Run a full build on the fast drive
- Document every error
- Fix the top 5–10 blocking issues

**Deliverable end of Week 2**: First successful `otapackage` that produces an image (even if it doesn't fully boot yet).

---

## Week 3: Make It Feel Like Our Own OS

**Goal**: Dramatically reduce the "LineageOS + Grok" feeling.

### Day 15–17: Aggressive Stock Removal + Branding
- Expand `grok_remove.mk` significantly (more launchers, browsers, system apps, Lineage-specific packages)
- Complete `grok_branding.mk` (custom bootanimation.zip, sounds, stronger product properties)
- Heavy framework + Settings string overlays to remove "Android" and "LineageOS" references

### Day 18–19: Default Launcher Enforcement
- Make Grok Launcher the **only** launcher (stronger removal + better forcing)
- Improve or replace the current `force_grok_launcher.sh` with a more reliable method (privileged component or init-based)

### Day 20–21: Early Grok Presence
- Improve `init.grok.rc` so the agent starts very early and reliably
- Add basic boot animation and sounds

**Deliverable end of Week 3**: A build that feels noticeably different from stock LineageOS when booted (Grok Launcher as default, different branding, early agent start).

---

## Week 4: AI Layer Integration & Polish

**Goal**: Start making the Parallel Brain + Guardian actually functional in the running system.

### Day 22–24: AI Source Integration
- Ensure the full `agent/` code (with proper `com.xai.grok` package) is part of the build
- Wire `GrokAgentCoreService` to actually start `ParallelOrchestrator` + `DailyGuardianAgent`
- Create minimal but working versions of missing support classes

### Day 25–26: Guardian + System Integration
- Give the Guardian real (safe) system access via the new `grok_system.mk` / privileged paths
- Implement first useful Guardian tasks (e.g. daily privacy scan, network optimization suggestions)

### Day 27–28: Grok Vault as Real Component
- Turn Grok Vault from a stub into a functional privileged component
- Wire basic Guardian protection + Meshmatic hooks

### Day 29–30: Validation & Documentation
- Full build + flash on real hardware
- Run the existing validation checklists
- Update `FULL_PROJECT_AUDIT_REPORT.md` with new maturity score
- Create a "What works / What doesn't" status document

**Deliverable end of Week 4**: A flashable build where:
- Grok Launcher is default
- The agent process starts
- Basic Guardian functionality runs
- The ROM clearly feels like "GrokPhone" instead of LineageOS

---

## Success Metrics After 30 Days

- First successful flashable OTA that boots on real hardware
- Grok Launcher is the default and only launcher
- `com.xai.grok` package naming is 100% consistent
- Parallel Brain + Guardian code is actually present and running
- Clear reduction in stock LineageOS/Android references in the UI
- GitHub repo contains a usable device tree + build instructions

---

## Immediate Next Action (Today / Tomorrow)

1. Decide on final package name (`com.xai.grok` recommended).
2. Start the package rename + source move (Week 1 Day 1–2).
3. Run the improved sync script and push a clean state to GitHub.

---

**This plan is aggressive but realistic.**  
It focuses on the highest-leverage problems identified in the audit report while steadily moving toward the long-term vision of a true sovereign Grok operating system.

Would you like me to break Week 1 into daily tasks with exact file changes? Or start by creating the first set of files for the package rename?