package com.coden.starslicer.entities.powerups

abstract class PowerUp(val type: PowerUpType){

    enum class PowerUpType {
        SHIELD, HPBOOST, SHOCKWAVE;

    }

    object Converter{
        private val map = PowerUpType.values().map{it.toString() to it}.toMap()
        fun get(name: String) = map[name]!!
    }


    var isDead = false
    var active = false

    abstract fun update()

    open fun kill() {
        isDead = true
    }

}