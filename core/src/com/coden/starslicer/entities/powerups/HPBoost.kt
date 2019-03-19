package com.coden.starslicer.entities.powerups

import com.coden.starslicer.entities.SpaceCraft

class HPBoost: PowerUp(PowerUpType.HPBOOST) {

    val damage = -100f // healing amount

    fun applyEffect() {
        SpaceCraft.takeDamage(damage)
        active = true
        kill()
    }

    override fun update() {

    }


}