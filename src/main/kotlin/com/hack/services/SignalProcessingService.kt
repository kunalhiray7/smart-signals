package com.hack.services

import com.hack.models.*

class SignalProcessingService {

    fun process(sensorProcessRequest: SensorProcessRequest): SensorProcessingResponse =
            SensorProcessingResponse(
                    routeId = sensorProcessRequest.routeId,
                    signals = sensorProcessRequest.signals.map { processSignal(sensorProcessRequest.signals, it) }
            )

    private fun processSignal(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalResponse {
        val signalDurationStatus = determineStatus(signals, currentSignal)
        return SignalResponse(
                id = currentSignal.id,
                isPedestrianSignal = currentSignal.isPedestrianSignal,
                nextStatus = signalDurationStatus.status,
                durationToNextStatus = signalDurationStatus.duration
        )
    }

    private fun determineDuration(signals: List<SignalRequest>, currentSignal: SignalRequest): Long {

        return 0L
    }

    private fun determineStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalDurationStatus =
            currentSignal.currentStatus.getNextStatus(signals, currentSignal)
}