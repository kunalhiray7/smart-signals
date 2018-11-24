package com.hack.controllers

import com.hack.domain.Signal
import com.hack.models.SensorProcessRequest
import com.hack.models.SignalRequest
import com.hack.services.SignalService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignalController(private val signalService: SignalService) {

//    @PostMapping("/signals")
//    fun create(@RequestBody signalRequest: SignalRequest): Signal {
//        return signalService.createSignal(signalRequest)
//    }

    @PutMapping("/signals/process")
    fun processSensorData(@RequestBody sensorProcessRequest: SensorProcessRequest) {

    }
}