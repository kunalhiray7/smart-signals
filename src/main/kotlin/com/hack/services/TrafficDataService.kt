package com.hack.services

import com.hack.models.SensorProcessRequest
import com.hack.models.SensorProcessingResponse
import com.hack.models.SignalRequest
import com.hack.models.microProcessor.PostSensorDataRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TrafficDataService() {

    @Autowired
    private lateinit var signalProcessingService: SignalProcessingService

    fun prepareData(postSernsorDataRequest: PostSensorDataRequest) {

    }

    fun getTrafficSignalData(): String {

        val sensorProcessRequest = SensorProcessRequest(routeId = "routeId",
                signals = getSignalsData()
        )
        val sensorProcessingResponse = signalProcessingService.process(sensorProcessRequest)
        return transformSignalsData(sensorProcessingResponse)
    }

    private fun transformSignalsData(sensorProcessingResponse: SensorProcessingResponse): String {
        var defaultOutput = "" //"R,G,G,30"
        var duration =30L
        sensorProcessingResponse.signals.forEach {signalResponse ->
            if(signalResponse.id == "1"){
                duration = signalResponse.durationToNextStatus
            }
            defaultOutput += signalResponse.nextStatus
        }
        defaultOutput += duration
        return defaultOutput
    }

    private fun getSignalsData(): List<SignalRequest> {
        return emptyList()
    }
}