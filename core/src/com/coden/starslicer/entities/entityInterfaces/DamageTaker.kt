package com.coden.starslicer.entities.entityInterfaces

interface DamageTaker: Mortal {
    val maxHealth: Float
    var health: Float

    fun takeDamage(damage: Float){
        health -= damage
        if (health <= 0) kill()
        health  = if (health > maxHealth) maxHealth else health
    }

    fun takeDamage(entity: DamageGiver){
        takeDamage(entity.damage)
    }

    fun heal(amount: Float){
        takeDamage(-amount)
    }

}