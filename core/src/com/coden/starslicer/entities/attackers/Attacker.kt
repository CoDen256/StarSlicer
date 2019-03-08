package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.util.spaceCraftCenter
import com.coden.starslicer.util.textureMap

abstract class Attacker(val name: String) : Entity {

    // Life
    abstract val lifeSpan : Float

    abstract override var health: Float
    abstract override var damage: Float

    private var life = 0f
    var isDead: Boolean = false

    // Movement
    abstract val movementSpeed: Float
    abstract val initialPos: Vector2
    abstract var pos: Vector2


    // Sprite
    abstract val collisional: Boolean
    abstract val state: Int
    abstract val hitBox: Rectangle


    val spriteTexture = textureMap[name]
    val sprite = Sprite(spriteTexture)

    // specialized vectors
    var targetVector = Vector2()
        get() = pos.cpy().sub(spaceCraftCenter).scl(-1f)

    var perpendicularVector = Vector2()
        get() = targetVector.cpy().rotate90(-1)


    abstract fun update()

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

    override fun giveDamage(entity: Entity, value: Float) {

    }

    override fun giveDamage(entities: ArrayList<Entity>, value: Float) {

    }

    override fun takeDamage(value: Float) {
        health -= value
        if (health <= 0) kill()
    }

}