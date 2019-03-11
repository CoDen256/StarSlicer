package com.coden.starslicer

import com.badlogic.gdx.Gdx
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.hud.PowerUpIcon

class InputManager(val data: EntityData) {

    fun updateSwiping(){
        with(data.spaceCraft){
            if (!(firstBlade.active || secondBlade.active)){
                return
            }
            for (entity in entities) {
                if (firstBlade.isSlicing(entity.hitBox) || secondBlade.isSlicing(entity.hitBox)) {
                    entity.takeDamage(damage)
                }
            }
        }
        }

    fun updateClicking(){
        if (Gdx.input.justTouched()){
            for (icon in data.hud.powerUpsBar.icons.values) {
                if (icon.hitBox.contains(Gdx.input.x * 1f, Gdx.graphics.height- Gdx.input.y * 1f)) {
                    data.powerupHandler.use(icon.type)
                }
            }
        }
    }

}