package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.util.center

abstract class Attacker(path: String) {

    // Movement
    abstract val movementSpeed: Float
    abstract val initialPos: Vector2
    abstract var pos: Vector2

    // Sprite
    abstract val state: Int
    abstract val hitBox: Rectangle

    val spriteTexture = Texture(path)
    val sprite = Sprite(spriteTexture)

    // Life
    abstract val lifeSpan : Float

    private var life = 0f
    var isDead: Boolean = false

    // specialized vectors
    var targetVector = Vector2()
        get() = pos.cpy().sub(center).scl(-1f)

    var perpendicularVector = Vector2()
        get() = targetVector.cpy().rotate90(-1)


    open fun render(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    open fun updateLife() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan) {
            kill()
        }

    }

    open fun kill() {
        isDead = true
    }

}