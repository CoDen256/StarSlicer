package com.coden.starslicer.entities.powerups

abstract class PowerUp(val name: PowerUpType){

    var isDead = false
    var active = false

    abstract fun update()

    open fun kill() {
        isDead = true
    }

}