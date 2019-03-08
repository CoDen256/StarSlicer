package com.coden.starslicer.entities.powerups

import com.coden.starslicer.entities.SpaceCraft

class HPBoost: PowerUp("hpboost") {
    override val damage = -100f // healing amount

    override val continuous = false


    fun applyEffect(spaceCraft: SpaceCraft) {
        spaceCraft.takeDamage(damage)
        kill()
    }

    fun update() {

    }


}