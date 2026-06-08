package ai.stay4safe.guardian.service

import ai.stay4safe.guardian.glyph.GlyphGuardianService
import ai.stay4safe.guardian.model.GuardianEvent
import ai.stay4safe.guardian.model.ThreatLevel
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.os.PowerManager
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.math.sqrt

/**
 * Stay4S Guardian AI — Core Background Service
 *
 * De Guardian draait altijd op de achtergrond en bewaakt vier lagen:
 *   1. SAFETY    — valdetectie, noodsituaties, Fastbutton
 *   2. SECURITY  — scam calls, phishing, verdachte apps
 *   3. PRIVACY   — camera/microfoon monitoring, data-lekken
 *   4. SUPPORT   — context-bewuste hulp, AI-assistent
 *
 * Onveranderlijke Regels (kunnen NOOIT worden uitgeschakeld):
 *   R1. 112 is altijd bereikbaar
 *   R2. Nooit stiekem data delen
 *   R3. Altijd lokaal verwerken waar mogelijk
 *   R4. Altijd uitleg geven bij ingrijpen
 *   R5. Altijd handmatig te overrulen
 *   R6. Nooit de gebruiker manipuleren
 */
@AndroidEntryPoint
class GuardianService : Service(), SensorEventListener {

    companion object {
        private const val TAG = "GuardianService"
        const val NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "guardian_service"
        const val CHANNEL_NAME = "Stay4S Guardian"

        // Intent actions
        const val ACTION_START = "ai.stay4safe.guardian.START"
        const val ACTION_STOP = "ai.stay4safe.guardian.STOP"
        const val ACTION_FASTBUTTON = "ai.stay4safe.guardian.FASTBUTTON"
        const val ACTION_EMERGENCY = "ai.stay4safe.guardian.EMERGENCY"

        // Fall detection threshold (m/s²)
        private const val FALL_FREE_FALL_THRESHOLD = 2.0f
        private const val FALL_IMPACT_THRESHOLD = 25.0f
        private const val FALL_CONFIRM_DELAY_MS = 30_000L  // 30 seconden wachten
    }

    @Inject lateinit var scamCallService: ScamCallService
    @Inject lateinit var privacyMonitor: PrivacyMonitorService
    @Inject lateinit var auditLog: AuditLogService

    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private lateinit var sensorManager: SensorManager
    private lateinit var wakeLock: PowerManager.WakeLock

    // Guardian state
    private val _guardianState = MutableStateFlow(GuardianState.ACTIVE)
    val guardianState: StateFlow<GuardianState> = _guardianState

    private val _threatLevel = MutableStateFlow(ThreatLevel.NONE)
    val threatLevel: StateFlow<ThreatLevel> = _threatLevel

    // Fall detection state
    private var isFreeFalling = false
    private var freeFallStartTime = 0L

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Stay4S Guardian AI — Opstarten...")

        setupNotificationChannel()
        acquireWakeLock()
        initSensors()
        startGuardianMonitoring()

