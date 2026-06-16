package mx.utng.smarthealthmonitor.wear.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import mx.utng.smarthealthmonitor.data.SmartHealthRepository
import mx.utng.smarthealthmonitor.data.models.LecturaFC

class WearDashboardViewModel : ViewModel() {

    private val _fc = MutableStateFlow(72)
    val fc: StateFlow<Int> = _fc.asStateFlow()

    val historial: StateFlow<List<LecturaFC>> =
        SmartHealthRepository.obtenerHistorial()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    fun actualizarFC(bpm: Int) {
        _fc.value = bpm
    }
}