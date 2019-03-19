package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.EntityLoader
import com.coden.starslicer.util.sqRatio

class Shield: PowerUp(PowerUpType.SHIELD) {

    companion object {
        val snapshot = EntityLoader.loadPowerUp(PowerUpType.SHIELD)
    }

    private val maxRadius = snapshot.radius
    private val lifeSpan = snapshot.lifeSpan
    private val growthSpeed = snapshot.growthSpeed

    private var radius = 0f
    private var life = 0f

    fun applyEffect() {
        SpaceCraft.isShielded = true
        SpaceCraft.shieldRadius = radius
        active = true

    }

    override fun update() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan || !SpaceCraft.isShielded) kill()

        if (radius < maxRadius) radius += growthSpeed*Gdx.graphics.deltaTime
        SpaceCraft.shieldRadius = radius


    }

    override fun kill() {
        super.kill()
        SpaceCraft.isShielded = false
    }


}