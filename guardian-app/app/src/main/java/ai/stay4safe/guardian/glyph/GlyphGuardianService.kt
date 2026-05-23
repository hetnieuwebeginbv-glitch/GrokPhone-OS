package ai.stay4safe.guardian.glyph

import ai.stay4safe.guardian.model.ThreatLevel
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.nothing.ketchum.Common
import com.nothing.ketchum.Glyph
import com.nothing.ketchum.GlyphFrame
import com.nothing.ketchum.GlyphManager
import kotlinx.coroutines.*

/**
 * Glyph Guardian Service
 *
 * Herprogrammeert de Glyph Interface van de Nothing Phone 3a
 * als visuele status-indicator voor de Stay4S Guardian AI.
 *
 * Nothing Phone 3a Glyph Layout:
 *   A1-A11: Bovenste strip (11 LEDs) — Guardian status
 *   B1-B5:  Rechter strip (5 LEDs)  — Threat level
 *   C1-C20: Grote ring (20 LEDs)    — Animaties & alerts
 *
 * Guardian Patronen:
 *   IDLE:      C-ring langzaam ademend (teal puls)
 *   SCANNING:  A-strip van links naar rechts
 *   ALERT:     B-strip rood knipperend
 *   EMERGENCY: Alle LEDs snel knipperend
 *   FALL:      C-ring SOS patroon
 *   SCAM_CALL: A+B strips waarschuwingspatroon
 */
class GlyphGuardianService : Service() {

    companion object {
        private const val TAG = "GlyphGuardian"

        const val ACTION_IDLE = "ai.stay4safe.glyph.IDLE"
        const val ACTION_SCANNING = "ai.stay4safe.glyph.SCANNING"
        const val ACTION_SCAM_CALL = "ai.stay4safe.glyph.SCAM_CALL"
        const val ACTION_FALL_DETECTED = "ai.stay4safe.glyph.FALL_DETECTED"
        const val ACTION_EMERGENCY = "ai.stay4safe.glyph.EMERGENCY"
        const val ACTION_THREAT_UPDATE = "ai.stay4safe.glyph.THREAT_UPDATE"
        const val EXTRA_THREAT_LEVEL = "threat_level"

        // Nothing Phone 3a Glyph indices
        // A strip: indices 20-30 (11 LEDs)
        // B strip: indices 31-35 (5 LEDs)
        // C ring:  indices 0-19  (20 LEDs)
        val A_STRIP = (20..30).toList()
        val B_STRIP = (31..35).toList()
        val C_RING  = (0..19).toList()
    }

    private var glyphManager: GlyphManager? = null
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var currentPattern: Job? = null
    private var isGlyphConnected = false

    private val glyphCallback = object : GlyphManager.Callback {
        override fun onServiceConnected(componentName: android.content.ComponentName) {
            Log.i(TAG, "Glyph SDK verbonden")
            isGlyphConnected = true
            try {
                glyphManager?.register(Common.DEVICE_24111)  // Nothing Phone 3a
                glyphManager?.openSession()
                startIdlePattern()
            } catch (e: Exception) {
                Log.e(TAG, "Glyph registratie mislukt: ${e.message}")
            }
        }

        override fun onServiceDisconnected(componentName: android.content.ComponentName) {
            Log.w(TAG, "Glyph SDK verbroken")
            isGlyphConnected = false
        }
    }

