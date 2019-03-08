package com.coden.starslicer.entities.powerups

import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.SpaceCraft

class Shield: PowerUp("shield") {
    override val damage = 0f
    override val continuous = true

    val radius = 60f

    val lifeSpan = 20f
    var life = 0f

    fun applyEffect(spaceCraft: SpaceCraft) {
        spaceCraft.isShielded = true
    }

}