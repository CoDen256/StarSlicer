package com.coden.starslicer.entities.powerups

import com.coden.starslicer.entities.SpaceCraft.SpaceCraft
import com.coden.starslicer.util.EntityLoader

class HPBoost: PowerUp(PowerUpType.HPBOOST) {

    companion object {
        val snapshot = EntityLoader.loadPowerUp(PowerUpType.HPBOOST)
    }

    private val heal = snapshot.damage // healing amount

    fun applyEffect() {
        SpaceCraft.takeDamage(heal)
        active = true
        kill()
    }

    override fun update() {
    }


}