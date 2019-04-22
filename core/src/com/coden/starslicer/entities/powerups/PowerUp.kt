package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.spaceCraftX
import com.coden.starslicer.util.spaceCraftY

abstract class PowerUp(val type: PowerUpType): Mortal{

    enum class PowerUpType {
        SHIELD, HPBOOST, SHOCKWAVE, RANDOM;

    }


    companion object {
        private val shields = ArrayList<Shield>()
        private val hpboosts = ArrayList<HPBoost>()
        private val shockwaves = ArrayList<ShockWave>()

        fun updateAll(){
            listOf(PowerUp.PowerUpType.SHIELD, PowerUp.PowerUpType.SHOCKWAVE, PowerUp.PowerUpType.HPBOOST).forEach { update(it)}
        }

        fun update(ability: PowerUpType){
            val iterator = when (ability) {
                PowerUpType.SHOCKWAVE -> shockwaves.iterator()
                PowerUpType.SHIELD -> shields.iterator()
                PowerUpType.HPBOOST -> hpboosts.iterator()
                PowerUpType.RANDOM -> throw IllegalArgumentException()
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

        fun convert(id: Int) = PowerUpType.values()[id]
        fun convert(powerUpType: PowerUpType) = when (powerUpType){
            PowerUpType.SHIELD -> 0
            PowerUpType.HPBOOST -> 1
            PowerUpType.SHOCKWAVE -> 2
            PowerUpType.RANDOM -> 3
        }

        fun create(ability: PowerUp.PowerUpType) = when(ability){
            PowerUpType.SHIELD -> shields.add(Shield())
            PowerUpType.HPBOOST -> hpboosts.add(HPBoost())
            PowerUpType.SHOCKWAVE -> shockwaves.add(ShockWave())
            PowerUpType.RANDOM ->  createRandom()

        }

        fun use(ability: PowerUpType) = when(ability){
            PowerUpType.SHIELD -> if (!shields.isEmpty() && !SpaceCraft.isShielded) shields[0].applyEffect() else Unit
            PowerUpType.HPBOOST -> if (!hpboosts.isEmpty()) hpboosts[0].applyEffect() else Unit
            PowerUpType.SHOCKWAVE -> {
                if (!shockwaves.isEmpty()) {
                    for (shockWave in shockwaves) if (!shockWave.active) {
                        shockWave.applyEffect()
                        break
                    }
                }
                else Unit
            }
            PowerUpType.RANDOM -> throw IllegalArgumentException()
        }

        private fun createRandom(): Boolean{
            create(PowerUpType.values()[MathUtils.random(0,2)])
            return true
        }

        fun debugShapes(shapeRenderer: ShapeRenderer){
            for (shockwave in shockwaves) {
                if (shockwave.active){
                    shapeRenderer.circle(spaceCraftX, spaceCraftY, shockwave.radius)
                }

            }
            for (shield in shields){
                if (shield.active){
                    shapeRenderer.circle(spaceCraftX, spaceCraftY, SpaceCraft.shieldRadius)
                    break
                }
            }
        }
    }

    override var isDead = false
    var active = false

    abstract fun update()


}