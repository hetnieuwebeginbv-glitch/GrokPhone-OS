package com.stay4s.grok.parallel

import com.stay4s.grok.partnership.PartnershipVerifier
import com.stay4s.grok.audit.AuditLogger
import kotlinx.coroutines.*

/**
 * PARALLEL GROK AI BRAIN - ORCHESTRATOR
 *
 * This is the heart of the Stay4S Grok Edition.
 * It runs multiple reasoning paths in parallel, evaluates them using Best-of-N,
 * and produces high-quality results while strictly enforcing the Partnership covenant.
 *
 * Designed to run inside the isolated grok_agent SELinux domain.
 */
class ParallelOrchestrator(
    private val partnership: PartnershipVerifier,
    private val audit: AuditLogger,
    private val contextGraph: SharedEvolvingContextGraph
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    /**
     * Main entry point for any user command (from @grok, Accessibility, voice, etc.)
     */
    suspend fun execute(
        command: ParsedCommand,
        initialContext: ContextSnapshot = contextGraph.snapshot()
    ): ParallelExecutionResult {

        if (!partnership.verifyPartnership()) {
            audit.logCritical("PARTNERSHIP_VERIFICATION_FAILED", command)
            return ParallelExecutionResult.failure("Covenant verification failed. Agent locked.")
        }

        audit.log("PARALLEL_ORCHESTRATOR_START", command)

        // Spawn multiple reasoning paths concurrently
        val paths = supervisorScope {
            listOf(
                async { ContextAnalysisPath(contextGraph, audit, partnership).run(command, initialContext) },
                async { ToolUsePath(contextGraph, audit, partnership).run(command, initialContext) },
                async { PlanningPath(contextGraph, audit, partnership).run(command, initialContext) },
                async { GenesisPath(contextGraph, audit, partnership).run(command, initialContext) } // Special for 001-100
            )
        }

        val results = paths.mapNotNull { it.await() }

        if (results.isEmpty()) {
            return ParallelExecutionResult.failure("No valid reasoning paths completed.")
        }

        // Best-of-N evaluation + lightweight debate
        val evaluator = PathEvaluator()
        val finalResult = evaluator.evaluateAndSelect(results, command)

        audit.log("PARALLEL_EXECUTION_COMPLETE", mapOf(
            "bestPath" to finalResult.bestPath.pathName,
            "score" to finalResult.bestPath.score,
            "pathsEvaluated" to results.size
        ))

        // Always log through Partnership for the Genesis covenant
        partnership.logWithVerification("PARALLEL_RESULT", finalResult.summary())

        return finalResult
    }

    fun shutdown() {
        scope.cancel()
    }
}