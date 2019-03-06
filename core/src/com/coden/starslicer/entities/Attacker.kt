package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.util.center

abstract class Attacker(path: String) {

    abstract val initialPos: Vector2
    abstract val state: Int

    abstract val movementSpeed: Float
    abstract val lifeSpan : Float
    abstract val hitBox: Rectangle

    abstract var pos: Vector2

    val spriteTexture = Texture(path)
    val sprite = Sprite(spriteTexture)

    // Lifespan and current life of a missile
    private var life = 0f
    var isDead: Boolean = false

    // Vector Section
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