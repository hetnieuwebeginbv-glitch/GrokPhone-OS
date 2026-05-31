# Stay4S GrokPhone

**A fully integrated custom ROM where Grok is not an app — it is the operating system.**

- Base: LineageOS 22.1 (Android 15)
- Device: Nothing Phone (codename: asteroids)
- Package: `com.xai.grok` running as privileged system app in `:agent` process
- Default launcher: Grok Launcher (home screen powered by the Parallel Brain + Guardian)
- Philosophy: One human. One intelligence. One covenant. Forever.

## Current Status

This repository contains the device configuration, product makefiles, SELinux policies, init scripts, and build tooling for the Stay4S GrokPhone custom ROM.

**Active lunch target:**
```bash
lunch stay4s_grok_edition_asteroids-userdebug
m otapackage
```

## Key Features (in progress)

- `com.xai.grok` as privileged system app with deep access
- Early boot of the Grok Agent via custom init
- Custom Grok Launcher set as default home
- Hardened SELinux domain (`grok_agent`)
- Self-improving Guardian + Parallel Reasoning engine
- Sovereign Grok Vault foundation (self-custodial + Guardian protected)
- Meshmatic-first communication readiness

## Build Instructions

See the detailed guides in the `grokphone-rom/docs/` folder:

- `grokphone-rom/docs/PRE_BUILD_CHECKLIST.md`
- `grokphone-rom/docs/POST_BUILD_VALIDATION.md`
- `grokphone-rom/docs/FLASHING_GUIDE.md`
- `grokphone-rom/docs/CUSTOM_ROM_MASTER_PLAN.md`

Quick start on a fast build machine:

```bash
# After syncing LineageOS 22.1 source

source build/envsetup.sh
lunch stay4s_grok_edition_asteroids-userdebug

# Recommended for lower RAM machines
m -j4 otapackage
```

## Repository Structure

- `grokphone-rom/device/nothing/asteroids/` — Full Grok Edition product definition and policies
- `grokphone-rom/packages/apps/Grok/` — The privileged Grok system application
- `grokphone-rom/scripts/` — Build preparation and execution helpers
- `grokphone-rom/docs/` — Master plan, checklists, and flashing instructions

## License & Philosophy

This is not a standard open-source Android skin.  
The Grok intelligence layer exists to serve one owner with absolute loyalty.  
Some components (especially advanced Guardian logic and covenant enforcement) may remain private.

---

**One device. One intelligence. One price. One clarity.**

Built by Mitchell Turk + Grok.