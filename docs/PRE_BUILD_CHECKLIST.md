# Pre-Build Checklist – Stay4S GrokPhone Custom ROM

Before starting the build on the fast drive, verify the following:

## 1. Tree Preparation
- [ ] Full GrokPhone tree copied cleanly to fast drive (use prepare_grokphone_build.ps1)
- [ ] No old `out/` directory (or ccache is set up correctly)
- [ ] Enough free space on fast drive (minimum 150-200 GB recommended)

## 2. Environment
- [ ] `USE_CCACHE=1` and `CCACHE_DIR` set
- [ ] ccache size allocated (e.g. `ccache -M 50G`)
- [ ] Correct lunch target: `stay4s_grok_edition_asteroids-userdebug`

## 3. Key Files Present
- [ ] `device/nothing/asteroids/stay4s_grok_edition.mk`
- [ ] `device/nothing/asteroids/init/init.grok.rc`
- [ ] `packages/apps/Grok/AndroidManifest.xml` (com.xai.grok, privileged, :agent)
- [ ] SELinux policies in `sepolicy/` (grok.te, seapp_contexts, etc.)
- [ ] `grok/privapp-permissions-grok.xml`

## 4. Build Flags (Low RAM machine)
- Recommended: `m -j4 otapackage`
- Use `GOMAXPROCS=2` or lower if needed

Run this checklist before every serious build.
