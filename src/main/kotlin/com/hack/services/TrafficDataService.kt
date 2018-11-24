package com.hack.services

import com.hack.domain.SignalStatus
import com.hack.domain.Vehicle
import com.hack.models.*
import com.hack.domain.Status
import com.hack.models.microProcessor.PostSensorDataRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TrafficDataService() {

    @Autowired
    private lateinit var signalProcessingService: SignalProcessingService

    fun postSensorData(postSernsorDataRequest: PostSensorDataRequest) {
        if (postSernsorDataRequest.signalPostId == "1") {
            if (postSernsorDataRequest.signalSent == "1") {
                currentSignalStatus1 = currentSignalStatus1.copy(pedestrians = 6, changeDate = Instant.now())
            } else if (postSernsorDataRequest.signalSent == "0") {
                if (currentSignalStatus1.changeDate.epochSecond - Instant.now().epochSecond > SecondsToWaitBeforeAssumingNoMovement) {
                    currentSignalStatus1 = currentSignalStatus1.copy(pedestrians = 0, changeDate = Instant.now())
                }
            }
        }
        if (postSernsorDataRequest.signalPostId == "2") {
            if (postSernsorDataRequest.signalSent == "1") {
                currentSignalStatus2 = currentSignalStatus2.copy(pedestrians = 6, changeDate = Instant.now())
            } else if (postSernsorDataRequest.signalSent == "0") {
                if (currentSignalStatus2.changeDate.epochSecond - Instant.now().epochSecond > SecondsToWaitBeforeAssumingNoMovement) {
                    currentSignalStatus2 = currentSignalStatus2.copy(pedestrians = 0, changeDate = Instant.now())
                }
            }
        }
        if (postSernsorDataRequest.signalPostId == "3") {
            if (postSernsorDataRequest.signalSent == "1") {
                currentSignalStatus3 = currentSignalStatus3.copy(pedestrians = 6, changeDate = Instant.now())
            } else if (postSernsorDataRequest.signalSent == "0") {
                currentSignalStatus3 = currentSignalStatus3.copy(pedestrians = 0, changeDate = Instant.now())
            }
        }
    }

    fun getTrafficSignalData(): String {

        val sensorProcessRequest = SensorProcessRequest(routeId = "routeId",
                signals = getSignalsData()
        )
        val sensorProcessingResponse = signalProcessingService.process(sensorProcessRequest)
        return transformSignalsData(sensorProcessingResponse)
    }


    fun getTrafficSignalDataJson(): SensorProcessingResponse {

        val sensorProcessRequest = SensorProcessRequest(routeId = "routeId",
                signals = getSignalsData()
        )
        return signalProcessingService.process(sensorProcessRequest)
    }

    private fun transformSignalsData(sensorProcessingResponse: SensorProcessingResponse): String {
        var defaultOutput = "" //"R,G,G,30"
        var duration = 30L
        sensorProcessingResponse.signals.forEach { signalResponse ->
            if (signalResponse.id == "1") {
                if (signalResponse.durationToNextStatus > 0) {
                    duration = signalResponse.durationToNextStatus
                } else {
                    duration = 0
                }
            }
            defaultOutput = defaultOutput + signalResponse.nextStatus + ","
        }
        defaultOutput += duration
        return defaultOutput
    }

    private fun getSignalsData(): List<SignalRequest> {
        return listOf(
                SignalRequest(currentSignalStatus1.signalId, currentSignalStatus1.isPedestrianSignal,
                        currentSignalStatus1.currentStatus,
                        Instant.now().epochSecond - currentSignalStatus1.changeDate.epochSecond,
                        20L, 60L, 5L,
                        listOf(Vehicle(40L, 5, Status.STATIONARY)), currentSignalStatus1.pedestrians
                ),
                SignalRequest(currentSignalStatus2.signalId, currentSignalStatus2.isPedestrianSignal,
                        currentSignalStatus2.currentStatus,
                        Instant.now().epochSecond - currentSignalStatus2.changeDate.epochSecond,
                        20L, 60L, 5L,
                        listOf(Vehicle(40L, 5, Status.STATIONARY)), currentSignalStatus2.pedestrians
                ),
                SignalRequest(currentSignalStatus3.signalId, currentSignalStatus3.isPedestrianSignal,
                        currentSignalStatus3.currentStatus,
                        Instant.now().epochSecond - currentSignalStatus3.changeDate.epochSecond,
                        60L, 20L, 5L,
                        emptyList(), currentSignalStatus3.pedestrians
                )

        )
    }

    companion object {
        private var currentSignalStatus1: CurrentSignalStatus = CurrentSignalStatus("1", false, SignalStatus.GREEN, Instant.now(), 0)
        private var currentSignalStatus2: CurrentSignalStatus = CurrentSignalStatus("2", false, SignalStatus.GREEN, Instant.now(), 0)
        private var currentSignalStatus3: CurrentSignalStatus = CurrentSignalStatus("3", true, SignalStatus.RED, Instant.now(), 0)


        private val SecondsToWaitBeforeAssumingNoMovement = 10
    }

}

data class CurrentSignalStatus(
        val signalId: String,
        val isPedestrianSignal: Boolean,
        val currentStatus: SignalStatus,
        val changeDate: Instant,
        val pedestrians: Long

)
