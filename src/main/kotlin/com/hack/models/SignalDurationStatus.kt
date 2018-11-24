package com.hack.models

import com.hack.domain.SignalStatus

data class SignalDurationStatus(
        val status: SignalStatus,
        val duration: Long
)