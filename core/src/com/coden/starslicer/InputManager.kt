package com.coden.starslicer

import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.EntityData

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

}