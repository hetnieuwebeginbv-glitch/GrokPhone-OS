#!/bin/bash
# ═══════════════════════════════════════════════════════════════════════
# Ai Manus Phone — Verwijderscript
# Stay4Safe Ai © 2026
# ═══════════════════════════════════════════════════════════════════════

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
TEAL='\033[0;36m'
NC='\033[0m'
BOLD='\033[1m'

echo -e "${TEAL}Ai Manus Phone — Verwijderscript${NC}"
echo ""

DEVICE=$(adb devices | grep -v "List" | grep "device$" | head -1 | awk '{print $1}')
if [ -z "$DEVICE" ]; then
    echo -e "${RED}✗ Geen telefoon gevonden.${NC}"
    exit 1
fi

echo -e "${BOLD}Ai Manus Phone apps verwijderen...${NC}"

adb -s "$DEVICE" uninstall ai.stay4safe.guardian 2>/dev/null && \
    echo -e "${GREEN}✓ Guardian AI verwijderd${NC}" || \
    echo -e "${RED}✗ Guardian AI niet gevonden${NC}"

adb -s "$DEVICE" uninstall ai.stay4safe.launcher 2>/dev/null && \
    echo -e "${GREEN}✓ Manus Launcher verwijderd${NC}" || \
    echo -e "${RED}✗ Manus Launcher niet gevonden${NC}"

adb -s "$DEVICE" uninstall ai.stay4safe.glyph 2>/dev/null && \
    echo -e "${GREEN}✓ Glyph Guardian verwijderd${NC}" || \
    echo -e "${RED}✗ Glyph Guardian niet gevonden${NC}"

# Glyph debug mode uitzetten
adb -s "$DEVICE" shell settings put global nt_glyph_interface_debug_enable 0

echo ""
echo -e "${GREEN}✓ Klaar — Nothing Launcher is weer actief${NC}"
