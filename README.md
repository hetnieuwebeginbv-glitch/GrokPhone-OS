# Stay4OS GrokPhone OS

**Meta-repo** met local manifests, build scripts, en CI/CD configuratie voor het bouwen van Stay4OS op Nothing Phone 3a (asteroids).

## Snelstart

`ash
# 1. Build omgeving
./scripts/setup-build-env.sh

# 2. Broncode ophalen
cd ~/stay4os
repo init -u https://github.com/LineageOS/android.git -b lineage-22.1
cp .repo/local_manifests/asteroids.xml .repo/local_manifests/
repo sync -j\

# 3. Vendor blobs (vereist Nothing Phone 3a met stock ROM)
cd device/nothing/asteroids
./extract-files.py

# 4. Bouwen
source build/envsetup.sh
lunch lineage_asteroids-userdebug
mka bacon -j\
`

## Repository Structuur

| Repo | Doel |
|------|------|
 | GrokPhone-OS | Meta-repo (hier) |
| android_packages_apps_AetherCore | AetherCore AI service |
| device_nothing_asteroids | Device tree (fork van sayann70) |
| android_vendor_nothing_asteroids | Vendor blobs (PRIVATE) |

## CI/CD

- uild.yml — Nightly/build-on-push
- 
elease.yml — Manuele release naar canary/beta/stable
- security-scan.yml — SELinux/AVB/security audit

## Gerelateerd

- Stay4S-SAIP (kan alleen als je toegang hebt): Documentatie, ADRs, architectuur
