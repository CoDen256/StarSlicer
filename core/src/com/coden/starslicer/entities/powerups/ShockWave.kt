package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.*

class ShockWave: PowerUp(PowerUpType.SHOCKWAVE) {

    private val maxRadius = 1000 * sqRatio // ratio of growth
    private val iterations = 18
    private val maxLife = iterations/60f // 0.333 seconds of life
    private val damage = 100f / iterations // 100 damage by 18 iterations

    var radius = 0f
    private var life = 0f

    fun applyEffect() {
        active = true
    }

    override fun update() {
        life += Gdx.graphics.deltaTime
        if (life < maxLife) radius += maxRadius/15
        else kill()

        damageAll()
    }

    private fun damageAll() {
        val iterator = entities.iterator()
        while (iterator.hasNext()) {
            val entity = iterator.next()
            if (dist2(entity.pos, spaceCraftCenter) < radius){
                entity.takeDamage(damage)
            }
        }
    }
}
