package ai.stay4safe.guardian.service

import android.app.AppOpsManager
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Stay4S Privacy Monitor
 *
 * Bewaakt welke apps toegang hebben tot camera, microfoon,
 * locatie en andere gevoelige sensoren.
 *
 * Volledig lokaal — geen data naar externe servers (Regel 2).
 * Altijd uitleg bij een melding (Regel 4).
 */
@Singleton
class PrivacyMonitorService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auditLog: AuditLogService
) {
    companion object {
        private const val TAG = "PrivacyMonitor"
        private const val SCAN_INTERVAL_MS = 5000L  // Elke 5 seconden scannen
    }

    private var isMonitoring = false

    suspend fun startMonitoring() = withContext(Dispatchers.Default) {
        isMonitoring = true
        Log.i(TAG, "Privacy Monitor gestart")
        auditLog.log("Privacy Monitor actief — camera/microfoon bewaking ingeschakeld")

        while (isMonitoring) {
            checkSensorAccess()
            delay(SCAN_INTERVAL_MS)
        }
    }

    fun stopMonitoring() {
        isMonitoring = false
        Log.i(TAG, "Privacy Monitor gestopt")
    }

    private fun checkSensorAccess() {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

        // Controleer welke apps momenteel camera gebruiken
        checkOpForAllApps(appOps, AppOpsManager.OPSTR_CAMERA, "Camera")

        // Controleer welke apps momenteel microfoon gebruiken
        checkOpForAllApps(appOps, AppOpsManager.OPSTR_RECORD_AUDIO, "Microfoon")

        // Controleer locatietoegang
        checkOpForAllApps(appOps, AppOpsManager.OPSTR_FINE_LOCATION, "Locatie")
    }

    private fun checkOpForAllApps(appOps: AppOpsManager, op: String, sensorName: String) {
        try {
            val packages = context.packageManager.getInstalledApplications(0)
            packages.forEach { appInfo ->
                val mode = appOps.checkOpNoThrow(op, appInfo.uid, appInfo.packageName)
                if (mode == AppOpsManager.MODE_ALLOWED) {
                    // App heeft toegang — is dit verwacht?
                    val appName = context.packageManager.getApplicationLabel(appInfo).toString()

                    // Bekende veilige apps die sensor mogen gebruiken
                    val trustedApps = setOf(
                        "com.nothing.camera",
                        "com.google.android.dialer",
                        "com.android.dialer",
                        "ai.stay4safe.guardian"
                    )

                    if (!trustedApps.contains(appInfo.packageName)) {
                        Log.d(TAG, "$sensorName toegang: $appName (${appInfo.packageName})")
                        // In productie: vergelijk met verwacht gedrag en waarschuw bij afwijking
                    }
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Privacy check fout voor $sensorName: ${e.message}")
        }
    }
}
