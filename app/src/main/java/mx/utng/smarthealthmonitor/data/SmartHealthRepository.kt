package mx.utng.smarthealthmonitor.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import mx.utng.smarthealthmonitor.data.models.LecturaFC
import mx.utng.smarthealthmonitor.data.models.MockData

object SmartHealthRepository {

    private val _fcFlow = MutableStateFlow(0)
    val fcFlow: StateFlow<Int> = _fcFlow.asStateFlow()

    private val _pasosFlow = MutableStateFlow(0)
    val pasosFlow: StateFlow<Int> = _pasosFlow.asStateFlow()

    private val _historial = mutableListOf<LecturaFC>()

    fun actualizarFC(bpm: Int) {
        _fcFlow.value = bpm
        _historial.add(0, LecturaFC(
            id = _historial.size + 1,
            valorBpm = bpm,
            hora = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                .format(java.util.Date())
        ))
    }

    fun actualizarPasos(pasos: Int) {
        _pasosFlow.value = pasos
    }

    fun obtenerHistorial(): Flow<List<LecturaFC>> = flow {
        emit(_historial.toList())
    }
}