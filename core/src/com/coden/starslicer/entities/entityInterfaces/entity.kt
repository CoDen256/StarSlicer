package com.coden.starslicer.entities.entityInterfaces

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.coden.starslicer.entities.powerups.PowerUp
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

interface Mortal {

    var isDead: Boolean

    fun kill(){
        isDead = true
    }
}

interface Collisional {
    val hitBox: Rectangle
    val hitSphere: Circle
}

interface Container {
    val content: PowerUp.PowerUpType
}