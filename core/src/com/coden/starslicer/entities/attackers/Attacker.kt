package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.util.spaceCraftCenter

abstract class Attacker : Entity {

    enum class AttackerType {
        SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR, MISSILE, NUCLEAR_BOMB,
        SATELLITE, POWERUP_CONTAINER
    }

    // Life
    abstract val lifeSpan : Float
    abstract val name: AttackerType

    private var life = 0f
    override var isDead = false

    // Movement
    abstract val movementSpeed: Float
    abstract val initialPos: Vector2


    // Sprite
    abstract val collisional: Boolean
    abstract val state: Int

    abstract var roundHitBox: Circle


    abstract val spriteTexture: TextureRegion?
    abstract val sprite: Sprite

    // specialized vectors
    var targetVector : Vector2
        get() = pos.cpy().sub(spaceCraftCenter).scl(-1f)
        set(value) {}

    var perpendicularVector : Vector2
        get() = targetVector.cpy().rotate90(-1)
        set(value) {}


    abstract fun update()

    init {
        entities.add(this)
    }

    open fun render(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    open fun updateLife() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan) {
            kill()
        }

    }

}