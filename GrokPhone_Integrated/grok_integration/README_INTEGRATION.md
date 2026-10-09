# Stay4S Grok AI - Active Build Integration (May 2026)

## Current Build
- Started: 2026-05-27 ~19:54
- Target: stay4s_asteroids (Lineage 22.2 Baklava)
- Command: m -j1 otapackage
- Status: Early phase (product config + globs)

## Goal
Integrate the advanced Parallel Grok AI (GrokAgentCore with Partnership enforcement, explicit @grok triggers, isolated execution, privacy-first design) into this build via `packages/apps/Grok`.

## Files in this folder (apply in order)

1. `grok_rom_integration_v2.patch` - Main integration patch (hardened)
2. `sepolicy/` - SELinux policies for isolated `grok_agent` domain
3. `privapp-permissions-grok.xml` + `default-permissions-grok.xml`
4. Security properties and init additions (to be merged into device.mk / BoardConfig)

## Quick Apply Steps (on Linux build machine)

```bash
# 1. Stop current build if needed (careful)
# kill the current otapackage process

# 2. Sync these files into the tree
cp -r /mnt/c/Users/Gebruiker/Stay4S_Rom/grok_integration/sepolicy/* \
   device/nothing/asteroids/sepolicy/vendor/

# 3. Place the Grok app source
# (copy your full packages/apps/Grok/ from development machine if not already present)

# 4. Apply the main patch (or manually merge the changes)
cd /root/android/lineage
patch -p1 < /mnt/c/Users/Gebruiker/Stay4S_Rom/grok_integration/grok_rom_integration_v2.patch

# 5. Add security properties (example - merge into the right mk file)
# echo 'PRODUCT_PRODUCT_PROPERTIES += ro.grok.agent.enabled=true ro.grok.agent.sandboxed=true' >> ...

# 6. Re-lunch and restart build (or let it continue if early enough)
source build/envsetup.sh
lunch stay4s_asteroids-trunk_staging-userdebug
m -j1 otapackage 2>&1 | tee otapackage_grok_$(date +%Y%m%d_%H%M).log
```

## Security Principles Applied
- Least privilege for the Grok agent process
- Dedicated SELinux domain (grok_agent) instead of broad privapp
- Explicit consent model preserved (no silent broad access)
- Partnership verification hooks prepared
- Full audit logging path to GrokAdminSOS

## Next after this build
- Verified boot key migration (test keys → proper keys)
- Hardware kill-switch init service
- Full parallel reasoning engine in the agent (work in progress by parallel subagent)

Contact: Mitchell + Grok (Eternal Software Boss)
