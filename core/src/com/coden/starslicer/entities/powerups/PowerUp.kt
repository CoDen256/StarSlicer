package com.coden.starslicer.entities.powerups

abstract class PowerUp(val type: PowerUpType){

    companion object {
        val powerUps = ArrayList<PowerUp>()
    }

    enum class PowerUpType {
        SHIELD, HPBOOST, SHOCKWAVE
    }

    init {
        powerUps.add(this)
    }

    var isDead = false
    var active = false

    abstract fun update()

    open fun kill() {
        isDead = true
    }

}