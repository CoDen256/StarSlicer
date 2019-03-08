package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.SpaceCraft

class HPBoost: PowerUp("hpboost") {
    override val damage = -100f // healing amount

    override val continuous = false
    override var hitBox: Rectangle = Rectangle(0f, 0f,0f,0f)
        get() = Rectangle(0f, 0f,0f,0f)

    override var pos: Vector2 = Vector2(0f, 0f)


    fun applyEffect(spaceCraft: SpaceCraft) {
        spaceCraft.takeDamage(damage)
        kill()
    }

    fun update() {

    }


}