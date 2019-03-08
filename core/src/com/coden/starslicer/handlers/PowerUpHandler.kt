package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.entities.powerups.PowerUpType.*
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.entities.powerups.*

class PowerUpHandler(private val spaceCraft: SpaceCraft) {

    val boosts = ArrayList<HPBoost>()
    val shields = ArrayList<Shield>()
    val shockWaves = ArrayList<ShockWave>()


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

    private fun add(ability: PowerUpType) = when (ability) {
        SHIELD -> shields.add(Shield(spaceCraft))
        HPBOOST -> boosts.add(HPBoost())
        SHOCKWAVE -> shockWaves.add(ShockWave())
    }

    private fun use(ability: PowerUpType) = when (ability) {
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

    private fun update(ability: PowerUpType) {
        val iterator = when (ability) {
            SHOCKWAVE -> shockWaves.iterator()
            SHIELD -> shields.iterator()
            HPBOOST -> boosts.iterator()
        }

        while (iterator.hasNext()) {
            val powerup = iterator.next()
            if (powerup.active) {
                powerup.update()
                if (powerup.isDead) {
                    iterator.remove()
                    Gdx.app.log("powerupUpdate", "${powerup.name} is dead")
                }
            }
        }
    }

    fun getPowerUps() : Map<PowerUpType, Int>{
        return mapOf(HPBOOST to boosts.size,
                     SHIELD to shields.size,
                     SHOCKWAVE to shockWaves.size)
    }

}