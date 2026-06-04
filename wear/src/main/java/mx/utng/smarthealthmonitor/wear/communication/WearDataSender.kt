package mx.utng.smarthealthmonitor.wear.communication

import android.content.Context
import android.util.Log
import mx.utng.smarthealthmonitor.wear.data.HeartRateData

class WearDataSender(
    private val context: Context
) {
    // Función original — se mantiene
    fun sendHeartRate(data: HeartRateData) {
        Log.d("WearDataSender", "Enviando FC: ${data.bpm}")
        // Aquí posteriormente se enviará al teléfono
    }

    // Nueva función llamada por HealthDataService
    suspend fun enviarFC(bpm: Int) {
        Log.d("WearDataSender", "FC real del sensor: $bpm bpm")
        // Aquí se enviará al teléfono vía MessageClient (sesiones futuras)
    }
}