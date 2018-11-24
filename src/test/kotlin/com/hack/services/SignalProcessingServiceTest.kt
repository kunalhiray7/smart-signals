package com.hack.services

import com.hack.domain.SignalStatus
import com.hack.domain.Status
import com.hack.domain.Vehicle
import com.hack.models.SensorProcessRequest
import com.hack.models.SignalRequest
import org.junit.Assert
import org.junit.Test

class SignalProcessingServiceTest {

    private val signalProcessingService = SignalProcessingService()

    private val vehicle1 = Vehicle(
            speedInKph = 0,
            distanceToSignalInMeters = 0,
            status = Status.STATIONARY
    )

    private val vehicle2 = Vehicle(
            speedInKph = 10,
            distanceToSignalInMeters = 10,
            status = Status.MOVING
    )

    private val signal1 = SignalRequest(
            id = "1234",
            isPedestrianSignal = false,
            currentStatus = SignalStatus.RED,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 25,
            vehicles = listOf(vehicle1),
            pedestrians = 0
    )

    private val signal2 = SignalRequest(
            id = "1235",
            isPedestrianSignal = false,
            currentStatus = SignalStatus.GREEN,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 35,
            vehicles = listOf(vehicle2),
            pedestrians = 0
    )

    private val signal3 = SignalRequest(
            id = "1236",
            isPedestrianSignal = true,
            currentStatus = SignalStatus.RED,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 30,
            vehicles = emptyList(),
            pedestrians = 5
    )
    private val signals = listOf(signal1, signal2, signal3)
    private val request = SensorProcessRequest(routeId = "erty678vbn", signals = signals)

    @Test
    fun `should return the processed data`() {
        val result = signalProcessingService.process(request)

    }
}