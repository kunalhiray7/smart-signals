package com.hack.services

import com.hack.domain.SignalStatus
import com.hack.models.*
import org.springframework.stereotype.Service

@Service
class SignalProcessingService {

    private var sensorProcessingResponse = SensorProcessingResponse()

    fun process(sensorProcessRequest: SensorProcessRequest): SensorProcessingResponse {
        println(sensorProcessRequest.signals[0].vehicles.size)
        println(sensorProcessRequest.signals[0].pedestrians)
        println(sensorProcessRequest.signals[1].vehicles.size)
        println(sensorProcessRequest.signals[1].pedestrians)
        println(sensorProcessRequest.signals[2].vehicles.size)
        println(sensorProcessRequest.signals[2].pedestrians)
        this.sensorProcessingResponse = SensorProcessingResponse(
                routeId = sensorProcessRequest.routeId,
                signals = sensorProcessRequest.signals.map { processSignal(sensorProcessRequest.signals, it) }
        )

        getFinalOutput()

        return this.sensorProcessingResponse
    }

    fun getCurrentStatus(): SensorProcessingResponse {
        getFinalOutput()

        return this.sensorProcessingResponse
    }

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

    private fun getFinalOutput() {
        val pedestrianSignalStatus = this.sensorProcessingResponse.signals.find { it.isPedestrianSignal }!!.nextStatus

        this.sensorProcessingResponse.signals.forEach {
            if(!it.isPedestrianSignal) {
                when (pedestrianSignalStatus) {
                    SignalStatus.GREEN -> it.nextStatus = SignalStatus.RED
                    SignalStatus.RED -> it.nextStatus = SignalStatus.GREEN
                    SignalStatus.YELLOW -> it.nextStatus = SignalStatus.RED
                }
            }
        }
    }
}