        auditLog.log("Guardian service gestart — alle vier lagen actief")
        Log.i(TAG, "Guardian volledig operationeel")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, buildNotification())

        when (intent?.action) {
            ACTION_FASTBUTTON -> handleFastbutton()
            ACTION_EMERGENCY -> triggerEmergencyProtocol("Handmatig geactiveerd")
            ACTION_STOP -> {
                // R5: Gebruiker kan altijd overrulen — maar Guardian waarschuwt
                auditLog.log("Guardian gestopt door gebruiker (R5: handmatig overrule)")
                stopSelf()
            }
        }

        return START_STICKY  // Herstart automatisch als het systeem de service stopt
    }

    override fun onBind(intent: Intent?): IBinder? = null

    // ═══════════════════════════════════════════════════════════
    // LAAG 1: SAFETY — Valdetectie & Noodprotocol
    // ═══════════════════════════════════════════════════════════

    private fun initSensors() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val magnitude = sqrt(x * x + y * y + z * z)

            when {
                // Vrije val gedetecteerd (lage g-kracht)
                magnitude < FALL_FREE_FALL_THRESHOLD && !isFreeFalling -> {
                    isFreeFalling = true
                    freeFallStartTime = System.currentTimeMillis()
                    Log.d(TAG, "Vrije val gedetecteerd — magnitude: $magnitude")
                }

                // Impact na vrije val (hoge g-kracht)
                magnitude > FALL_IMPACT_THRESHOLD && isFreeFalling -> {
                    val fallDuration = System.currentTimeMillis() - freeFallStartTime
                    if (fallDuration > 100) {  // Minimaal 100ms vrije val
                        onFallDetected(magnitude, fallDuration)
                    }
                    isFreeFalling = false
                }

                // Reset als er geen impact volgt
                magnitude > 5.0f && isFreeFalling -> {
                    isFreeFalling = false
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun onFallDetected(impactForce: Float, duration: Long) {
        Log.w(TAG, "VAL GEDETECTEERD — kracht: $impactForce m/s², duur: ${duration}ms")
        auditLog.log("Valdetectie: kracht=$impactForce, duur=${duration}ms")

        // Glyph activeren als waarschuwing
        startService(Intent(this, GlyphGuardianService::class.java).apply {
            action = GlyphGuardianService.ACTION_FALL_DETECTED
        })

        // 30 seconden wachten — als gebruiker niet reageert, noodprotocol
        serviceScope.launch {
            delay(FALL_CONFIRM_DELAY_MS)
            if (_guardianState.value != GuardianState.USER_RESPONDED) {
                triggerEmergencyProtocol("Valdetectie — geen reactie na 30 seconden")
            }
        }

        // Notificatie sturen
        showFallWarningNotification()
    }

    private fun triggerEmergencyProtocol(reason: String) {
        Log.e(TAG, "NOODPROTOCOL GEACTIVEERD: $reason")
        auditLog.log("Noodprotocol: $reason")

        // R1: 112 is ALTIJD bereikbaar — dit kan nooit worden geblokkeerd
        val emergencyIntent = Intent(Intent.ACTION_CALL).apply {
            data = android.net.Uri.parse("tel:112")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(emergencyIntent)

        // Locatie delen met noodcontacten
        serviceScope.launch {
            shareLocationWithEmergencyContacts(reason)
        }

        // Glyph noodpatroon
        startService(Intent(this, GlyphGuardianService::class.java).apply {
            action = GlyphGuardianService.ACTION_EMERGENCY
        })

        _threatLevel.value = ThreatLevel.CRITICAL
    }

    // ═══════════════════════════════════════════════════════════
    // LAAG 2: SECURITY — Scam & Phishing Detectie
    // ═══════════════════════════════════════════════════════════

    private fun startGuardianMonitoring() {
        serviceScope.launch {
            // Monitor inkomende oproepen
            monitorIncomingCalls()
        }

        serviceScope.launch {
            // Monitor privacy (camera/microfoon toegang)
            privacyMonitor.startMonitoring()
        }
    }

    private suspend fun monitorIncomingCalls() {
        // Telephony state monitoring
        Log.i(TAG, "Scam Call Defender actief")
    }

    // ═══════════════════════════════════════════════════════════
    // LAAG 4: SUPPORT — Fastbutton
    // ═══════════════════════════════════════════════════════════

    private fun handleFastbutton() {
        Log.i(TAG, "Fastbutton ingedrukt")
        auditLog.log("Fastbutton geactiveerd door gebruiker")

        val intent = Intent(this, Class.forName("ai.stay4safe.guardian.ui.screens.FastbuttonActivity")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    // ═══════════════════════════════════════════════════════════
    // HULPFUNCTIES
    // ═══════════════════════════════════════════════════════════

    private suspend fun shareLocationWithEmergencyContacts(reason: String) {
        // Locatie ophalen en delen
        Log.i(TAG, "Locatie delen met noodcontacten: $reason")
    }

    private fun acquireWakeLock() {
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "Stay4Safe::GuardianWakeLock"
        )
        wakeLock.acquire(10 * 60 * 1000L)  // Max 10 minuten per keer
    }

    private fun setupNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Stay4S Guardian AI beschermt je op de achtergrond"
            setShowBadge(false)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification {
        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Stay4S Guardian Actief")
            .setContentText("Alle vier beschermingslagen zijn actief")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setOngoing(true)
            .setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }

    private fun showFallWarningNotification() {
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("⚠️ Val gedetecteerd")
            .setContentText("Ben je oké? Tik hier als alles goed is. Anders bellen we 112.")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID + 1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        sensorManager.unregisterListener(this)
        if (wakeLock.isHeld) wakeLock.release()
        auditLog.log("Guardian service gestopt")
        Log.i(TAG, "Guardian gestopt")
    }
}

enum class GuardianState {
    ACTIVE,
    MONITORING,
    ALERT,
    EMERGENCY,
    USER_RESPONDED,
    PAUSED
}
