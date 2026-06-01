package mx.utng.smarthealthmonitor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import mx.utng.smarthealthmonitor.data.SmartHealthRepository
import mx.utng.smarthealthmonitor.data.models.MockData

class DashboardViewModel : ViewModel() {

    val fc: StateFlow<Int> =
        SmartHealthRepository.fcFlow
            .map {

                if (it == 0)
                    MockData.fcActual
                else
                    it
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                MockData.fcActual
            )

    val pasos: StateFlow<Int> =
        SmartHealthRepository.pasosFlow
            .map {

                if (it == 0)
                    MockData.pasosActual
                else
                    it
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                MockData.pasosActual
            )

    val historial =
        MockData.historialFC
}