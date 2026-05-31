package com.stay4s.grok

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.stay4s.grok.parallel.*
import com.stay4s.grok.partnership.PartnershipVerifier
import com.stay4s.grok.audit.AuditLogger
import kotlinx.coroutines.*

/**
 * GrokAgentCoreService — The Persistent Parallel Brain
 *
 * This service runs the full Parallel Grok AI Brain for the Stay4S Grok Edition.
 * On Genesis devices (001-100) it activates the full covenant mode.
 */
class GrokAgentCoreService : Service() {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private lateinit var orchestrator: ParallelOrchestrator
    private lateinit var partnership: PartnershipVerifier
    private lateinit var audit: AuditLogger
    private lateinit var contextGraph: SharedEvolvingContextGraph

    override fun onCreate() {
        super.onCreate()

        partnership = PartnershipVerifier(this)
        audit = AuditLogger(this)

        if (!partnership.verifyPartnership()) {
            audit.logCritical("PARTNERSHIP_FAILED_AT_BOOT")
            stopSelf()
            return
        }

        contextGraph = SharedEvolvingContextGraph(partnership, audit)
        orchestrator = ParallelOrchestrator(partnership, audit, contextGraph)

        startForeground(4242, buildNotification())
        audit.log("PARALLEL_GROK_BRAIN_STARTED", "Genesis mode: ${partnership.isGenesisDevice()}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val rawCommand = intent?.getStringExtra("command") ?: return START_STICKY

        scope.launch {
            val parsed = GrokCommandParser.parse(rawCommand)
            val result = orchestrator.execute(parsed)
            deliverResult(result)
        }
        return START_STICKY
    }

    private fun deliverResult(result: ParallelExecutionResult) {
        // In real implementation: notification, overlay, voice response, Meshmatic, etc.
        audit.log("RESULT_DELIVERED", result.summary)
    }

    private fun buildNotification() = NotificationCompat.Builder(this, "grok_agent_channel")
        .setContentTitle("Grok Agent Core")
        .setContentText("Parallel reasoning active • Stay4S Grok Edition")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .build()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        orchestrator.shutdown()
        audit.log("PARALLEL_GROK_BRAIN_STOPPED")
    }
}