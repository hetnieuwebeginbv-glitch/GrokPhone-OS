#!/bin/bash
# Manus Ai Phone flash readiness checker.
# This script is intentionally non-destructive: it does not unlock, wipe or flash.

set -euo pipefail

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
TEAL='\033[0;36m'
NC='\033[0m'

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
REPORT_DIR="$REPO_DIR/reports"
REPORT_FILE="$REPORT_DIR/flash-readiness.txt"

mkdir -p "$REPORT_DIR"

log() {
    echo "$1" | tee -a "$REPORT_FILE"
}

reset_report() {
    {
        echo "Manus Ai Phone Flash Readiness Report"
        echo "Generated: $(date -u +"%Y-%m-%dT%H:%M:%SZ")"
        echo "Repo: $REPO_DIR"
        echo
    } > "$REPORT_FILE"
}

require_command() {
    local command_name="$1"
    if ! command -v "$command_name" >/dev/null 2>&1; then
        echo -e "${RED}Missing required command: $command_name${NC}"
        log "MISSING: $command_name"
        return 1
    fi
    log "OK: command $command_name"
}

adb_prop() {
    local device="$1"
    local prop="$2"
    adb -s "$device" shell getprop "$prop" 2>/dev/null | tr -d '\r'
}

verify_apk() {
    local apk="$1"
    local apksigner_bin="${APKSIGNER:-}"

    if [ -z "$apksigner_bin" ]; then
        if command -v apksigner >/dev/null 2>&1; then
            apksigner_bin="apksigner"
        elif [ -x "/usr/local/share/android-commandlinetools/build-tools/35.0.0/apksigner" ]; then
            apksigner_bin="/usr/local/share/android-commandlinetools/build-tools/35.0.0/apksigner"
        fi
    fi

    if [ ! -f "$apk" ]; then
        log "APK MISSING: $apk"
        return
    fi

    if [ -z "$apksigner_bin" ]; then
        log "APK PRESENT, SIGNATURE NOT CHECKED: $apk"
        return
    fi

    if "$apksigner_bin" verify --verbose "$apk" >/tmp/manus-apksigner.out 2>&1; then
        log "APK SIGNATURE OK: $apk"
        grep "Verified using v2 scheme" /tmp/manus-apksigner.out | tee -a "$REPORT_FILE" >/dev/null || true
        grep "Number of signers" /tmp/manus-apksigner.out | tee -a "$REPORT_FILE" >/dev/null || true
    else
        log "APK SIGNATURE FAILED: $apk"
        cat /tmp/manus-apksigner.out >> "$REPORT_FILE"
    fi
}

reset_report

echo -e "${TEAL}Manus Ai Phone flash readiness checker${NC}"
echo "Report: $REPORT_FILE"
echo

require_command adb || exit 1

if command -v fastboot >/dev/null 2>&1; then
    log "OK: command fastboot"
else
    log "WARNING: fastboot not found"
fi

DEVICE="$(adb devices | awk '$2 == "device" {print $1; exit}')"
if [ -z "$DEVICE" ]; then
    echo -e "${RED}No ADB device found.${NC}"
    log "FAILED: no ADB device found"
    exit 1
fi

log "ADB DEVICE: $DEVICE"
log "MODEL: $(adb_prop "$DEVICE" ro.product.model)"
log "DEVICE CODENAME: $(adb_prop "$DEVICE" ro.product.device)"
log "PRODUCT: $(adb_prop "$DEVICE" ro.product.name)"
log "MANUFACTURER: $(adb_prop "$DEVICE" ro.product.manufacturer)"
log "ANDROID: $(adb_prop "$DEVICE" ro.build.version.release)"
log "SDK: $(adb_prop "$DEVICE" ro.build.version.sdk)"
log "FINGERPRINT: $(adb_prop "$DEVICE" ro.build.fingerprint)"
log "SECURITY PATCH: $(adb_prop "$DEVICE" ro.build.version.security_patch)"
log "BUILD TYPE: $(adb_prop "$DEVICE" ro.build.type)"
log "BUILD TAGS: $(adb_prop "$DEVICE" ro.build.tags)"
log "ACTIVE SLOT: $(adb_prop "$DEVICE" ro.boot.slot_suffix)"
log "BOOT VERIFIED STATE: $(adb_prop "$DEVICE" ro.boot.verifiedbootstate)"
log "BOOT FLASH LOCKED: $(adb_prop "$DEVICE" ro.boot.flash.locked)"
log "BOOT VBMETA DEVICE STATE: $(adb_prop "$DEVICE" ro.boot.vbmeta.device_state)"
log "DEVICE OWNER: $(adb -s "$DEVICE" shell dumpsys device_policy 2>/dev/null | grep -m 1 'Device Owner' | tr -d '\r' || true)"

log
log "APK CHECKS"
verify_apk "$REPO_DIR/releases/guardian-app-release.apk"
verify_apk "$REPO_DIR/releases/manus-launcher-release.apk"
verify_apk "$REPO_DIR/releases/glyph-guardian-release.apk"
verify_apk "$REPO_DIR/releases/manus-suite-release.apk"

log
log "FLASH READINESS DECISION"
log "READY FOR APK INSTALL: yes, if USB debugging is authorized and APK checks pass."
log "READY FOR DEVICE OWNER: only on a fresh/reset device with no personal accounts."
log "READY FOR ROM FLASH: no, not until tested images, device tree, vendor blobs, unlock plan, backup and rollback plan exist."
log "NEXT SAFE STEP: run ./scripts/install.sh for APK layer, or MANUS_ENABLE_DEVICE_OWNER=1 ./scripts/install.sh on a fresh managed test device."

echo
echo -e "${GREEN}Readiness report written: $REPORT_FILE${NC}"
