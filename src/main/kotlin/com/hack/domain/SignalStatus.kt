package com.hack.domain

import com.hack.models.SignalRequest
import com.hack.services.PedestrianSignalProcessor
import com.hack.services.VehicleSignalProcessor

enum class SignalStatus {
    RED {
        override fun getNextStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalStatus {
            return getSignalStatus(currentSignal, signals, GREEN)
        }
    },

    GREEN {
        override fun getNextStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalStatus {
            return getSignalStatus(currentSignal, signals, YELLOW)
        }
    },

    YELLOW {
        override fun getNextStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalStatus {
            return getSignalStatus(currentSignal, signals, RED)
        }
    };

    abstract fun getNextStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalStatus

    fun getSignalStatus(currentSignal: SignalRequest, signals: List<SignalRequest>, nextStatus: SignalStatus): SignalStatus {
        return if (currentSignal.isPedestrianSignal) {
            PedestrianSignalProcessor.getPedestrianSignalStatus(signals, currentSignal, nextStatus)
        } else {
            VehicleSignalProcessor.getVehicleSignalStatus(signals, currentSignal, nextStatus)
        }
    }
}