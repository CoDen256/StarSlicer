package com.coden.starslicer.entities.entityInterfaces

import com.coden.starslicer.util.Log


interface DamageGiver {
    val damage:Float

    fun giveDamage(entity: DamageTaker, amount: Float = damage){
        Log.info("Giving damage to $entity with damage: $amount")
        entity.takeDamage(amount)
    }

    fun damageAll(entities: ArrayList<DamageTaker>){
        for (entity in entities){
            giveDamage(entity)
        }
    }

    fun heal(entity: DamageTaker, amount: Float=damage){
        Log.info("Giving healing to $entity with heal: $amount")
        entity.heal(amount)
    }
}