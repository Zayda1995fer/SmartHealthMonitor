package mx.utng.smarthealthmonitor.wear.data

data class HeartRateData(
    val bpm: Int,
    val timestamp: Long = System.currentTimeMillis()
)