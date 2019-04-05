package com.coden.starslicer.entities.powerups

import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.util.EntityLoader

class HPBoost: PowerUp(PowerUpType.HPBOOST), DamageGiver {

    companion object {
        val snapshot = EntityLoader.loadPowerUp(PowerUpType.HPBOOST)

    }

    override val damage = snapshot.damage // healing amount

    fun applyEffect() {
        heal(SpaceCraft)
        active = true
        kill()
    }

    override fun update() {
    }


}