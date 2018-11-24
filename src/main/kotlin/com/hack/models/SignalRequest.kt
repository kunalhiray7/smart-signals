package com.hack.models

import com.hack.domain.Signal

data class SignalRequest(
        val routeName: String
) {
    fun toDomain(): Signal = Signal(routeName = this.routeName)
}