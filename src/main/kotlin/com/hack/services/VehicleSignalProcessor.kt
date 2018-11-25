package com.hack.services

import com.hack.constants.Constants
import com.hack.domain.SignalStatus
import com.hack.models.SignalDurationStatus
import com.hack.models.SignalRequest

class VehicleSignalProcessor {
    companion object {
        fun getVehicleSignalStatus(signals: List<SignalRequest>, currentSignal: SignalRequest, nextStatus: SignalStatus): SignalDurationStatus {
            var status: SignalStatus = nextStatus
            when {
                currentSignal.vehicles.isEmpty() -> status = SignalStatus.RED
                currentSignal.vehicles.size >= Constants.MAX_VEHICLE_ALLOWED_TO_WAIT -> status = SignalStatus.GREEN
                currentSignal.secondsInCurrentStatus > 0 -> status = currentSignal.currentStatus
            }
            var duration = currentSignal.currentStatus.getConfigureDuration(currentSignal) - currentSignal.secondsInCurrentStatus
            if(duration <0){
                duration = 0
            }

            return SignalDurationStatus(
                    status = status,
                    duration = duration)

        }
    }
}