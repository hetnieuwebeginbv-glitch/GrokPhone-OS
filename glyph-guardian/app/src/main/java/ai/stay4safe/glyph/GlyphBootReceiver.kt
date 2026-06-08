package ai.stay4safe.glyph

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GlyphBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            context.startForegroundService(
                Intent(context, GlyphGuardianService::class.java).apply {
                    action = GlyphGuardianService.ACTION_START
                }
            )
        }
    }
}
