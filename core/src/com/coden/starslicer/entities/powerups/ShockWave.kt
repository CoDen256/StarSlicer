package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.util.*
import com.coden.starslicer.util.EntityLoader.loadPowerUp

class ShockWave: PowerUp(PowerUpType.SHOCKWAVE), DamageGiver {

    companion object {
        val snapshot = loadPowerUp(PowerUpType.SHOCKWAVE)
    }

    private val pushingSpeed = snapshot.pushingSpeed * sqRatio
    private val growthSpeed = snapshot.growthSpeed * sqRatio
    private val lifeSpan = snapshot.lifeSpan
    override val damage = snapshot.damage / (lifeSpan*60)

    var radius = 0f
    private var life = 0f

    fun applyEffect() {
        active = true
    }

    override fun update() {
        life += Gdx.graphics.deltaTime
        if (life < lifeSpan) radius += growthSpeed * Gdx.graphics.deltaTime
        else kill()

        //Log.info("Radius: $radius - Life: $life - Damage: $damage")
        damageAll()
    }

    private fun damageAll() {
        for (attacker in attackers){
            giveDamage(attacker)
            attacker.pushAway(pushingSpeed)
        }
    }
}
