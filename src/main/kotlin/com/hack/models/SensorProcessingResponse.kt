package com.hack.models

data class SensorProcessingResponse(
        val routeId: String = "",
        val signals: List<SignalResponse> = emptyList()
)