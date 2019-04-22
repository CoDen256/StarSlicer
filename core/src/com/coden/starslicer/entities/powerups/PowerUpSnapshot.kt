package com.coden.starslicer.entities.powerups

class PowerUpSnapshot {

    val damage = -1f
    val growthSpeed = -1f
    val lifeSpan = -1f
    val radius = -1f
    val pushingSpeed = -1f

    val name = "UNDEFINED"
    val type get() = PowerUp.PowerUpType.valueOf(name)
}