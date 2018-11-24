package com.hack.models.microProcessor

data class PostSensorDataRequest(
    val signalPostId: String,
    val signalSent: String
)

//Output Response: R,Y,G,20