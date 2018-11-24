package com.hack.services

import com.hack.constants.Constants.Companion.MAX_PEDESTRIAN_ALLOWED_TO_WAIT
import com.hack.domain.SignalStatus
import com.hack.domain.SignalStatus.GREEN
import com.hack.domain.SignalStatus.RED
import com.hack.models.SignalRequest
import org.springframework.stereotype.Service

@Service
class PedestrianSignalProcessor {

    companion object {
        fun getPedestrianSignalStatus(signals: List<SignalRequest>, currentSignal: SignalRequest, nextStatus: SignalStatus): SignalStatus {
            if (currentSignal.pedestrians == 0L) {
                return RED
            } else {
                if(currentSignal.pedestrians >= MAX_PEDESTRIAN_ALLOWED_TO_WAIT) {
                    return GREEN
                }
                if(currentSignal.secondsInCurrentStatus > 0) {
                    return currentSignal.currentStatus
                }
            }
            return nextStatus
        }
    }
}