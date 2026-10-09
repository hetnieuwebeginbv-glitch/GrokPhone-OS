#!/system/bin/sh
# postboot_force_grok_launcher.sh
# Forces com.xai.grok as default launcher after boot

sleep 20

PACKAGE="com.xai.grok"
COMPONENT="com.xai.grok/.launcher.GrokLauncherActivity"

# Clear other launchers
pm clear com.android.launcher3 2>/dev/null
pm clear com.google.android.apps.nexuslauncher 2>/dev/null

# Set as default
cmd package set-home-activity $COMPONENT 2>/dev/null

# Fallback
am start -a android.intent.action.MAIN -c android.intent.category.HOME -n $COMPONENT 2>/dev/null

echo "Grok set as default launcher" | tee /data/grok/launcher_force.log