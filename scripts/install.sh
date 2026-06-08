#!/bin/bash
# ═══════════════════════════════════════════════════════════════════════
# Ai Manus Phone — Automatisch Installatiescript
# Stay4Safe Ai © 2026
#
# Gebruik: ./install.sh
# Vereisten: ADB geïnstalleerd, Nothing Phone 3a verbonden via USB
#            Developer Mode + USB debugging ingeschakeld
# ═══════════════════════════════════════════════════════════════════════

set -e

# Kleuren voor output
RED='\033[0;31m'
GREEN='\033[0;32m'
TEAL='\033[0;36m'
YELLOW='\033[1;33m'
BOLD='\033[1m'
NC='\033[0m'

# Banner
echo -e "${TEAL}"
echo "  ╔═══════════════════════════════════════════════════╗"
echo "  ║         Ai MANUS PHONE — INSTALLATIE              ║"
echo "  ║         Stay4Safe Ai Telecom © 2026               ║"
echo "  ╚═══════════════════════════════════════════════════╝"
echo -e "${NC}"

# ─── Stap 1: Controleer ADB ─────────────────────────────────────────
echo -e "${BOLD}[1/8] ADB controleren...${NC}"
if ! command -v adb &> /dev/null; then
    echo -e "${RED}✗ ADB niet gevonden. Download via: https://developer.android.com/tools/releases/platform-tools${NC}"
    exit 1
fi
echo -e "${GREEN}✓ ADB gevonden${NC}"

# ─── Stap 2: Telefoon detecteren ────────────────────────────────────
echo -e "${BOLD}[2/8] Nothing Phone 3a detecteren...${NC}"
DEVICE=$(adb devices | grep -v "List" | grep "device$" | head -1 | awk '{print $1}')

if [ -z "$DEVICE" ]; then
    echo -e "${RED}✗ Geen telefoon gevonden. Controleer USB-verbinding en USB-debugging.${NC}"
    exit 1
fi

MODEL=$(adb -s "$DEVICE" shell getprop ro.product.model 2>/dev/null | tr -d '\r')
ANDROID_VER=$(adb -s "$DEVICE" shell getprop ro.build.version.release 2>/dev/null | tr -d '\r')

echo -e "${GREEN}✓ Verbonden: $MODEL (Android $ANDROID_VER)${NC}"

# Controleer of het een Nothing Phone 3a is
if [[ "$MODEL" != *"A059"* ]] && [[ "$MODEL" != *"Nothing Phone 3a"* ]]; then
    echo -e "${YELLOW}⚠ Waarschuwing: Dit script is geoptimaliseerd voor Nothing Phone 3a.${NC}"
    echo -e "${YELLOW}  Gedetecteerd model: $MODEL${NC}"
    read -p "  Toch doorgaan? (j/n): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Jj]$ ]]; then
        exit 1
    fi
fi

# ─── Stap 3: APKs controleren ───────────────────────────────────────
echo -e "${BOLD}[3/8] APK-bestanden controleren...${NC}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

GUARDIAN_APK="${GUARDIAN_APK:-$REPO_DIR/releases/guardian-app-release.apk}"
LAUNCHER_APK="${LAUNCHER_APK:-$REPO_DIR/releases/manus-launcher-release.apk}"
GLYPH_APK="${GLYPH_APK:-$REPO_DIR/releases/glyph-guardian-release.apk}"
SUITE_APK="${SUITE_APK:-$REPO_DIR/releases/manus-suite-release.apk}"

build_if_missing() {
    local module_dir="$1"
    local apk_path="$2"

    if [ -f "$apk_path" ]; then
        return
    fi

    echo -e "${YELLOW}  APK ontbreekt, release-build starten: $module_dir${NC}"
    if ! command -v gradle &> /dev/null; then
        echo -e "${RED}✗ Gradle niet gevonden. Installeer Gradle of bouw de APK handmatig.${NC}"
        exit 1
    fi
    (cd "$REPO_DIR/$module_dir" && gradle :app:assembleRelease)

    case "$module_dir" in
        guardian-app)
            mkdir -p "$REPO_DIR/releases"
            cp "$REPO_DIR/guardian-app/app/build/outputs/apk/release/app-release.apk" "$apk_path"
            ;;
        manus-launcher)
            mkdir -p "$REPO_DIR/releases"
            cp "$REPO_DIR/manus-launcher/app/build/outputs/apk/release/app-release.apk" "$apk_path"
            ;;
        glyph-guardian)
            mkdir -p "$REPO_DIR/releases"
            cp "$REPO_DIR/glyph-guardian/app/build/outputs/apk/release/app-release.apk" "$apk_path"
            ;;
        manus-suite)
            mkdir -p "$REPO_DIR/releases"
            cp "$REPO_DIR/manus-suite/app/build/outputs/apk/release/app-release.apk" "$apk_path"
            ;;
    esac
}

