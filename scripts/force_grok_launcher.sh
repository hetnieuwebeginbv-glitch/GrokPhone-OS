#!/system/bin/sh
# force_grok_launcher.sh
# Post-boot script om Grok Launcher als default home screen te forceren
# Dit script moet in de ROM worden meegeleverd en via init of een privileged app worden gestart.

LOG_TAG="GrokForceLauncher"
PACKAGE="com.xai.grok"
ACTIVITY="com.xai.grok.launcher.GrokLauncherActivity"

log() {
    log -t "$LOG_TAG" "$1"
}

log "Starting Grok Launcher force script..."

# Wacht even tot PackageManager klaar is
sleep 15

# Verwijder eventuele bestaande default launcher voorkeur
pm clear com.android.launcher3 2>/dev/null || true
pm clear com.google.android.apps.nexuslauncher 2>/dev/null || true

# Stel Grok in als default launcher via settings
settings put secure default_input_method "$PACKAGE/$ACTIVITY" 2>/dev/null || true

# Forceer via ActivityManager (beste methode op veel LineageOS builds)
am start -a android.intent.action.MAIN -c android.intent.category.HOME -n "$PACKAGE/$ACTIVITY" 2>/dev/null || true

# Alternatief via cmd (Android 10+)
cmd package set-home-activity "$PACKAGE/$ACTIVITY" 2>/dev/null || true

log "Grok Launcher force attempt completed."

# Optioneel: herhaal na 30 seconden voor zekerheid
sleep 30
cmd package set-home-activity "$PACKAGE/$ACTIVITY" 2>/dev/null || true

log "Grok Launcher is now the default home screen (if permissions allow)."