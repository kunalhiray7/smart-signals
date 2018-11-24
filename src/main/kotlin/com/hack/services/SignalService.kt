package com.hack.services

import com.hack.domain.Signal
import com.hack.models.SignalRequest
import com.hack.repositories.SignalRepository
import org.springframework.stereotype.Service

@Service
class SignalService(private val signalRepository: SignalRepository) {

//    fun createSignal(signalRequest: SignalRequest): Signal {
//        return signalRepository.save(signalRequest.toDomain())
//    }
}