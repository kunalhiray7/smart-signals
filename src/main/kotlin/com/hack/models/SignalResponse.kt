package com.hack.models

import com.hack.domain.SignalStatus

data class SignalResponse(
        val id: String,
        val isPedestrianSignal: Boolean,
        var nextStatus: SignalStatus,
        val durationToNextStatus: Long
)