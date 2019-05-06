package com.coden.starslicer.gameObjects.powerups

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.gameObjects.Mortal
import com.coden.starslicer.util.Locator
import com.coden.starslicer.util.Log
import com.coden.starslicer.assets.loaders.PowerUpLoader
import com.coden.starslicer.util.spaceCraftX
import com.coden.starslicer.util.spaceCraftY
import com.coden.starslicer.gameObjects.powerups.PowerUpType.*

abstract class PowerUp(val type: PowerUpType): Mortal {

    override var isDead = false
    var active = false

    abstract fun update()

    companion object {
        private val shields = ArrayList<Shield>()
        private val hpboosts = ArrayList<HPBoost>()
        private val shockwaves = ArrayList<ShockWave>()
        val loader = PowerUpLoader()

        // updating
        fun updateAll(){
            listOf(SHIELD, SHOCKWAVE, HPBOOST).forEach { update(it)}
        }

        fun update(ability: PowerUpType){
            val iterator = when (ability) {
                SHOCKWAVE -> shockwaves.iterator()
                SHIELD -> shields.iterator()
                HPBOOST -> hpboosts.iterator()
                RANDOM -> throw IllegalArgumentException()
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

        // creating
        fun create(ability: PowerUpType) = when(ability){
            SHIELD -> shields.add(Shield())
            HPBOOST -> hpboosts.add(HPBoost())
            SHOCKWAVE -> shockwaves.add(ShockWave())
            RANDOM ->  createRandom()
        }

        private fun createRandom(): Boolean{
            create(PowerUpType.values()[MathUtils.random(0,2)])
            return true
        }

        //using
        fun isUsable(ability: PowerUpType) = when(ability){
            SHIELD    -> !shields.isEmpty() && !Locator.spaceCraft.isShielded
            HPBOOST   -> !hpboosts.isEmpty()
            SHOCKWAVE -> !shockwaves.isEmpty()
            RANDOM    -> false
        }

        fun use(ability: PowerUpType) = when(ability){
            SHIELD -> shields[0].applyEffect()
            HPBOOST -> hpboosts[0].applyEffect()
            SHOCKWAVE -> shockwaves.find {!it.active}?.applyEffect()
            RANDOM -> throw IllegalArgumentException()
        }

        fun isEmpty(ability: PowerUpType) = when(ability){
            SHIELD -> shields.isEmpty()
            HPBOOST -> hpboosts.isEmpty()
            SHOCKWAVE -> shockwaves.isEmpty()
            RANDOM -> throw IllegalArgumentException()
        }

        // DEBUG
        fun debugShapes(shapeRenderer: ShapeRenderer){
            for (shockwave in shockwaves) {
                if (shockwave.active){
                    shapeRenderer.circle(spaceCraftX, spaceCraftY, shockwave.radius)
                }

            }
            for (shield in shields){
                if (shield.active){
                    shapeRenderer.circle(spaceCraftX, spaceCraftY, Locator.spaceCraft.shieldRadius)
                    break
                }
            }
        }
    }


}