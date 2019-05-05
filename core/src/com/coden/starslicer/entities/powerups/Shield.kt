package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.coden.starslicer.util.Locator

class Shield: PowerUp(PowerUpType.SHIELD) {

    companion object {
        val snapshot = loader.load(PowerUpType.SHIELD)
    }

    private val maxRadius = snapshot.radius
    private val lifeSpan = snapshot.lifeSpan
    private val growthSpeed = snapshot.growthSpeed

    private var radius = 0f
    private var life = 0f

    fun applyEffect() {
        Locator.spaceCraft.isShielded = true
        Locator.spaceCraft.shieldRadius = radius
        active = true
    }

    override fun update() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan || !Locator.spaceCraft.isShielded) kill()

        if (radius < maxRadius) radius += growthSpeed*Gdx.graphics.deltaTime
        Locator.spaceCraft.shieldRadius = radius


    }

    override fun kill() {
        super.kill()
        Locator.spaceCraft.isShielded = false
    }


}