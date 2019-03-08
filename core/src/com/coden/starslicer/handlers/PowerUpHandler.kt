package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.entities.powerups.*

class PowerUpHandler(val spaceCraft: SpaceCraft) {

    val boosts = ArrayList<HPBoost>()
    val shields = ArrayList<Shield>()
    val shockWaves = ArrayList<ShockWave>()


    fun updateAll() {
        update(PowerUpType.HPBOOST)
        update(PowerUpType.SHIELD)
        update(PowerUpType.SHOCKWAVE)
    }

    fun renderAll(batch: SpriteBatch) {

    }

    fun updateInput() {
        when  {
            Gdx.input.isKeyJustPressed(Input.Keys.H) -> add(PowerUpType.HPBOOST)
            Gdx.input.isKeyJustPressed(Input.Keys.J) -> use(PowerUpType.HPBOOST)

            Gdx.input.isKeyJustPressed(Input.Keys.S) -> add(PowerUpType.SHIELD)
            Gdx.input.isKeyJustPressed(Input.Keys.D) -> use(PowerUpType.SHIELD)

            Gdx.input.isKeyJustPressed(Input.Keys.W) -> add(PowerUpType.SHOCKWAVE)
            Gdx.input.isKeyJustPressed(Input.Keys.E) -> use(PowerUpType.SHOCKWAVE)
        }

    }

    private fun add(ability: PowerUpType) = when (ability) {
        PowerUpType.SHIELD -> shields.add(Shield(spaceCraft))
        PowerUpType.HPBOOST -> boosts.add(HPBoost())
        PowerUpType.SHOCKWAVE -> shockWaves.add(ShockWave())
    }

    private fun use(ability: PowerUpType) = when (ability) {
        PowerUpType.SHIELD -> if (!shields.isEmpty() && !spaceCraft.isShielded) shields[0].applyEffect() else Unit
        PowerUpType.HPBOOST -> if (!boosts.isEmpty()) boosts[0].applyEffect(spaceCraft) else Unit
        PowerUpType.SHOCKWAVE -> {
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
            PowerUpType.SHOCKWAVE -> shockWaves.iterator()
            PowerUpType.SHIELD -> shields.iterator()
            PowerUpType.HPBOOST -> boosts.iterator()
        }

        while (iterator.hasNext()) {
            val powerup = iterator.next()
            if (powerup.active) {
                powerup.update()
                if (powerup.isDead) {
                    iterator.remove()
                    Gdx.app.log("powerupUpdate", "$powerup is dead")
                }
            }
        }
    }

    fun getPowerUps() : Map<PowerUpType, Int>{
        return mapOf(PowerUpType.HPBOOST to boosts.size,
                    PowerUpType.SHIELD to shields.size,
                    PowerUpType.SHOCKWAVE to shockWaves.size)
    }

}