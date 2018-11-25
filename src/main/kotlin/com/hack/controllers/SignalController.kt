package com.hack.controllers

import com.hack.models.SensorProcessingResponse
import com.hack.models.microProcessor.PostSensorDataRequest
import com.hack.services.SignalProcessingService
import com.hack.services.TrafficDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:3000"], maxAge = 3000)
@RestController
class SignalController(private val signalProcessingService: SignalProcessingService) {

    @Autowired
    private lateinit var trafficDataService: TrafficDataService

    @PutMapping("/signals/{signalId}/{signalSent}")
    fun processSensorData(@PathVariable("signalId") signalId: String, @PathVariable("signalSent") signalSent: String) {
        trafficDataService.postSensorData(PostSensorDataRequest(signalId, signalSent))
    }

    @GetMapping("/signals")
    fun getTrafficSignalData(): String{
        return trafficDataService.getTrafficSignalData()
    }

    @GetMapping("/signals/v2")
    fun getProcessedSignalsForUi(): SensorProcessingResponse = trafficDataService.getTrafficSignalDataJson()

    @GetMapping("/signals/v3")
    fun getProcessedSignalsForUi1(): SensorProcessingResponse = signalProcessingService.getCurrentStatus()

}