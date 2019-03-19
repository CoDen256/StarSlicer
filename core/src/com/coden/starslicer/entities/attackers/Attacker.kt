package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.*

abstract class Attacker(val snapshot: AttackerSnapshot,val state: Int = 0, assets: Assets.AttackerAssets): Entity {

    init {
        entities.add(this)
    }

    // Snapshot properties
    val name = snapshot.name
    val type = snapshot.type
    val container = snapshot.isContainer()

    // If special property is null, so undefined, then look in map for every state
    val lifeSpan = snapshot.getLifeSpan(state)
    val maxMovementSpeed = snapshot.getMaxMovementSpeed(state) * sqRatio
    val collisional = snapshot.getCollisional(state)

    final override val maxHealth = snapshot.getMaxHealth(state)
    override val damage = snapshot.getDamage(state)

    // Life
    private var life = 0f
    override var isDead = false
    override var health = maxHealth

    // Movement
    abstract val initialPos: Vector2
    abstract var velocity: Vector2

    // Sprite
    private val spriteTexture: TextureRegion? = assets.getTexture(type)
    protected val sprite = Sprite(spriteTexture)

    protected val width = spriteTexture!!.regionWidth * xRatio
    protected val height = spriteTexture!!.regionHeight * yRatio

    open val content: PowerUp.PowerUpType? = null

    // specialized vectors
    var targetVector : Vector2
        get() = pos.cpy().sub(spaceCraftCenter).scl(-1f)
        set(value) {}

    var perpendicularVector : Vector2
        get() = targetVector.cpy().rotate90(-1)
        set(value) {}


    open fun render(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    open fun updateLife() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan) kill()


    }

    fun rotate(angleSpeed: Float) {
        sprite.rotate(angleSpeed*Gdx.graphics.deltaTime)
    }

    abstract fun update()

    fun applyVelocity(vel: Vector2) {
        velocity.add(vel)
    }

}