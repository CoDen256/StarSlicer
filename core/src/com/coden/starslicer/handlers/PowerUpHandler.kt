package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.entities.powerups.*
import com.coden.starslicer.entities.powerups.PowerUp.Companion.powerUps

class PowerUpHandler(private val spaceCraft: SpaceCraft) {

    val boosts = ArrayList<HPBoost>()
    val shields = ArrayList<Shield>()
    val shockWaves = ArrayList<ShockWave>()

    init {
        for (i in 0 until 5) {
            add(HPBOOST)
            add(SHIELD)
            add(SHOCKWAVE)
        }
    }

    fun updateAll() {
        update(HPBOOST)
        update(SHIELD)
        update(SHOCKWAVE)
    }

    fun renderAll(batch: SpriteBatch) {

    }

    fun updateInput() {
        when  {
            Gdx.input.isKeyJustPressed(Input.Keys.H) -> add(HPBOOST)
            Gdx.input.isKeyJustPressed(Input.Keys.J) -> use(HPBOOST)

            Gdx.input.isKeyJustPressed(Input.Keys.S) -> add(SHIELD)
            Gdx.input.isKeyJustPressed(Input.Keys.D) -> use(SHIELD)

            Gdx.input.isKeyJustPressed(Input.Keys.W) -> add(SHOCKWAVE)
            Gdx.input.isKeyJustPressed(Input.Keys.E) -> use(SHOCKWAVE)
        }

    }

    private fun add(ability: PowerUp.PowerUpType) = when (ability) {
        SHIELD -> shields.add(Shield(spaceCraft))
        HPBOOST -> boosts.add(HPBoost())
        SHOCKWAVE -> shockWaves.add(ShockWave())
    }

    fun use(ability: PowerUp.PowerUpType) = when (ability) {
        SHIELD -> if (!shields.isEmpty() && !spaceCraft.isShielded) shields[0].applyEffect() else Unit
        HPBOOST -> if (!boosts.isEmpty()) boosts[0].applyEffect(spaceCraft) else Unit
        SHOCKWAVE -> {
            if (!shockWaves.isEmpty()) {
                for (shockWave in shockWaves) if (!shockWave.active) {
                    shockWave.applyEffect()
                    break
                }
            }
            else Unit
        }


    }

    private fun update(ability: PowerUp.PowerUpType) {
        val iterator = when (ability) {
            SHOCKWAVE -> shockWaves.iterator()
            SHIELD -> shields.iterator()
            HPBOOST -> boosts.iterator()
        }

        while (iterator.hasNext()) {
            val powerUp = iterator.next()
            if (powerUp.active) {
                powerUp.update()
                if (powerUp.isDead) {
                    iterator.remove()
                    powerUps.remove(powerUp)
                    Gdx.app.log("powerupUpdate", "${powerUp.type} is dead")
                }
            }
        }
    }

    fun getPowerUps() : Map<PowerUp.PowerUpType, Int>{
        return mapOf(HPBOOST to boosts.size,
                     SHIELD to shields.size,
                     SHOCKWAVE to shockWaves.size)
    }

}