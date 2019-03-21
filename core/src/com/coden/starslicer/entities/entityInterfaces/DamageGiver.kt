package com.coden.starslicer.entities.entityInterfaces

interface DamageGiver {
    val damage:Float

    fun giveDamage(entity: DamageTaker){
        entity.takeDamage(damage)
    }

    fun damageAll(entities: ArrayList<DamageTaker>){
        for (entity in entities){
            giveDamage(entity)
        }
    }
}