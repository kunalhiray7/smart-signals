package com.hack.models

class SensorProcessRequest(
    val routeId: String,
    val signals: List<SignalRequest>
)