package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.util.center
import com.coden.starslicer.util.sqRatio

class NuclerBomb(override val initialPos: Vector2,
                 override val state: Int): Attacker("nuclearbomb.png"){
    // Life
    override val lifeSpan = 100f

    // Speed constants
    override val movementSpeed = 10 * sqRatio

    // Movement
    override var pos: Vector2 = initialPos
    private var velocity: Vector2

    // Sprite
    override val hitBox = Rectangle()

    init {
        velocity = when (state) {
            0 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                    MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(movementSpeed)
            1 -> initialPos.cpy().sub(center).scl(-1f).setLength(movementSpeed)
            else -> Vector2()
        }

        Gdx.app.log("missile.init", "Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state - ${states[state]}")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(if (state == 2) 180f else velocity.angle())
    }

}