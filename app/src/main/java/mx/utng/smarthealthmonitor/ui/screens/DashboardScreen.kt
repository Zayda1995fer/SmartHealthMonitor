package mx.utng.smarthealthmonitor.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.utng.smarthealthmonitor.data.SmartHealthRepository
import mx.utng.smarthealthmonitor.ui.components.FilaHistorial
import mx.utng.smarthealthmonitor.ui.components.TarjetaDato
import mx.utng.smarthealthmonitor.ui.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    onHistorialClick: () -> Unit = {},
    onAlertClick: () -> Unit = {},
    viewModel: DashboardViewModel = viewModel()
) {

    val fc by viewModel.fc.collectAsState()
    val pasos by viewModel.pasos.collectAsState()
    val historial = viewModel.historial

    val fcNormal = fc in 60..100

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = onAlertClick
            ) {

                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Alerta"
                )
            }
        }

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {

                Column {

                    TarjetaDato(
                        valor = "$fc",
                        unidad = "bpm",
                        label = "Frecuencia cardíaca",
                        colorValor = MaterialTheme.colorScheme.error,
                        icono = Icons.Default.Favorite
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    if (fcNormal) {

                        AssistChip(
                            onClick = {},
                            label = {
                                Text("Normal")
                            },
                            colors =
                                AssistChipDefaults.assistChipColors(
                                    containerColor = Color.Green
                                )
                        )

                    } else {

                        AssistChip(
                            onClick = {},
                            label = {
                                Text("Consulta al médico")
                            },
                            colors =
                                AssistChipDefaults.assistChipColors(
                                    containerColor = Color.Red
                                )
                        )
                    }
                }
            }

            item {

                TarjetaDato(
                    valor = "$pasos",
                    unidad = "pasos",
                    label = "Pasos del día",
                    colorValor = MaterialTheme.colorScheme.primary,
                    icono = Icons.Default.DirectionsWalk
                )
            }

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Historial",
                        style = MaterialTheme.typography.titleLarge
                    )

                    TextButton(
                        onClick = onHistorialClick
                    ) {

                        Text(
                            text = "Ver todo"
                        )
                    }
                }
            }

            items(historial) { lectura ->

                FilaHistorial(
                    lectura = lectura
                )
            }

            item {

                OutlinedButton(

                    onClick = {

                        val fcSimulado =
                            (60..110).random()

                        SmartHealthRepository
                            .actualizarFC(fcSimulado)

                        SmartHealthRepository
                            .actualizarPasos(
                                (3000..8000).random()
                            )
                    },

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Text(
                        "Simular dato del wearable"
                    )
                }
            }
        }
    }
}