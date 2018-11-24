package com.hack.services

import com.hack.domain.SignalStatus
import com.hack.domain.Status
import com.hack.domain.Vehicle
import com.hack.models.SignalRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class PedestrianSignalProcessorTest {

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

    private val signalWithMaxPedestrians = SignalRequest(
            id = "1236",
            isPedestrianSignal = true,
            currentStatus = SignalStatus.RED,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 30,
            vehicles = emptyList(),
            pedestrians = 6
    )

    private val signalWithTimeToComplete = SignalRequest(
            id = "1236",
            isPedestrianSignal = true,
            currentStatus = SignalStatus.RED,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 30,
            vehicles = emptyList(),
            pedestrians = 3
    )

    private val signalWithNoTime = SignalRequest(
            id = "1236",
            isPedestrianSignal = true,
            currentStatus = SignalStatus.RED,
            configuredGreenDuration = 60,
            configuredRedDuration = 60,
            configuredYellowDuration = 10,
            secondsInCurrentStatus = 0,
            vehicles = emptyList(),
            pedestrians = 3
    )

    @Test
    fun `should return RED when no passengers are waiting for signal`() {
        val signals = listOf(signal1, signal2, signalWithNoPassenger)

        assertEquals(SignalStatus.RED,
                PedestrianSignalProcessor.getPedestrianSignalStatus(signals, signalWithNoPassenger, SignalStatus.RED).status)
    }

    @Test
    fun `should return GREEN when max allowed pedestrians to wait limit is crossed`() {
        val signals = listOf(signal1, signal2, signalWithMaxPedestrians)

        assertEquals(SignalStatus.GREEN,
                PedestrianSignalProcessor.getPedestrianSignalStatus(signals, signalWithMaxPedestrians, SignalStatus.RED).status)
    }

    @Test
    fun `should return current status when there is still time to complete the signal`() {
        val signals = listOf(signal1, signal2, signalWithTimeToComplete)

        assertEquals(SignalStatus.RED,
                PedestrianSignalProcessor.getPedestrianSignalStatus(signals, signalWithTimeToComplete, SignalStatus.RED).status)
    }

    @Test
    fun `should return next status when time in current status is elapsed`() {
        val signals = listOf(signal1, signal2, signalWithNoTime)

        assertEquals(SignalStatus.GREEN,
                PedestrianSignalProcessor.getPedestrianSignalStatus(signals, signalWithNoTime, SignalStatus.GREEN).status)
    }
}