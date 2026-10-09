# Build Instructions – Stay4S GrokPhone

This document explains how to build the custom ROM from source.

## 1. Requirements

- Ubuntu 22.04 or 24.04 (recommended)
- At least 300 GB free disk space (SSD strongly preferred)
- 16 GB+ RAM (32 GB+ recommended for comfortable builds)
- Java 17 (OpenJDK)

## 2. Initialize LineageOS 22.1 Source

```bash
mkdir -p ~/android/lineage-22.1
cd ~/android/lineage-22.1

repo init -u https://github.com/LineageOS/android.git -b lineage-22.1

# Add the GrokPhone roomservice
mkdir -p .repo/local_manifests
cp /path/to/your/GrokPhone/roomservice.xml .repo/local_manifests/roomservice.xml

repo sync -c -j$(nproc)
```

## 3. Place the GrokPhone Configuration

The content of your local `GrokPhone/device/nothing/asteroids/` should end up in:

`device/nothing/asteroids/`

The content of `GrokPhone/packages/apps/Grok/` should be in:

`packages/apps/Grok/`

You can either:
- Copy the folders manually, or
- Use the `sync_to_correct_repo.ps1` script as inspiration for a sync workflow.

## 4. Extract Vendor Blobs

```bash
cd device/nothing/asteroids
./extract-files.py /path/to/your/dumped/stock/rom
```

## 5. Build

```bash
source build/envsetup.sh
lunch stay4s_grok_edition_asteroids-userdebug

# For machines with limited RAM:
m -j4 otapackage
```

The OTA will be located at:

`out/target/product/asteroids/stay4s_grok_edition_asteroids-ota-*.zip`

## 6. After Building

See:
- `docs/POST_BUILD_VALIDATION.md`
- `docs/FLASHING_GUIDE.md`

## Notes

- The `roomservice.xml` in this folder is a template. Adjust kernel and vendor projects as needed.
- Never commit proprietary blobs to this repository.
- Use ccache for much faster incremental builds.