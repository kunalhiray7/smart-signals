package com.hack.services

import com.hack.constants.Constants
import com.hack.domain.SignalStatus
import com.hack.models.SignalRequest

class VehicleSignalProcessor {
    companion object {
        fun getVehicleSignalStatus(signals: List<SignalRequest>, currentSignal: SignalRequest, nextStatus: SignalStatus): SignalStatus {
            if (currentSignal.vehicles.isEmpty()) {
                return SignalStatus.RED
            } else {
                if(currentSignal.vehicles.size >= Constants.MAX_VEHICLE_ALLOWED_TO_WAIT) {
                    return SignalStatus.GREEN
                }
                if(currentSignal.secondsInCurrentStatus > 0) {
                    return currentSignal.currentStatus
                }
            }
            return nextStatus
        }
    }
}