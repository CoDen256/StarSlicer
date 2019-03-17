package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.util.spaceCraftCenter
import com.coden.starslicer.util.sqRatio

abstract class Attacker(snapshot: AttackerSnapshot): Entity {

    // Life
    val lifeSpan = snapshot.lifeSpan
    val name = snapshot.name
    val type = snapshot.type

    override val maxHealth = snapshot.maxHealth
    override val damage = snapshot.damage

    private var life = 0f
    override var isDead = false

    // Movement
    val movementSpeed = snapshot.movementSpeed * sqRatio
    abstract val initialPos: Vector2


    // Sprite
    val collisional: Boolean = snapshot.collisional
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