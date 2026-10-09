package ai.stay4safe.glyph

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class GlyphGuardianApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannel(
            "glyph_guardian",
            getString(R.string.channel_glyph_name),
            NotificationManager.IMPORTANCE_LOW
        ).also {
            it.description = getString(R.string.channel_glyph_desc)
            getSystemService(NotificationManager::class.java).createNotificationChannel(it)
        }
    }
}
