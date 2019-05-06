package com.coden.starslicer.gameObjects.powerups

import com.coden.starslicer.gameObjects.DamageGiver
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