build_if_missing "guardian-app" "$GUARDIAN_APK"
build_if_missing "manus-launcher" "$LAUNCHER_APK"
build_if_missing "glyph-guardian" "$GLYPH_APK"
build_if_missing "manus-suite" "$SUITE_APK"

for apk in "$GUARDIAN_APK" "$LAUNCHER_APK" "$GLYPH_APK" "$SUITE_APK"; do
    if [ ! -f "$apk" ]; then
        echo -e "${RED}✗ APK niet gevonden: $apk${NC}"
        echo -e "${YELLOW}  Bouw lokaal met: gradle :app:assembleRelease in de betreffende module.${NC}"
        exit 1
    fi
    echo -e "${GREEN}✓ $(basename $apk)${NC}"
done

# ─── Stap 4: Stay4S Guardian installeren ────────────────────────────
echo -e "${BOLD}[4/8] Stay4S Guardian AI installeren...${NC}"
adb -s "$DEVICE" install -r "$GUARDIAN_APK"
echo -e "${GREEN}✓ Guardian AI geïnstalleerd${NC}"

# ─── Stap 5: Manus Launcher installeren ─────────────────────────────
echo -e "${BOLD}[5/8] Manus Launcher installeren...${NC}"
adb -s "$DEVICE" install -r "$LAUNCHER_APK"
echo -e "${GREEN}✓ Manus Launcher geïnstalleerd${NC}"

# ─── Stap 6: Glyph Guardian installeren ─────────────────────────────
echo -e "${BOLD}[6/8] Manus AI Glyph installeren...${NC}"
adb -s "$DEVICE" install -r "$GLYPH_APK"

# Glyph debug mode activeren (vereist voor SDK zonder API key)
echo -e "  Glyph debug mode activeren..."
adb -s "$DEVICE" shell settings put global nt_glyph_interface_debug_enable 1
echo -e "${GREEN}✓ Manus AI Glyph geïnstalleerd en debug mode actief${NC}"

# ─── Stap 7: Manus Suite installeren ────────────────────────────────
echo -e "${BOLD}[7/8] Manus Suite installeren...${NC}"
adb -s "$DEVICE" install -r "$SUITE_APK"
echo -e "${GREEN}✓ Manus Suite geïnstalleerd${NC}"

if [ "${MANUS_ENABLE_DEVICE_OWNER:-0}" = "1" ]; then
    echo -e "${YELLOW}  Device-owner provisioning proberen...${NC}"
    adb -s "$DEVICE" shell dpm set-device-owner ai.stay4safe.manus/.ManusDeviceAdminReceiver || {
        echo -e "${YELLOW}  Device-owner niet gezet. Dit werkt alleen op een schoon/fresh toestel zonder accounts.${NC}"
    }
fi

# ─── Stap 8: Configuratie & Launcher instellen ──────────────────────
echo -e "${BOLD}[8/8] Manus Launcher instellen als standaard...${NC}"

# Launcher instellen
adb -s "$DEVICE" shell cmd package set-home-activity ai.stay4safe.launcher/ai.stay4safe.launcher.ui.home.HomeActivity 2>/dev/null || true

# Guardian autostart inschakelen
adb -s "$DEVICE" shell am start-foreground-service -n ai.stay4safe.guardian/ai.stay4safe.guardian.service.GuardianService 2>/dev/null || true
adb -s "$DEVICE" shell am start-foreground-service -n ai.stay4safe.glyph/ai.stay4safe.glyph.GlyphGuardianService -a ai.stay4safe.glyph.IDLE 2>/dev/null || true
adb -s "$DEVICE" shell am start -n ai.stay4safe.manus/ai.stay4safe.manus.MainActivity 2>/dev/null || true

echo -e "${GREEN}✓ Configuratie voltooid${NC}"

# ─── Klaar! ──────────────────────────────────────────────────────────
echo ""
echo -e "${TEAL}╔═══════════════════════════════════════════════════╗${NC}"
echo -e "${TEAL}║  ✅  INSTALLATIE SUCCESVOL AFGEROND!              ║${NC}"
echo -e "${TEAL}╠═══════════════════════════════════════════════════╣${NC}"
echo -e "${TEAL}║                                                   ║${NC}"
echo -e "${TEAL}║  Je Nothing Phone 3a is nu een Ai Manus Phone!   ║${NC}"
echo -e "${TEAL}║                                                   ║${NC}"
echo -e "${TEAL}║  Volgende stappen:                                ║${NC}"
echo -e "${TEAL}║  1. Ontgrendel je telefoon                        ║${NC}"
echo -e "${TEAL}║  2. Kies 'Manus Launcher' als standaard           ║${NC}"
echo -e "${TEAL}║  3. Open Manus Suite voor Setup/Store/Chat        ║${NC}"
echo -e "${TEAL}║  4. Open Guardian en stel noodcontacten in        ║${NC}"
echo -e "${TEAL}║                                                   ║${NC}"
echo -e "${TEAL}║  Stay4Safe Ai Telecom © 2026                      ║${NC}"
echo -e "${TEAL}╚═══════════════════════════════════════════════════╝${NC}"
echo ""
