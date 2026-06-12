package mx.utng.smarthealthmonitor.wear.communication

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await
import mx.utng.smarthealthmonitor.wear.data.HeartRateData

class WearDataSender(
    private val context: Context
) {
    private val messageClient = Wearable.getMessageClient(context)

    companion object {
        const val PATH_FC = "/smarthealthmonitor/fc"
        const val PATH_PASOS = "/smarthealthmonitor/pasos"
        private const val TAG = "WearDataSender"
    }

    fun sendHeartRate(data: HeartRateData) {
        Log.d(TAG, "Enviando FC: ${data.bpm}")
    }

    suspend fun enviarFC(bpm: Int) {
        try {
            // Obtener nodos conectados (el teléfono)
            val nodes = Wearable.getNodeClient(context)
                .connectedNodes.await()

            if (nodes.isEmpty()) {
                Log.w(TAG, "No hay nodos conectados")
                return
            }

            // Enviar a cada nodo (teléfono)
            nodes.forEach { node ->
                messageClient.sendMessage(
                    node.id,
                    PATH_FC,
                    bpm.toString().toByteArray()
                ).await()
                Log.d(TAG, "FC enviada al teléfono: $bpm bpm → nodo: ${node.displayName}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error enviando FC: ${e.message}")
        }
    }

    suspend fun enviarPasos(pasos: Int) {
        try {
            val nodes = Wearable.getNodeClient(context)
                .connectedNodes.await()

            nodes.forEach { node ->
                messageClient.sendMessage(
                    node.id,
                    PATH_PASOS,
                    pasos.toString().toByteArray()
                ).await()
                Log.d(TAG, "Pasos enviados: $pasos → nodo: ${node.displayName}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error enviando pasos: ${e.message}")
        }
    }
}