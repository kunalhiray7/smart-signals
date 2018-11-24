package com.hack.domain

import com.hack.models.SignalDurationStatus
import com.hack.models.SignalRequest
import com.hack.services.PedestrianSignalProcessor
import com.hack.services.VehicleSignalProcessor

enum class SignalStatus {
    RED {
        override fun getConfigureDuration(currentSignal: SignalRequest): Long =
                currentSignal.configuredRedDuration

        override fun getNextStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalDurationStatus {
            return getSignalStatus(currentSignal, signals, GREEN)
        }
    },

    GREEN {
        override fun getConfigureDuration(currentSignal: SignalRequest): Long =
                currentSignal.configuredGreenDuration

        override fun getNextStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalDurationStatus {
            return getSignalStatus(currentSignal, signals, YELLOW)
        }
    },

    YELLOW {
        override fun getConfigureDuration(currentSignal: SignalRequest): Long =
                currentSignal.configuredYellowDuration

        override fun getNextStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalDurationStatus {
            return getSignalStatus(currentSignal, signals, RED)
        }
    };

    abstract fun getNextStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalDurationStatus

    abstract fun getConfigureDuration(currentSignal: SignalRequest): Long

    fun getSignalStatus(currentSignal: SignalRequest, signals: List<SignalRequest>, nextStatus: SignalStatus): SignalDurationStatus {
        return if (currentSignal.isPedestrianSignal) {
            PedestrianSignalProcessor.getPedestrianSignalStatus(signals, currentSignal, nextStatus)
        } else {
            VehicleSignalProcessor.getVehicleSignalStatus(signals, currentSignal, nextStatus)
        }
    }
}