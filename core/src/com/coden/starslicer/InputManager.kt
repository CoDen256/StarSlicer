package com.coden.starslicer

import com.badlogic.gdx.Gdx
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave
import com.coden.starslicer.hud.PowerUpIcon

class InputManager(private val data: EntityData) {

    init {
        for (i in 0 until 5) {
            addPowerUp(HPBOOST)
            addPowerUp(SHIELD)
            addPowerUp(SHOCKWAVE)
        }
    }

    fun updateSwiping(){
        with(data.spaceCraft){
            if (!(firstBlade.active || secondBlade.active)){
                return
            }
            for (entity in entities) {
                if (firstBlade.isSlicing(entity.hitBox) || secondBlade.isSlicing(entity.hitBox)) {
                    entity.takeDamage(damage) // TODO: The bigger slice the more damage will received
                    //TODO: if entity is container add(random PowerUpType)
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
        SHIELD -> if (!data.shields.isEmpty() && !data.spaceCraft.isShielded) data.shields[0].applyEffect() else Unit
        HPBOOST -> if (!data.boosts.isEmpty()) data.boosts[0].applyEffect(data.spaceCraft) else Unit
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
       SHIELD -> data.shields.add(Shield(data.spaceCraft))
       HPBOOST -> data.boosts.add(HPBoost())
       SHOCKWAVE -> data.shockWaves.add(ShockWave())
    }

}