#!/bin/bash
# build_grokphone.sh
# Run this on the fast drive after copying the tree

set -e

echo "=== Stay4S GrokPhone Custom ROM Build ==="

# ccache optimization (recommended)
export USE_CCACHE=1
export CCACHE_DIR=${CCACHE_DIR:-/ccache}
ccache -M 50G 2>/dev/null || true

source build/envsetup.sh

lunch stay4s_grok_edition_asteroids-userdebug

echo "Starting build (low -j4 recommended for low-RAM machines)..."
m -j4 otapackage 2>&1 | tee build_grokphone_$(date +%Y%m%d_%H%M).log

echo ""
echo "Build finished."
echo "OTA package location: out/target/product/asteroids/"
