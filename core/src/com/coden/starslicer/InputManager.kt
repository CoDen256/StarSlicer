package com.coden.starslicer

import com.badlogic.gdx.Gdx
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave
import com.coden.starslicer.hud.PowerUpIcon

class InputManager(val data: EntityData) {

    init {
        for (i in 0 until 5) {
            addPowerUp(PowerUp.PowerUpType.HPBOOST)
            addPowerUp(PowerUp.PowerUpType.SHIELD)
            addPowerUp(PowerUp.PowerUpType.SHOCKWAVE)
        }
    }

    fun updateSwiping(){
        with(data.spaceCraft){
            if (!(firstBlade.active || secondBlade.active)){
                return
            }
            for (entity in entities) {
                if (firstBlade.isSlicing(entity.hitBox) || secondBlade.isSlicing(entity.hitBox)) {
                    entity.takeDamage(damage)
                    //TODO: if entity is container add(random PowerUpType)
                }
            }
        }
        }

    fun updateClicking(){
        if (Gdx.input.justTouched()){
            for (icon in data.hud.powerUpsBar.icons.values) {
                if (icon.hitBox.contains(Gdx.input.x * 1f, Gdx.graphics.height- Gdx.input.y * 1f)) {
                    usePowerUp(icon.type)
                }
            }
        }
    }

    private fun usePowerUp(ability: PowerUp.PowerUpType) = when (ability) {
        PowerUp.PowerUpType.SHIELD -> if (!data.shields.isEmpty() && !data.spaceCraft.isShielded) data.shields[0].applyEffect() else Unit
        PowerUp.PowerUpType.HPBOOST -> if (!data.boosts.isEmpty()) data.boosts[0].applyEffect(data.spaceCraft) else Unit
        PowerUp.PowerUpType.SHOCKWAVE -> {
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
        PowerUp.PowerUpType.SHIELD -> data.shields.add(Shield(data.spaceCraft))
        PowerUp.PowerUpType.HPBOOST -> data.boosts.add(HPBoost())
        PowerUp.PowerUpType.SHOCKWAVE -> data.shockWaves.add(ShockWave())
    }

}