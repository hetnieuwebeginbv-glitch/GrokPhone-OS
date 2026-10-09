# Post-Build Validation – Stay4S GrokPhone

After `m otapackage` completes, check the following:

## 1. Build Artifacts
- [ ] OTA zip exists: `out/target/product/asteroids/stay4s_grok_edition_asteroids-ota-*.zip`
- [ ] No critical errors in build log related to "Grok" or "com.xai.grok"

## 2. Package Verification
- [ ] Grok app is included (check in system or product partition)
- [ ] `privapp-permissions-grok.xml` is present in the image

## 3. SELinux (after flashing)
On device after boot:
```bash
adb shell ps -Z | grep grok
adb shell getprop | grep grok
```

Expected:
- GrokAgentCoreService running under `grok_agent` or `grok_app` domain
- Properties like `ro.stay4s.grok.edition=true` visible

## 4. Launcher Test
- Grok should appear in launcher list
- Attempt to set as default home

## 5. Boot Experience
- Check `adb logcat -b all | grep -i grok` for early agent start messages

Report any issues back with logs.
