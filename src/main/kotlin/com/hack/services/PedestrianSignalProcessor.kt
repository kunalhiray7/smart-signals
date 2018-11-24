package com.hack.services

import com.hack.constants.Constants.Companion.MAX_PEDESTRIAN_ALLOWED_TO_WAIT
import com.hack.domain.SignalStatus
import com.hack.domain.SignalStatus.GREEN
import com.hack.domain.SignalStatus.RED
import com.hack.models.SignalDurationStatus
import com.hack.models.SignalRequest
import org.springframework.stereotype.Service

@Service
class PedestrianSignalProcessor {

    companion object {
        fun getPedestrianSignalStatus(signals: List<SignalRequest>, currentSignal: SignalRequest, nextStatus: SignalStatus): SignalDurationStatus {
            var status: SignalStatus = nextStatus
            when {
                currentSignal.pedestrians == 0L -> status = RED
                currentSignal.pedestrians >= MAX_PEDESTRIAN_ALLOWED_TO_WAIT -> status = GREEN
                currentSignal.secondsInCurrentStatus > 0 -> status = currentSignal.currentStatus
            }

            return SignalDurationStatus(
                    status = status,
                    duration = currentSignal.currentStatus.getConfigureDuration(currentSignal) - currentSignal.secondsInCurrentStatus)
        }
    }
}