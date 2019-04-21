package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.entities.spacecraft.SpaceCraft

abstract class PowerUp(val type: PowerUpType): Mortal{

    enum class PowerUpType {
        SHIELD, HPBOOST, SHOCKWAVE, RANDOM;

    }


    companion object {
        val shields = ArrayList<Shield>()
        val hpboosts = ArrayList<HPBoost>()
        val shockwaves = ArrayList<ShockWave>()

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
    }

    override var isDead = false
    var active = false

    abstract fun update()


}