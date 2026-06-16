package mx.utng.smarthealthmonitor.wear.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import mx.utng.smarthealthmonitor.wear.data.LecturaFC
import mx.utng.smarthealthmonitor.wear.data.WearRepository

class WearDashboardViewModel : ViewModel() {

    val fc: StateFlow<Int> = WearRepository.fcFlow
        .map { if (it == 0) 72 else it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 72)

    val historial: StateFlow<List<LecturaFC>> =
        WearRepository.obtenerHistorial()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    fun actualizarFC(bpm: Int) {
        WearRepository.actualizarFC(bpm)
    }
}