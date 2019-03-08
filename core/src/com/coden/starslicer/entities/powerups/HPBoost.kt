package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.SpaceCraft

class HPBoost: PowerUp(PowerUpType.HPBOOST) {

    val damage = -100f // healing amount

    fun applyEffect(spaceCraft: SpaceCraft) {
        spaceCraft.takeDamage(damage)
        active = true
        kill()
    }

    override fun update() {

    }


}