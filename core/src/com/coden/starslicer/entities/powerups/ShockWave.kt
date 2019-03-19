package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.*
import com.coden.starslicer.util.EntityLoader.loadPowerUp

class ShockWave: PowerUp(PowerUpType.SHOCKWAVE) {

    companion object {
        val snapshot = loadPowerUp(PowerUpType.SHOCKWAVE)
    }

    private val growthSpeed = snapshot.growthSpeed * sqRatio
    private val lifeSpan = snapshot.lifeSpan
    private val deltaDamage = snapshot.damage / (lifeSpan*60)

    var radius = 0f
    var damage = 0f
    private var life = 0f

    fun applyEffect() {
        active = true
    }

    override fun update() {
        life += Gdx.graphics.deltaTime
        damage += deltaDamage
        if (life < lifeSpan) radius += growthSpeed * Gdx.graphics.deltaTime
        else kill()

        //Log.info("Radius: $radius - Life: $life - Damage: $damage")
        damageAll()
    }

    private fun damageAll() {
        val iterator = entities.iterator()
        while (iterator.hasNext()) {
            val entity = iterator.next()
            if (dist2(entity.pos, spaceCraftCenter) < radius){
                entity.takeDamage(deltaDamage)
            }
        }
    }
}
