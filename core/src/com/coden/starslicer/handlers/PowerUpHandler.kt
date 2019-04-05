package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.*
import com.coden.starslicer.util.Log

class PowerUpHandler(private val data: EntityData) {

    fun updateAll() {
        listOf(SHIELD, SHOCKWAVE, HPBOOST).forEach {update(it)}
    }

    private fun update(ability: PowerUp.PowerUpType) {
        val iterator = when (ability) {
            SHOCKWAVE  -> data.shockWaves.iterator()
            SHIELD     -> data.shields.iterator()
            HPBOOST    -> data.boosts.iterator()
            RANDOM     -> throw IllegalArgumentException()
        }

        while (iterator.hasNext()) {
            val powerUp = iterator.next()
            if (powerUp.active) {
                powerUp.update()
                if (powerUp.isDead) {
                    iterator.remove()
                    Log.info("${powerUp.type} is dead", Log.LogType.POWERUP)
                }
            }
        }
    }

}