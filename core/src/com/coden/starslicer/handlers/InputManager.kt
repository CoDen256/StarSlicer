package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.entityInterfaces.Entity.Companion.entities
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.SpaceCraft.SpaceCraft
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.NuclearBomb
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave

class InputManager(private val data: EntityData) {

    private val log = Logger("InputManager", Logger.DEBUG)

    init {
        for (i in 0 until 5) {
            addPowerUp(HPBOOST)
            addPowerUp(SHIELD)
            addPowerUp(SHOCKWAVE)
        }
    }

    fun updateSwiping(){
        with(SpaceCraft){
            if (!(firstBlade.active || secondBlade.active)){
                return
            }
            for (entity in entities) {
                if (firstBlade.isSlicing(entity.hitBox) || secondBlade.isSlicing(entity.hitBox)) {
                    entity.takeDamage(damage) // TODO: The bigger slice the more damage will received
                    if (entity.isDead && entity is Attacker) {
                        if (entity.container) addPowerUp(entity.content!!)
                        if (entity is NuclearBomb) entity.damageAll()
                    }
                }
            }
        }
        }

    fun updateClicking(){
        if (Gdx.input.justTouched()){
            for (icon in data.powerUpIcons) {
                if (icon.hitBox.contains(Gdx.input.x * 1f, Gdx.graphics.height - Gdx.input.y * 1f)) {
                    usePowerUp(icon.type)
                }
            }
        }
    }

    private fun usePowerUp(ability: PowerUp.PowerUpType) = when (ability) {
        SHIELD    -> if (!data.shields.isEmpty() && !SpaceCraft.isShielded) data.shields[0].applyEffect() else Unit
        HPBOOST   -> if (!data.boosts.isEmpty()) data.boosts[0].applyEffect() else Unit
        SHOCKWAVE -> {
            if (!data.shockWaves.isEmpty()) {
                for (shockWave in data.shockWaves) if (!shockWave.active) {
                    shockWave.applyEffect()
                    break
                }
            }
            else Unit
        }
    }

    private fun addPowerUp(ability: PowerUp.PowerUpType) = when (ability) {
        SHIELD     -> data.shields.add(Shield())
        HPBOOST    -> data.boosts.add(HPBoost())
        SHOCKWAVE  -> data.shockWaves.add(ShockWave())
    }

}