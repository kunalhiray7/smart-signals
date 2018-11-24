package com.hack.services

import com.hack.domain.SignalStatus
import com.hack.domain.Status
import com.hack.domain.Vehicle
import com.hack.models.SignalRequest
import org.junit.Assert.*
import org.junit.Test

class VehicleSignalProcessorTest {

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
            vehicles = emptyList(),
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
            vehicles = listOf(vehicle1, vehicle2, vehicle1, vehicle2, vehicle1, vehicle2, vehicle1, vehicle2, vehicle1, vehicle2),
            pedestrians = 0
    )

    private val signal3 = SignalRequest(
            id = "1235",
            isPedestrianSignal = false,
            currentStatus = SignalStatus.GREEN,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 35,
            vehicles = listOf(vehicle1, vehicle2, vehicle1),
            pedestrians = 0
    )

    private val signal4 = SignalRequest(
            id = "1235",
            isPedestrianSignal = false,
            currentStatus = SignalStatus.GREEN,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 0,
            vehicles = listOf(vehicle1, vehicle2, vehicle1),
            pedestrians = 0
    )

    private val signalWithNoPassenger = SignalRequest(
            id = "1236",
            isPedestrianSignal = true,
            currentStatus = SignalStatus.RED,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 30,
            vehicles = emptyList(),
            pedestrians = 0
    )

    @Test
    fun `should return RED when no vehicles are waiting on signal`() {
        val signals = listOf(signal1, signal2, signalWithNoPassenger)

        assertEquals(SignalStatus.RED, VehicleSignalProcessor.getVehicleSignalStatus(signals, signal1, SignalStatus.RED).status)
    }

    @Test
    fun `should return GREEN when max allowed vehicle to wait limit is crossed`() {
        val signals = listOf(signal1, signal2, signalWithNoPassenger)

        assertEquals(SignalStatus.GREEN, VehicleSignalProcessor.getVehicleSignalStatus(signals, signal2, SignalStatus.RED).status)
    }

    @Test
    fun `should return current status when there is still time to complete the signal`() {
        val signals = listOf(signal1, signal3, signalWithNoPassenger)

        assertEquals(SignalStatus.GREEN, VehicleSignalProcessor.getVehicleSignalStatus(signals, signal3, SignalStatus.GREEN).status)
    }

    @Test
    fun `should return next status when time in current status is elapsed`() {
        val signals = listOf(signal1, signal4, signalWithNoPassenger)

        assertEquals(SignalStatus.YELLOW, VehicleSignalProcessor.getVehicleSignalStatus(signals, signal4, SignalStatus.YELLOW).status)
    }
}