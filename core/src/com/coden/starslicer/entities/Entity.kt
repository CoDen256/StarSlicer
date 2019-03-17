package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2


interface Entity {
    companion object {
        val entities = ArrayList<Entity>()
    }
    val maxHealth: Float
    var health: Float
    val damage: Float

    var isDead: Boolean

    var hitBox: Rectangle
    var hitCircle: Circle
    var pos: Vector2


    fun kill(){
        isDead = true
    }

    fun giveDamage(entity: Entity, value: Float) {
        entity.takeDamage(value)
    }

     fun takeDamage(value: Float) {
        health -= value
        if (health <= 0) kill()
         else if (health > maxHealth) health = maxHealth
    }

}