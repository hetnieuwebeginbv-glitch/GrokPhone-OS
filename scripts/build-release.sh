#!/bin/bash
# Build alle Manus Ai Phone APKs en plaats ze in ./releases.

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RELEASE_DIR="$ROOT_DIR/releases"

build_module() {
    local module="$1"
    local output_name="$2"

    echo "==> Building $module"
    (cd "$ROOT_DIR/$module" && gradle :app:assembleRelease)
    mkdir -p "$RELEASE_DIR"
    cp "$ROOT_DIR/$module/app/build/outputs/apk/release/app-release.apk" "$RELEASE_DIR/$output_name"
    echo "==> Wrote releases/$output_name"
}

build_module "guardian-app" "guardian-app-release.apk"
build_module "manus-launcher" "manus-launcher-release.apk"
build_module "glyph-guardian" "glyph-guardian-release.apk"
build_module "manus-suite" "manus-suite-release.apk"

echo "==> Manus Ai Phone release build complete"
ls -lh "$RELEASE_DIR"
