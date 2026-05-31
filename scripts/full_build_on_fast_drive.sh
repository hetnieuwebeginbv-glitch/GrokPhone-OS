#!/bin/bash
# full_build_on_fast_drive.sh
# Volledig geoptimaliseerd build script voor de fast drive (GrokPhone_Build)

set -e

echo "=============================================="
echo "   Stay4S GrokPhone - Full Custom ROM Build   "
echo "=============================================="

# === Environment optimalisatie ===
export USE_CCACHE=1
export CCACHE_DIR="${CCACHE_DIR:-/ccache}"
export CCACHE_COMPRESS=1
export CCACHE_COMPRESSLEVEL=6

# Voor low-RAM machines (aanpasbaar)
export GOMAXPROCS=4

# Maak ccache directory als die nog niet bestaat
mkdir -p "$CCACHE_DIR"

echo "[1/5] ccache initialiseren..."
ccache -M 80G
ccache -s | head -10

# === Source environment ===
echo "[2/5] Sourcing build environment..."
source build/envsetup.sh

# === Lunch de juiste target ===
echo "[3/5] Lunch target: stay4s_grok_edition_asteroids-userdebug"
lunch stay4s_grok_edition_asteroids-userdebug

# === Build commando ===
echo "[4/5] Start build (otapackage)..."
echo "Gebruik -j4 of lager bij lage RAM. Logging naar build_grokphone_$(date +%Y%m%d_%H%M).log"

START_TIME=$(date +%s)

m -j4 otapackage 2>&1 | tee "build_grokphone_$(date +%Y%m%d_%H%M).log"

END_TIME=$(date +%s)
DURATION=$((END_TIME - START_TIME))
echo ""
echo "Build duurde: $((DURATION / 3600)) uur $(( (DURATION % 3600) / 60 )) minuten"

# === Post-build info ===
echo "[5/5] Build voltooid."
echo ""
echo "OTA package zou hier moeten staan:"
echo "out/target/product/asteroids/stay4s_grok_edition_asteroids-ota-*.zip"
echo ""
echo "Volgende stappen:"
echo "1. Voer docs/POST_BUILD_VALIDATION.md uit"
echo "2. Gebruik docs/FLASHING_GUIDE.md om te flashen"
echo "3. Na flash: force_grok_launcher.sh zou moeten draaien (of handmatig via adb push + sh)"