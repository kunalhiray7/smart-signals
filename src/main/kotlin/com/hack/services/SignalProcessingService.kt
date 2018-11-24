package com.hack.services

import com.hack.domain.SignalStatus
import com.hack.models.SensorProcessRequest
import com.hack.models.SensorProcessingResponse
import com.hack.models.SignalRequest
import com.hack.models.SignalResponse
import org.springframework.stereotype.Service

@Service
class SignalProcessingService {

    fun process(sensorProcessRequest: SensorProcessRequest): SensorProcessingResponse =
            SensorProcessingResponse(
                    routeId = sensorProcessRequest.routeId,
                    signals = sensorProcessRequest.signals.map { processSignal(sensorProcessRequest.signals, it) }
            )

    private fun processSignal(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalResponse =
            SignalResponse(
                    id = currentSignal.id,
                    isPedestrianSignal = currentSignal.isPedestrianSignal,
                    nextStatus = determineStatus(signals, currentSignal),
                    durationToNextStatus = determineDuration(signals, currentSignal)
            )

    private fun determineDuration(signals: List<SignalRequest>, currentSignal: SignalRequest): Long {
        return 0L
    }

    private fun determineStatus(signals: List<SignalRequest>, currentSignal: SignalRequest): SignalStatus =
            currentSignal.currentStatus.getNextStatus(signals, currentSignal)
}