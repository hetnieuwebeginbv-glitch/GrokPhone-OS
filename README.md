# Stay4OS GrokPhone OS

**Meta-repo** met local manifests, build scripts, en CI/CD configuratie voor het bouwen van Stay4OS op Nothing Phone 3a (asteroids).

## Snelstart

```bash
# 1. Build omgeving
./scripts/setup-build-env.sh

# 2. Broncode ophalen
cd ~/stay4os
repo init -u https://github.com/LineageOS/android.git -b lineage-23.2
cp .repo/local_manifests/asteroids.xml .repo/local_manifests/
repo sync -j$(nproc)

# 3. Vendor blobs (vereist Nothing Phone 3a met stock ROM)
cd device/nothing/asteroids
./extract-files.py

# 4. Bouwen
source build/envsetup.sh
lunch lineage_asteroids-userdebug
mka bacon -j$(nproc)
```

## Repository Structuur

| Repo | Doel |
|------|------|
| GrokPhone-OS | Meta-repo (hier) — local manifests, scripts, CI/CD |
| android_device_nothing_asteroids | Device tree (Stay4S fork, lineage-23.2) |
| android_kernel_nothing_sm7635 | Kernel source (Stay4S fork, lineage-23.2) |
| android_vendor_nothing_asteroids | Vendor blobs (PRIVATE) |
| android_packages_apps_AetherCore | AetherCore AI orchestrator service |
| android_packages_apps_Grok | Grok Agent Core — privileged AI agent |

## CI/CD

- build.yml — Nightly/build-on-push
- release.yml — Manuele release naar canary/beta/stable
- security-scan.yml — SELinux/AVB/security audit (TODO)

## Branch

**lineage-23.2** (ADR-0011A, H23) — geautoriseerd 2026-07-28.

## Gerelateerd

- Stay4S-SAIP: Documentatie, ADRs, architectuur
- stay4os-docs: Centrale documentatie hub (00_CENTRAAL)
