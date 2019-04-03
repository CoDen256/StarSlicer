package com.coden.starslicer.entities.powerups

abstract class PowerUp(val type: PowerUpType){

    enum class PowerUpType {
        SHIELD, HPBOOST, SHOCKWAVE, RANDOM;

    }

    var isDead = false
    var active = false

    abstract fun update()

    open fun kill() {
        isDead = true
    }

}