package com.coden.starslicer.gameObjects.powerups

enum class PowerUpType {
    SHIELD, HPBOOST, SHOCKWAVE, RANDOM;
    companion object {
        fun convert(id: Int) = PowerUpType.values()[id]
        fun convert(powerUpType: PowerUpType) = when (powerUpType){
            SHIELD -> 0
            HPBOOST -> 1
            SHOCKWAVE -> 2
            RANDOM -> 3
        }
    }
}