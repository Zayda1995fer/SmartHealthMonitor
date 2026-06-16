package mx.utng.smarthealthmonitor.wear.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object WearRepository {

    private val _fcFlow = MutableStateFlow(0)
    val fcFlow = _fcFlow.asStateFlow()

    private val _historial = MutableStateFlow<List<LecturaFC>>(emptyList())

    fun actualizarFC(bpm: Int) {
        _fcFlow.value = bpm
        val lista = _historial.value.toMutableList()
        lista.add(0, LecturaFC(
            id = lista.size + 1,
            valorBpm = bpm,
            hora = java.text.SimpleDateFormat(
                "HH:mm", java.util.Locale.getDefault()
            ).format(java.util.Date())
        ))
        _historial.value = lista
    }

    fun obtenerHistorial(): Flow<List<LecturaFC>> = _historial
}