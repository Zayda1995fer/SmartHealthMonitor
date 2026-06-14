package mx.utng.smarthealthmonitor.wear.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.utng.smarthealthmonitor.wear.presentation.theme.SmartHealthWearTheme
import mx.utng.smarthealthmonitor.wear.services.HealthDataService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permisos = arrayOf(
            android.Manifest.permission.BODY_SENSORS,
            android.Manifest.permission.ACTIVITY_RECOGNITION,
            "android.permission.health.READ_HEART_RATE"
        )

        requestPermissions(permisos, 100)

        setContent {
            SmartHealthWearTheme {
                WearDashboardScreen()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        ) {
            lifecycleScope.launch {
                try {
                    HealthDataService.registrar(applicationContext)
                } catch (e: Exception) {
                    android.util.Log.e(
                        "MainActivity",
                        "Error registrando HealthDataService: ${e.message}"
                    )
                }
            }
        } else {
            android.util.Log.w(
                "MainActivity",
                "Permisos denegados — Health Services no disponible"
            )
        }
    }
}