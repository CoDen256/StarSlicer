package com.coden.starslicer.entities.powerups

import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.util.EntityLoader
import com.coden.starslicer.util.Locator

class HPBoost: PowerUp(PowerUpType.HPBOOST), DamageGiver {

    companion object {
        val snapshot = loader.load(PowerUpType.HPBOOST)

    }

    override val damage = snapshot.damage // healing amount

    fun applyEffect() {
        heal(Locator.spaceCraft)
        active = true
        kill()
    }

    override fun update() {
    }


}