package ai.stay4safe.guardian.service

import ai.stay4safe.guardian.model.AuditCategory
import ai.stay4safe.guardian.model.AuditEntry
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Stay4S Audit Log Service
 *
 * Regel 4: De Guardian legt ALTIJD uit wat hij doet en waarom.
 * Elke actie wordt gelogd en is zichtbaar voor de gebruiker.
 *
 * Volledig lokaal — logs verlaten nooit het apparaat (Regel 2).
 */
@Singleton
class AuditLogService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "S4S_AuditLog"
        private const val MAX_ENTRIES = 1000
    }

    private val _entries = MutableStateFlow<List<AuditEntry>>(emptyList())
    val entries: StateFlow<List<AuditEntry>> = _entries

    /**
     * Log een Guardian actie
     * R4: Altijd transparant — elke actie wordt vastgelegd
     */
    fun log(
        message: String,
        category: AuditCategory = AuditCategory.SYSTEM,
        isUserVisible: Boolean = true
    ) {
        val entry = AuditEntry(
            id = System.currentTimeMillis(),
            timestamp = Instant.now(),
            message = message,
            category = category,
            isUserVisible = isUserVisible
        )

        Log.i(TAG, "[$category] $message")

        val current = _entries.value.toMutableList()
        current.add(0, entry)  // Nieuwste bovenaan

        // Maximum entries bewaren
        if (current.size > MAX_ENTRIES) {
            current.removeAt(current.size - 1)
        }

        _entries.value = current
    }

    fun getRecentEntries(count: Int = 50): List<AuditEntry> {
        return _entries.value.take(count)
    }

    fun clearLog() {
        log("Audit log gewist door gebruiker", AuditCategory.USER_ACTION)
        _entries.value = emptyList()
    }
}
