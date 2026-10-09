package ai.stay4safe.guardian.model

import java.time.Instant

// ═══════════════════════════════════════════════════════════
// THREAT & RISK MODELS
// ═══════════════════════════════════════════════════════════

enum class ThreatLevel {
    NONE, LOW, MEDIUM, HIGH, CRITICAL
}

enum class ScamRisk {
    NONE, LOW, MEDIUM, HIGH
}

data class CallAnalysis(
    val phoneNumber: String,
    val scamRisk: ScamRisk,
    val riskScore: Int,
    val reasons: List<String>,
    val recommendation: String,
    val timestamp: Instant = Instant.now()
)

// ═══════════════════════════════════════════════════════════
// GUARDIAN EVENT MODELS
// ═══════════════════════════════════════════════════════════

sealed class GuardianEvent {
    data class FallDetected(val impactForce: Float, val duration: Long) : GuardianEvent()
    data class ScamCallDetected(val analysis: CallAnalysis) : GuardianEvent()
    data class PrivacyViolation(val appName: String, val sensor: String) : GuardianEvent()
    data class EmergencyTriggered(val reason: String) : GuardianEvent()
    data class FastbuttonPressed(val timestamp: Instant = Instant.now()) : GuardianEvent()
    object UserResponded : GuardianEvent()
}

// ═══════════════════════════════════════════════════════════
// AUDIT LOG MODEL
// ═══════════════════════════════════════════════════════════

data class AuditEntry(
    val id: Long = 0,
    val timestamp: Instant = Instant.now(),
    val message: String,
    val category: AuditCategory = AuditCategory.SYSTEM,
    val isUserVisible: Boolean = true
)

enum class AuditCategory {
    SYSTEM, SAFETY, SECURITY, PRIVACY, SUPPORT, USER_ACTION
}

// ═══════════════════════════════════════════════════════════
// EMERGENCY CONTACT MODEL
// ═══════════════════════════════════════════════════════════

data class EmergencyContact(
    val id: Long = 0,
    val name: String,
    val phoneNumber: String,
    val relationship: String,
    val shareLocation: Boolean = true,
    val notifyOnFall: Boolean = true,
    val notifyOnEmergency: Boolean = true
)

// ═══════════════════════════════════════════════════════════
// GUARDIAN SETTINGS MODEL
// ═══════════════════════════════════════════════════════════

data class GuardianSettings(
    // Safety
    val fallDetectionEnabled: Boolean = true,
    val fallConfirmDelaySeconds: Int = 30,
    val autoCall112: Boolean = true,

    // Security
    val scamCallDefenderEnabled: Boolean = true,
    val scamSmsDefenderEnabled: Boolean = true,
    val phishingDetectionEnabled: Boolean = true,

    // Privacy
    val privacyMonitorEnabled: Boolean = true,
    val cameraAccessAlerts: Boolean = true,
    val microphoneAccessAlerts: Boolean = true,

    // Glyph
    val glyphGuardianEnabled: Boolean = true,
    val glyphIdlePattern: Boolean = true,

    // Network
    val allowCloudAi: Boolean = false,  // Default: lokaal verwerken (R3)
    val meshNetworkEnabled: Boolean = true
)
