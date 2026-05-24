package ai.stay4safe.glyph

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class GlyphGuardianApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannel(
            "glyph_guardian",
            "Glyph Guardian",
            NotificationManager.IMPORTANCE_LOW
        ).also {
            it.description = "Stay4S Glyph LED status indicator"
            getSystemService(NotificationManager::class.java).createNotificationChannel(it)
        }
    }
}
