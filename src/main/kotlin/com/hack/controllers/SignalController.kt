package com.hack.controllers

import com.hack.models.SensorProcessRequest
import com.hack.models.microProcessor.PostSensorDataRequest
import com.hack.services.SignalService
import com.hack.services.TrafficDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class SignalController(private val signalService: SignalService) {

    @Autowired
    private lateinit var trafficDataService: TrafficDataService

    @PutMapping("/signals/{signalId}/{signalSent}")
    fun processSensorData(@PathVariable("signalId") signalId: String, @PathVariable("signalSent") signalSent: String) {
        trafficDataService.prepareData(PostSensorDataRequest(signalId, signalSent))
    }

    @GetMapping("/signals")
    fun getTrafficSignalData(): String{
        return trafficDataService.getTrafficSignalData()
    }
}