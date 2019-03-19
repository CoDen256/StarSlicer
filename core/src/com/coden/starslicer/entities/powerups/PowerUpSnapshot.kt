package com.coden.starslicer.entities.powerups

class PowerUpSnapshot {

    val damage = 0f
    val growthSpeed = 0f
    val lifeSpan = 0f
    val radius = 0f
    val pushingSpeed = 0f

    val name = "UNDEFINED"
    val type get() = PowerUp.Converter.get(name) // TODO: Change to such definition
}