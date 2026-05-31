# Stay4S GrokPhone — Full Custom ROM

**One integrated custom OS.**  
`com.xai.grok` as privileged system app with its own launcher as default home, deep system access, and own boot experience.

**Base**: LineageOS 22.1 (Android 15)  
**Device**: Nothing Phone (asteroids)

## Current Lunch Target
```bash
lunch stay4s_grok_edition_asteroids-userdebug
m otapackage
```

## Quick Start on Build Machine
1. Copy this tree to fast drive (`GrokPhone_Build`)
2. Run `scripts/prepare_grokphone_build.ps1` (or the .sh version)
3. `source build/envsetup.sh`
4. `lunch stay4s_grok_edition_asteroids-userdebug`
5. `m -j4 otapackage`

## Key Files
- `device/nothing/asteroids/stay4s_grok_edition.mk` — Main product definition
- `packages/apps/Grok/` — The real Grok privileged system app
- `device/nothing/asteroids/sepolicy/` — Grok SELinux policy
- `device/nothing/asteroids/init/init.grok.rc` — Early boot

See `docs/CUSTOM_ROM_MASTER_PLAN.md` for the full roadmap.

**Primary GitHub Repository**: https://github.com/miesdevries/Stay4s-grokrom

This is no longer a collection of loose apps. This is one custom OS.
