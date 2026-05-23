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
echo -e "${BOLD}[1/7] ADB controleren...${NC}"
if ! command -v adb &> /dev/null; then
    echo -e "${RED}✗ ADB niet gevonden. Download via: https://developer.android.com/tools/releases/platform-tools${NC}"
    exit 1
fi
echo -e "${GREEN}✓ ADB gevonden${NC}"

# ─── Stap 2: Telefoon detecteren ────────────────────────────────────
echo -e "${BOLD}[2/7] Nothing Phone 3a detecteren...${NC}"
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
echo -e "${BOLD}[3/7] APK-bestanden controleren...${NC}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
GUARDIAN_APK="$SCRIPT_DIR/guardian-app-release.apk"
LAUNCHER_APK="$SCRIPT_DIR/manus-launcher-release.apk"
GLYPH_APK="$SCRIPT_DIR/glyph-guardian-release.apk"

for apk in "$GUARDIAN_APK" "$LAUNCHER_APK" "$GLYPH_APK"; do
    if [ ! -f "$apk" ]; then
        echo -e "${RED}✗ APK niet gevonden: $apk${NC}"
        echo -e "${YELLOW}  Download de APKs via: https://github.com/hetnieuwebeginbv-glitch/M-Ai-Phone/releases${NC}"
        exit 1
    fi
    echo -e "${GREEN}✓ $(basename $apk)${NC}"
done

# ─── Stap 4: Stay4S Guardian installeren ────────────────────────────
echo -e "${BOLD}[4/7] Stay4S Guardian AI installeren...${NC}"
adb -s "$DEVICE" install -r "$GUARDIAN_APK"
echo -e "${GREEN}✓ Guardian AI geïnstalleerd${NC}"

# ─── Stap 5: Manus Launcher installeren ─────────────────────────────
echo -e "${BOLD}[5/7] Manus Launcher installeren...${NC}"
adb -s "$DEVICE" install -r "$LAUNCHER_APK"
echo -e "${GREEN}✓ Manus Launcher geïnstalleerd${NC}"

# ─── Stap 6: Glyph Guardian installeren ─────────────────────────────
echo -e "${BOLD}[6/7] Glyph Guardian installeren...${NC}"
adb -s "$DEVICE" install -r "$GLYPH_APK"

# Glyph debug mode activeren (vereist voor SDK zonder API key)
echo -e "  Glyph debug mode activeren..."
adb -s "$DEVICE" shell settings put global nt_glyph_interface_debug_enable 1
echo -e "${GREEN}✓ Glyph Guardian geïnstalleerd en debug mode actief${NC}"

# ─── Stap 7: Configuratie & Launcher instellen ──────────────────────
echo -e "${BOLD}[7/7] Manus Launcher instellen als standaard...${NC}"

# Launcher instellen
adb -s "$DEVICE" shell cmd package set-home-activity ai.stay4safe.launcher/.ui.home.HomeActivity 2>/dev/null || true

# Guardian autostart inschakelen
adb -s "$DEVICE" shell am start-foreground-service -n ai.stay4safe.guardian/.service.GuardianService 2>/dev/null || true

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
echo -e "${TEAL}║  3. Open Guardian en stel noodcontacten in        ║${NC}"
echo -e "${TEAL}║  4. De Glyph LEDs zijn nu Guardian-indicators     ║${NC}"
echo -e "${TEAL}║                                                   ║${NC}"
echo -e "${TEAL}║  Stay4Safe Ai Telecom © 2026                      ║${NC}"
echo -e "${TEAL}╚═══════════════════════════════════════════════════╝${NC}"
echo ""
