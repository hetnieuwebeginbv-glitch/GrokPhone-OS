package ai.stay4safe.glyph

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class GlyphGuardianService : Service() {
    companion object {
        private const val TAG = "ManusAIGlyph"
        const val ACTION_START = "ai.stay4safe.glyph.START"
        const val ACTION_IDLE = "ai.stay4safe.glyph.IDLE"
        const val ACTION_ALERT = "ai.stay4safe.glyph.ALERT"
        const val ACTION_EMERGENCY = "ai.stay4safe.glyph.EMERGENCY"
        const val ACTION_AGENT_ACTIVE = "ai.stay4safe.glyph.AGENT_ACTIVE"
        const val ACTION_MESSAGE = "ai.stay4safe.glyph.MESSAGE"
        const val ACTION_PAYMENT = "ai.stay4safe.glyph.PAYMENT"
        const val ACTION_BROWSER_RISK = "ai.stay4safe.glyph.BROWSER_RISK"
        const val EXTRA_REASON = "reason"
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(
            2401,
            Notification.Builder(this, "glyph_guardian")
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_idle))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setOngoing(true)
                .build()
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val signal = ManusGlyphSignal.fromIntent(intent)
        Log.i(TAG, "Manus AI Glyph signal: ${signal.mode} reason=${signal.reason}")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

data class ManusGlyphSignal(
    val mode: ManusGlyphMode,
    val reason: String?
) {
    companion object {
        fun fromIntent(intent: Intent?): ManusGlyphSignal {
            val mode = when (intent?.action) {
                GlyphGuardianService.ACTION_START,
                GlyphGuardianService.ACTION_IDLE,
                null -> ManusGlyphMode.IDLE
                GlyphGuardianService.ACTION_ALERT -> ManusGlyphMode.ALERT
                GlyphGuardianService.ACTION_EMERGENCY -> ManusGlyphMode.EMERGENCY
                GlyphGuardianService.ACTION_AGENT_ACTIVE -> ManusGlyphMode.AGENT_ACTIVE
                GlyphGuardianService.ACTION_MESSAGE -> ManusGlyphMode.MESSAGE
                GlyphGuardianService.ACTION_PAYMENT -> ManusGlyphMode.PAYMENT
                GlyphGuardianService.ACTION_BROWSER_RISK -> ManusGlyphMode.BROWSER_RISK
                else -> ManusGlyphMode.ALERT
            }
            return ManusGlyphSignal(mode, intent?.getStringExtra(GlyphGuardianService.EXTRA_REASON))
        }
    }
}

enum class ManusGlyphMode {
    IDLE,
    ALERT,
    EMERGENCY,
    AGENT_ACTIVE,
    MESSAGE,
    PAYMENT,
    BROWSER_RISK
}
