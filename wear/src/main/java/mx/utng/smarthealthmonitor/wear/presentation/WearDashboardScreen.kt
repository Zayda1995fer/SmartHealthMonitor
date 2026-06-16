package mx.utng.smarthealthmonitor.wear.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import mx.utng.smarthealthmonitor.wear.presentation.components.WearFCCard

@Composable
fun WearDashboardScreen(
    onAlertClick: () -> Unit = {},
    viewModel: WearDashboardViewModel = viewModel(),
    onHistorialClick: () -> Unit
) {
    val fc by viewModel.fc.collectAsState()
    val listState = rememberScalingLazyListState()

    Scaffold(
        timeText = {
            TimeText(modifier = Modifier.scrollAway(listState))
        },
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        }
    ) {
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                WearFCCard(
                    fc = fc,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Chip(
                    label = { Text("⚠ Alerta") },
                    onClick = {
                        android.util.Log.d("WearNav", "onClick del Chip ejecutado — llamando onAlertClick")
                        onAlertClick()
                    },
                    colors = ChipDefaults.primaryChipColors(
                        backgroundColor = MaterialTheme.colors.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}