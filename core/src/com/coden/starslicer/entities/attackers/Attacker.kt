package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.entityInterfaces.Collisional
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.entities.entityInterfaces.DamageTaker
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.*

abstract class Attacker(val snapshot: AttackerSnapshot,val state: Int = 0, assets: Assets.AttackerAssets): DamageGiver, DamageTaker{

    companion object {
        val attackers = ArrayList<Attacker>()
    }


    init {
        attackers.add(this)
    }

    // Snapshot properties
    val name = snapshot.name
    val type = snapshot.type

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
    abstract var pos: Vector2

    // Sprite
    private val spriteTexture: TextureRegion? = assets.getTexture(type)
    protected val sprite = Sprite(spriteTexture)

    protected val width = spriteTexture!!.regionWidth * xRatio
    protected val height = spriteTexture!!.regionHeight * yRatio

    // specialized vectors
    protected var targetVector : Vector2
        get() = pos.cpy().sub(spaceCraftCenter).scl(-1f)
        set(value) {}

    protected var perpendicularVector : Vector2
        get() = targetVector.cpy().rotate90(-1)
        set(value) {}


    open fun render(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    protected fun updateLife() {
        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan) kill()
    }

    protected fun rotate(angleSpeed: Float) {
        sprite.rotate(angleSpeed*Gdx.graphics.deltaTime)
    }

    abstract fun update()

    open fun onDestroy() {
        kill()
    }

    protected fun applyVelocity(vel: Vector2) {
        velocity.add(vel)
    }

    fun pushAway(pushingSpeed: Float){
        applyVelocity(targetVector.cpy().scl(-1f).setLength(pushingSpeed*Gdx.graphics.deltaTime))
    }

}