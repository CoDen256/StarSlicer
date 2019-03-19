package com.coden.starslicer.entities.powerups

class PowerUpSnapshot {

    val name = "UNDEFINED"
    val type get() = PowerUp.Converter.get(name) // TODO: Change to such definition
}