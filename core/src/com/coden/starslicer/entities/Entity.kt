package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx

interface Entity {
    val maxHealth: Float
    var health: Float
    val damage: Float

    var isDead: Boolean

    fun kill(){
        isDead = true
    }

    fun giveDamage(entity: Entity, value: Float) {
        entity.takeDamage(value)
    }

    fun giveDamage(entities: ArrayList<Entity>, value: Float) {
        for (entity in entities) {
            if (entity != this){
                entity.takeDamage(value)
            }

        }
    }

     fun takeDamage(value: Float) {
        health -= value
        if (health <= 0) kill()
         else if (health > maxHealth) health = maxHealth
    }

}