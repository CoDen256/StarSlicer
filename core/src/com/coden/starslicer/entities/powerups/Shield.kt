package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.sqRatio

class Shield: PowerUp(PowerUpType.SHIELD) {

    private val maxRadius = 180f * sqRatio
    private val lifeSpan = 10f

    private var radius = 0f
    private var life = 0f

    fun applyEffect() {
        SpaceCraft.isShielded = true
        active = true
        SpaceCraft.shieldRadius = radius
    }

    override fun update() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan || !SpaceCraft.isShielded) kill()

        if (radius < maxRadius) radius += maxRadius/30
        SpaceCraft.shieldRadius = radius


    }

    override fun kill() {
        super.kill()
        SpaceCraft.isShielded = false
    }


}