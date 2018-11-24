package com.hack.models

import com.hack.domain.SignalStatus
import com.hack.domain.Vehicle

data class SignalRequest(
        val id: String,
        val isPedestrianSignal: Boolean,
        val currentStatus: SignalStatus,
        val secondsInCurrentStatus: Long,
        val configuredRedDuration: Long,
        val configuredGreenDuration: Long,
        val configuredYellowDuration: Long,
        val vehicles: List<Vehicle>,
        val pedestrians: Long
)