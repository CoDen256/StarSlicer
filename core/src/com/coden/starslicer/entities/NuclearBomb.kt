package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.util.center
import com.coden.starslicer.util.sqRatio
import com.coden.starslicer.util.yRatio

class NuclerBomb(override val initialPos: Vector2,
                 override val state: Int): Attacker("nuclearbomb.png"){
    // Life
    override val lifeSpan = 100f

    // Speed constants
    override val movementSpeed = 5 * sqRatio

    // Movement
    override var pos: Vector2 = initialPos
    private var velocity: Vector2

    // Sprite
    private val w = spriteTexture.width
    private val h = spriteTexture.height

    override var hitBox = Rectangle(0f,0f,0f,0f)
        get() = Rectangle(pos.x - w * yRatio/2, pos.y - h * yRatio/2, w * yRatio, h * yRatio)

    init {
        velocity = when (state) {
            0 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                    MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(movementSpeed)
            1 -> initialPos.cpy().sub(center).scl(-1f).setLength(movementSpeed)
            else -> Vector2()
        }

        Gdx.app.log("nuclearBomb.init", "Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state - ${state}")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(velocity.angle()+90)
    }

    override fun update() {
        updateLife()
        pos = pos.add(velocity)
        sprite.setScale(yRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)
    }

}