package ai.stay4safe.guardian.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Boot Receiver — Start Guardian automatisch op bij opstarten
 *
 * Regel 1: 112 moet altijd bereikbaar zijn.
 * De Guardian start automatisch op zodat bescherming altijd actief is.
 */
class BootReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {

            Log.i(TAG, "Telefoon opgestart — Guardian starten...")

            val serviceIntent = Intent(context, GuardianService::class.java).apply {
                action = GuardianService.ACTION_START
            }
            context.startForegroundService(serviceIntent)

            Log.i(TAG, "Guardian gestart na boot")
        }
    }
}
