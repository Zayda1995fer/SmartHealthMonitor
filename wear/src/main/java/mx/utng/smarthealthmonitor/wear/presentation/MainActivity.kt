package mx.utng.smarthealthmonitor.wear.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import kotlinx.coroutines.launch
import mx.utng.smarthealthmonitor.wear.services.HealthDataService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Solicitar permisos en tiempo de ejecución
        val permisos = arrayOf(
            android.Manifest.permission.BODY_SENSORS,
            android.Manifest.permission.ACTIVITY_RECOGNITION,
            "android.permission.health.READ_HEART_RATE"
        )

        requestPermissions(permisos, 100)

        setContent {
            WearScreen()
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
            // Permisos concedidos — registrar Health Services
            lifecycleScope.launch {
                try {
                    HealthDataService.registrar(applicationContext)
                } catch (e: Exception) {
                    android.util.Log.e("MainActivity", "Error registrando HealthDataService: ${e.message}")
                }
            }
        } else {
            android.util.Log.w("MainActivity", "Permisos denegados — Health Services no disponible")
        }
    }
}

@Composable
fun WearScreen() {
    var bpm by remember { mutableStateOf("--") }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "FC: $bpm bpm",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Monitoreando...",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}