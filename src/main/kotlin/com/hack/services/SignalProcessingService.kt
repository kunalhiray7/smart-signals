package com.hack.services

import com.hack.models.*
import org.springframework.stereotype.Service

@Service
class SignalProcessingService {

    private var sensorProcessingResponse = SensorProcessingResponse()

    fun process(sensorProcessRequest: SensorProcessRequest): SensorProcessingResponse {
        this.sensorProcessingResponse = SensorProcessingResponse(
                routeId = sensorProcessRequest.routeId,
                signals = sensorProcessRequest.signals.map { processSignal(sensorProcessRequest.signals, it) }
        )

        return this.sensorProcessingResponse
    }

    fun getCurrentStatus(): SensorProcessingResponse = this.sensorProcessingResponse

    private fun processSignal(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalResponse {
        val signalDurationStatus = determineStatus(signals, currentSignal)
        return SignalResponse(
                id = currentSignal.id,
                isPedestrianSignal = currentSignal.isPedestrianSignal,
                nextStatus = signalDurationStatus.status,
                durationToNextStatus = signalDurationStatus.duration
        )
    }

    private fun determineStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalDurationStatus =
            currentSignal.currentStatus.getNextStatus(signals, currentSignal)
}