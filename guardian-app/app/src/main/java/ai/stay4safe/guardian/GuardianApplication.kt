package ai.stay4safe.guardian

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

/**
 * Stay4S Guardian — Application klasse
 * Hilt DI initialisatie + notificatiekanalen aanmaken
 */
@HiltAndroidApp
class GuardianApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val manager = getSystemService(NotificationManager::class.java)

        // Kanaal 1: Guardian status (altijd zichtbaar)
        NotificationChannel(
            CHANNEL_GUARDIAN,
            getString(R.string.channel_guardian_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.channel_guardian_desc)
            setShowBadge(false)
            manager.createNotificationChannel(this)
        }

        // Kanaal 2: Scam Defender (hoge prioriteit)
        NotificationChannel(
            CHANNEL_SCAM,
            getString(R.string.channel_scam_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = getString(R.string.channel_scam_desc)
            enableVibration(true)
            manager.createNotificationChannel(this)
        }

        // Kanaal 3: Noodprotocol (maximale prioriteit)
        NotificationChannel(
            CHANNEL_EMERGENCY,
            getString(R.string.channel_emergency_name),
            NotificationManager.IMPORTANCE_MAX
        ).apply {
            description = getString(R.string.channel_emergency_desc)
            enableVibration(true)
            enableLights(true)
            lightColor = 0xFFFF3B3B.toInt()
            manager.createNotificationChannel(this)
        }

        // Kanaal 4: Privacy Monitor
        NotificationChannel(
            CHANNEL_PRIVACY,
            getString(R.string.channel_privacy_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.channel_privacy_desc)
            manager.createNotificationChannel(this)
        }
    }

    companion object {
        const val CHANNEL_GUARDIAN = "guardian_status"
        const val CHANNEL_SCAM = "scam_defender"
        const val CHANNEL_EMERGENCY = "emergency_protocol"
        const val CHANNEL_PRIVACY = "privacy_monitor"
    }
}
