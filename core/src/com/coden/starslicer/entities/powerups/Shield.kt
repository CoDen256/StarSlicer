package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.SpaceCraft

class Shield(val spaceCraft: SpaceCraft): PowerUp("shield") {
    override val damage = 0f
    override val continuous = true

    val maxRadius = 120f
    var radius = 0f

    val lifeSpan = 10f
    var life = 0f

    var active = false

    fun applyEffect(spaceCraft: SpaceCraft) {
        spaceCraft.isShielded = true
        active = true
        spaceCraft.shieldRadius = radius


    }

    fun update() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan) kill()

        if (radius < maxRadius) radius += maxRadius/30
        spaceCraft.shieldRadius = radius


    }

    override fun kill() {
        super.kill()
        spaceCraft.isShielded = false
    }


}