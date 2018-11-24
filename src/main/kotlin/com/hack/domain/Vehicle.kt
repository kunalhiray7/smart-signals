package com.hack.domain

data class Vehicle(
        val speedInKph: Long,
        val distanceToSignalInMeters: Long,
        val status: Status
)