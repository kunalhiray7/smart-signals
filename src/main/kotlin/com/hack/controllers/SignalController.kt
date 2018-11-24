package com.hack.controllers

import com.hack.models.microProcessor.PostSensorDataRequest
import com.hack.services.TrafficDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SignalController() {

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
}