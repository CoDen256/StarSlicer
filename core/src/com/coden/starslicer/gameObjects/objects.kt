package com.coden.starslicer.gameObjects

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.gameObjects.powerups.PowerUp
import com.coden.starslicer.gameObjects.powerups.PowerUpType
import com.coden.starslicer.util.Log

interface DamageGiver {
    val damage:Float

    fun giveDamage(entity: DamageTaker, amount: Float = damage){
        Log.info("Giving damage to $entity with damage: $amount", Log.LogType.GAME)
        entity.takeDamage(amount)
    }

    fun damageAll(entities: ArrayList<DamageTaker>){
        for (entity in entities){
            giveDamage(entity)
        }
    }

    fun heal(entity: DamageTaker, amount: Float = damage){
        Log.info("Giving healing to $entity with heal: $amount", Log.LogType.GAME)
        entity.heal(amount)
    }
}

interface DamageTaker: Mortal, Collisional {
    val maxHealth: Float
    var health: Float

    fun takeDamage(damage: Float){
        if (isDead) return
        health -= damage
        if (health <= 0) {
            health = 0f
            kill()
        }
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

    val width: Float
    val height: Float

    var pos: Vector2

}

interface Container {
    val content: PowerUpType
}