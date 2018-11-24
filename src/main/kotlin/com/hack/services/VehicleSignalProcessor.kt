package com.hack.services

import com.hack.domain.SignalStatus
import com.hack.models.SignalRequest

class VehicleSignalProcessor {
    companion object {
        fun getVehicleSignalStatus(signals: List<SignalRequest>, currentSignal: SignalRequest, nextStatus: SignalStatus): SignalStatus {
            return SignalStatus.RED
        }
    }
}