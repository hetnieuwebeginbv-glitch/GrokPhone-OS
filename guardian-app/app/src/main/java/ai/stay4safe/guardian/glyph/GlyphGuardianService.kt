package ai.stay4safe.guardian.glyph

import ai.stay4safe.guardian.model.ThreatLevel
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class GlyphGuardianService : Service() {
    companion object {
        private const val TAG = "GlyphGuardian"

        const val ACTION_START = "ai.stay4safe.glyph.START"
        const val ACTION_IDLE = "ai.stay4safe.glyph.IDLE"
        const val ACTION_SCANNING = "ai.stay4safe.glyph.SCANNING"
        const val ACTION_SCAM_CALL = "ai.stay4safe.glyph.SCAM_CALL"
        const val ACTION_FALL_DETECTED = "ai.stay4safe.glyph.FALL_DETECTED"
        const val ACTION_EMERGENCY = "ai.stay4safe.glyph.EMERGENCY"
        const val ACTION_THREAT_UPDATE = "ai.stay4safe.glyph.THREAT_UPDATE"
        const val EXTRA_THREAT_LEVEL = "threat_level"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action ?: ACTION_IDLE
        val detail = if (action == ACTION_THREAT_UPDATE) {
            val level = intent?.getStringExtra(EXTRA_THREAT_LEVEL)
                ?.let { runCatching { ThreatLevel.valueOf(it) }.getOrNull() }
                ?: ThreatLevel.NONE
            "$action:$level"
        } else {
            action
        }
        Log.i(TAG, "Glyph fallback pattern requested: $detail")
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
