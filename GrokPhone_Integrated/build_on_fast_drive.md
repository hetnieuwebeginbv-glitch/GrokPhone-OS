# How to Build the Full 3-Pillar Grok Edition on the Fast Drive

After copying `GrokPhone_Integrated` to your fast drive (using the included `copy_to_fast_drive.ps1`):

```powershell
# On the fast drive machine
cd C:\GrokPhone_Build\grokphone-integrated-20260601   # adjust path

# Standard AOSP/Lineage setup (you already have this from previous builds)
source build/envsetup.sh

# The exact Grok Edition target with all 3 pillars
lunch stay4s_grok_edition_asteroids-userdebug

# Build (use lower -j because of RAM limitations)
m -j4 otapackage 2>&1 | tee build_grok_edition_$(date +%Y%m%d).log
```

## After Build Succeeds

Images will be in:
`out/target/product/asteroids/`

Key files to flash:
- `stay4s_grok_edition_asteroids-ota-*.zip`
- Or the individual partition images for fastboot

## Validation on Device (after flash)

1. `adb logcat | grep -E 'Grok|stay4s.grok'`
2. Check SELinux domain: `adb shell ps -Z | grep grok`
3. The Grok launcher should be selectable as default home.
4. Guardian and Parallel Brain should be starting early.

This is the first real integrated build containing foundations of all three pillars.
