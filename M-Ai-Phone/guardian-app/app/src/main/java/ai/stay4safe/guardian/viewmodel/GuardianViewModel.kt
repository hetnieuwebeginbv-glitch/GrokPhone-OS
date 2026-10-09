package ai.stay4safe.guardian.viewmodel

import ai.stay4safe.guardian.model.AuditEntry
import ai.stay4safe.guardian.model.GuardianSettings
import ai.stay4safe.guardian.model.ThreatLevel
import ai.stay4safe.guardian.service.AuditLogService
import ai.stay4safe.guardian.service.GuardianService
import ai.stay4safe.guardian.service.GuardianState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * GuardianViewModel — MVVM ViewModel voor de Guardian UI
 *
 * Verbindt de GuardianService met de Compose UI via StateFlow.
 * Alle UI-state is hier gecentraliseerd.
 */
@HiltViewModel
class GuardianViewModel @Inject constructor(
    private val auditLogService: AuditLogService
) : ViewModel() {

    // Guardian status
    private val _guardianState = MutableStateFlow(GuardianState.ACTIVE)
    val guardianState: StateFlow<GuardianState> = _guardianState.asStateFlow()

    // Dreigingsniveau
    private val _threatLevel = MutableStateFlow(ThreatLevel.NONE)
    val threatLevel: StateFlow<ThreatLevel> = _threatLevel.asStateFlow()

    // Instellingen
    private val _settings = MutableStateFlow(GuardianSettings())
    val settings: StateFlow<GuardianSettings> = _settings.asStateFlow()

    // Audit log
    val auditEntries: StateFlow<List<AuditEntry>> = auditLogService.entries

    // Statistieken
    private val _scamCallsBlocked = MutableStateFlow(0)
    val scamCallsBlocked: StateFlow<Int> = _scamCallsBlocked.asStateFlow()

    private val _daysProtected = MutableStateFlow(0)
    val daysProtected: StateFlow<Int> = _daysProtected.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            // In productie: laden uit Room database
            _scamCallsBlocked.value = 0
            _daysProtected.value = 0
        }
    }

    fun onFastbuttonPressed() {
        viewModelScope.launch {
            auditLogService.log("Fastbutton ingedrukt door gebruiker")
            // In productie: stuur signaal naar GuardianService
        }
    }

    fun onEmergencyTriggered() {
        viewModelScope.launch {
            _guardianState.value = GuardianState.EMERGENCY
            auditLogService.log("Noodprotocol handmatig geactiveerd door gebruiker")
        }
    }

    fun onUserResponded() {
        viewModelScope.launch {
            _guardianState.value = GuardianState.ACTIVE
            _threatLevel.value = ThreatLevel.NONE
            auditLogService.log("Gebruiker heeft gereageerd — noodprotocol geannuleerd")
        }
    }

    fun updateSettings(newSettings: GuardianSettings) {
        viewModelScope.launch {
            _settings.value = newSettings
            auditLogService.log("Instellingen bijgewerkt door gebruiker")
        }
    }
}
