package com.coden.starslicer.handlers

import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.*
import com.coden.starslicer.entities.powerups.PowerUp.Companion.hpboosts
import com.coden.starslicer.entities.powerups.PowerUp.Companion.shields
import com.coden.starslicer.entities.powerups.PowerUp.Companion.shockwaves
import com.coden.starslicer.util.Log

class PowerUpHandler {

    fun updateAll() {
        listOf(SHIELD, SHOCKWAVE, HPBOOST).forEach {update(it)}
    }

    private fun update(ability: PowerUp.PowerUpType) {
        val iterator = when (ability) {
            SHOCKWAVE  -> shockwaves.iterator()
            SHIELD     -> shields.iterator()
            HPBOOST    -> hpboosts.iterator()
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