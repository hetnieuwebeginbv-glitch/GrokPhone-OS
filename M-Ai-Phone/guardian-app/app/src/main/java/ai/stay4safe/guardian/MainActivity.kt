package ai.stay4safe.guardian

import ai.stay4safe.guardian.ui.screens.MainScreen
import ai.stay4safe.guardian.viewmodel.GuardianViewModel
import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint

/**
 * Stay4S Guardian — Hoofd Activity
 *
 * Start de Guardian service en toont de Compose UI.
 * Vraagt alle benodigde machtigingen aan bij eerste start.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: GuardianViewModel by viewModels()

    // Machtigingen aanvragen
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            startGuardianService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Machtigingen aanvragen
        requestGuardianPermissions()

        setContent {
            val guardianState by viewModel.guardianState.collectAsState()
            val threatLevel by viewModel.threatLevel.collectAsState()
            val settings by viewModel.settings.collectAsState()

            MainScreen(
                guardianState = guardianState,
                threatLevel = threatLevel,
                settings = settings,
                onFastbutton = { viewModel.onFastbuttonPressed() },
                onEmergency = { viewModel.onEmergencyTriggered() }
            )
        }
    }

    private fun requestGuardianPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.VIBRATE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        permissionLauncher.launch(permissions.toTypedArray())
    }

    private fun startGuardianService() {
        val intent = Intent(this, ai.stay4safe.guardian.service.GuardianService::class.java).apply {
            action = ai.stay4safe.guardian.service.GuardianService.ACTION_START
        }
        startForegroundService(intent)
    }
}
