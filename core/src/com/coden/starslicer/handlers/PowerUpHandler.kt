package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.*

class PowerUpHandler(private val data: EntityData) {

    private val log = Logger("PowerUpHandler", Logger.NONE)

    fun updateAll() {
        listOf(SHIELD, SHOCKWAVE, HPBOOST).forEach {update(it)}
    }

    private fun update(ability: PowerUp.PowerUpType) {
        val iterator = when (ability) {
            SHOCKWAVE  -> data.shockWaves.iterator()
            SHIELD     -> data.shields.iterator()
            HPBOOST    -> data.boosts.iterator()
        }

        while (iterator.hasNext()) {
            val powerUp = iterator.next()
            if (powerUp.active) {
                powerUp.update()
                if (powerUp.isDead) {
                    iterator.remove()
                    log.info("${powerUp.type} is dead")
                }
            }
        }
    }

}