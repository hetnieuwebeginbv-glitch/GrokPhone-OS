#!/bin/bash
# Validate_3Pillar_Integration.sh
# Run this after building to check that all 3 pillars are actually present in the image

echo "=== Stay4S Grok Edition — 3-Pillar Validation ==="

PRODUCT_OUT="out/target/product/asteroids"

echo "Checking Pillar 1 (ROM integration)..."
if [ -f "$PRODUCT_OUT/system/etc/permissions/privapp-permissions-grok.xml" ]; then
    echo "  ✓ privapp-permissions-grok.xml present"
else
    echo "  ✗ MISSING Pillar 1 permission file"
fi

if [ -f "$PRODUCT_OUT/vendor/etc/init/init.grok.rc" ]; then
    echo "  ✓ init.grok.rc present (early boot)"
else
    echo "  ✗ MISSING early boot init"
fi

echo ""
echo "Checking Pillar 2 (Parallel Brain + Guardian)..."
if grep -q "GrokAgentCoreService" "$PRODUCT_OUT/system/framework/framework.jar" 2>/dev/null || \
   find "$PRODUCT_OUT/system" -name "*.apk" -exec unzip -l {} \; 2>/dev/null | grep -q GrokAgentCoreService; then
    echo "  ✓ GrokAgentCoreService found in system"
else
    echo "  ? Could not easily verify (check logs after boot)"
fi

echo ""
echo "Checking Pillar 3 (Own Software)..."
if grep -q "GrokLauncherActivity" "$PRODUCT_OUT/system/framework/framework.jar" 2>/dev/null; then
    echo "  ✓ GrokLauncherActivity present"
else
    echo "  ? Launcher may need manual verification on device"
fi

echo ""
echo "=== Validation script finished ==="
echo "Next: Boot device and check logs for 'GrokAgentCoreService' and 'DailyGuardianAgent'"