    override fun onCreate() {
        super.onCreate()
        glyphManager = GlyphManager.getInstance(applicationContext)
        glyphManager?.init(glyphCallback)
        Log.i(TAG, "Glyph Guardian geïnitialiseerd")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_IDLE -> startIdlePattern()
            ACTION_SCANNING -> startScanningPattern()
            ACTION_SCAM_CALL -> startScamCallPattern()
            ACTION_FALL_DETECTED -> startFallPattern()
            ACTION_EMERGENCY -> startEmergencyPattern()
            ACTION_THREAT_UPDATE -> {
                val level = intent.getStringExtra(EXTRA_THREAT_LEVEL)
                updateThreatIndicator(ThreatLevel.valueOf(level ?: "NONE"))
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    // ═══════════════════════════════════════════════════════════
    // GUARDIAN GLYPH PATRONEN
    // ═══════════════════════════════════════════════════════════

    /**
     * IDLE: Zachte puls op de C-ring — Guardian is actief en rustig
     */
    private fun startIdlePattern() {
        if (!isGlyphConnected) return
        cancelCurrentPattern()

        currentPattern = serviceScope.launch {
            Log.d(TAG, "Idle patroon gestart")
            val frame = glyphManager?.glyphFrameBuilder
                ?.buildPeriod(3000)
                ?.buildCycles(0)  // Oneindig
                ?.buildInterval(1000)
                ?.buildChannelC()  // Volledige C-ring
                ?.build() ?: return@launch

            glyphManager?.animate(frame)
        }
    }

    /**
     * SCANNING: A-strip loopt van boven naar beneden — Guardian scant
     */
    private fun startScanningPattern() {
        if (!isGlyphConnected) return
        cancelCurrentPattern()

        currentPattern = serviceScope.launch {
            Log.d(TAG, "Scan patroon gestart")
            repeat(5) { cycle ->
                A_STRIP.forEach { index ->
                    val frame = glyphManager?.glyphFrameBuilder
                        ?.buildPeriod(100)
                        ?.buildCycles(1)
                        ?.buildChannel(index)
                        ?.build() ?: return@launch

                    glyphManager?.toggle(frame)
                    delay(80)
                }
            }
            startIdlePattern()
        }
    }

    /**
     * SCAM CALL: A+B strips knipperen — waarschuwing voor verdachte oproep
     */
    private fun startScamCallPattern() {
        if (!isGlyphConnected) return
        cancelCurrentPattern()

        currentPattern = serviceScope.launch {
            Log.w(TAG, "Scam call patroon gestart")
            repeat(10) {
                // A-strip aan
                val frameA = glyphManager?.glyphFrameBuilder
                    ?.buildPeriod(300)
                    ?.buildCycles(1)
                    ?.buildChannelA()
                    ?.build() ?: return@launch
                glyphManager?.toggle(frameA)
                delay(300)

                // B-strip aan
                val frameB = glyphManager?.glyphFrameBuilder
                    ?.buildPeriod(300)
                    ?.buildCycles(1)
                    ?.buildChannelB()
                    ?.build() ?: return@launch
                glyphManager?.toggle(frameB)
                delay(300)
            }
        }
    }

    /**
     * FALL DETECTED: SOS patroon op C-ring — valdetectie
     * Morse code: ... --- ... (SOS)
     */
    private fun startFallPattern() {
        if (!isGlyphConnected) return
        cancelCurrentPattern()

        currentPattern = serviceScope.launch {
            Log.e(TAG, "Val patroon gestart — SOS")

            // SOS: 3 kort, 3 lang, 3 kort
            val sosPattern = listOf(
                200L, 200L, 200L,   // S: 3 korte flitsen
                600L, 600L, 600L,   // O: 3 lange flitsen
                200L, 200L, 200L    // S: 3 korte flitsen
            )

            repeat(5) {
                sosPattern.forEach { duration ->
                    val frame = glyphManager?.glyphFrameBuilder
                        ?.buildPeriod(duration.toInt())
                        ?.buildCycles(1)
                        ?.buildChannelC()
                        ?.build() ?: return@launch
                    glyphManager?.toggle(frame)
                    delay(duration + 100)
                }
                delay(500)
            }
        }
    }

    /**
     * EMERGENCY: Alle LEDs snel knipperend — noodprotocol actief
     */
    private fun startEmergencyPattern() {
        if (!isGlyphConnected) return
        cancelCurrentPattern()

        currentPattern = serviceScope.launch {
            Log.e(TAG, "NOOD patroon gestart")

            // Alle LEDs tegelijk, snel knipperend
            val frame = glyphManager?.glyphFrameBuilder
                ?.buildPeriod(200)
                ?.buildCycles(0)  // Oneindig tot gestopt
                ?.buildInterval(100)
                ?.buildChannelA()
                ?.buildChannelB()
                ?.buildChannelC()
                ?.build() ?: return@launch

            glyphManager?.animate(frame)
        }
    }

    /**
     * Update B-strip als threat level indicator
     * NONE:     0 LEDs
     * LOW:      1 LED
     * MEDIUM:   3 LEDs
     * HIGH:     5 LEDs
     * CRITICAL: 5 LEDs knipperend
     */
    private fun updateThreatIndicator(level: ThreatLevel) {
        if (!isGlyphConnected) return

        val ledCount = when (level) {
            ThreatLevel.NONE -> 0
            ThreatLevel.LOW -> 1
            ThreatLevel.MEDIUM -> 3
            ThreatLevel.HIGH -> 5
            ThreatLevel.CRITICAL -> 5
        }

        serviceScope.launch {
            val builder = glyphManager?.glyphFrameBuilder
                ?.buildPeriod(if (level == ThreatLevel.CRITICAL) 300 else 0)
                ?.buildCycles(if (level == ThreatLevel.CRITICAL) 0 else 1)

            B_STRIP.take(ledCount).forEach { index ->
                builder?.buildChannel(index)
            }

            val frame = builder?.build() ?: return@launch
            if (level == ThreatLevel.CRITICAL) {
                glyphManager?.animate(frame)
            } else {
                glyphManager?.toggle(frame)
            }
        }
    }

    private fun cancelCurrentPattern() {
        currentPattern?.cancel()
        currentPattern = null
        try {
            glyphManager?.closeSession()
            glyphManager?.openSession()
        } catch (e: Exception) {
            Log.w(TAG, "Session reset: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        try {
            glyphManager?.closeSession()
            glyphManager?.unInit()
        } catch (e: Exception) {
            Log.w(TAG, "Glyph cleanup: ${e.message}")
        }
        Log.i(TAG, "Glyph Guardian gestopt")
    }
}
