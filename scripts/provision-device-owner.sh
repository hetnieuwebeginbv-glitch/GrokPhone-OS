#!/bin/bash
# Zet Manus Suite als device owner op een schoon/fresh Android toestel.
# Dit werkt alleen voordat er gebruikersaccounts op het toestel staan.

set -euo pipefail

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

DEVICE=$(adb devices | grep -v "List" | grep "device$" | head -1 | awk '{print $1}')
if [ -z "$DEVICE" ]; then
    echo -e "${RED}Geen ADB device gevonden.${NC}"
    exit 1
fi

echo "Device: $DEVICE"

if ! adb -s "$DEVICE" shell pm list packages | grep -q "ai.stay4safe.manus"; then
    echo -e "${YELLOW}Manus Suite niet gevonden; eerst installeren via ./scripts/install.sh${NC}"
    exit 1
fi

echo "Device-owner provisioning starten..."
adb -s "$DEVICE" shell dpm set-device-owner ai.stay4safe.manus/.ManusDeviceAdminReceiver

echo -e "${GREEN}Manus Managed OS device-owner provisioning voltooid.${NC}"
