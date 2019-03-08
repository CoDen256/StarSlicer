package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.SpaceCraft

class Shield(val spaceCraft: SpaceCraft): PowerUp("shield") {
    override val damage = 0f
    override val continuous = true

    override var hitBox: Rectangle = Rectangle(0f, 0f,0f,0f)
        get() = Rectangle(0f, 0f,0f,0f)

    override var pos: Vector2 = Vector2(0f, 0f)

    val maxRadius = 120f
    var radius = 0f

    val lifeSpan = 10f
    var life = 0f

    var active = false

    fun applyEffect() {
        spaceCraft.isShielded = true
        active = true
        spaceCraft.shieldRadius = radius


    }

    fun update() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan) kill()

        if (radius < maxRadius) radius += maxRadius/30
        spaceCraft.shieldRadius = radius


    }

    override fun kill() {
        super.kill()
        spaceCraft.isShielded = false
    }


}