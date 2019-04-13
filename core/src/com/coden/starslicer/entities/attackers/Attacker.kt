package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.entityInterfaces.Collisional
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.entities.entityInterfaces.DamageTaker
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.hud.HealthBar
import com.coden.starslicer.util.*

abstract class Attacker(val snapshot: AttackerSnapshot,val state: Int = 0, assets: Assets.AttackerAssets): DamageGiver, DamageTaker{

    companion object {
        val attackers = ArrayList<Attacker>()
    }

    // Snapshot properties
    val name = snapshot.name
    val type = snapshot.type

    // If special property is null, so undefined, then look in map for every state
    val lifeSpan = snapshot.getLifeSpan(state)
    val maxMovementSpeed = snapshot.getMaxMovementSpeed(state) * sqRatio
    val collisional = snapshot.getCollisional(state)
    val reward = snapshot.reward

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

    override val width = spriteTexture!!.regionWidth * xRatio
    override val height = spriteTexture!!.regionHeight * yRatio

    var healthBar : HealthBar? = null

    override fun toString() = name

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
        updateHitBoxes()

        life += Gdx.graphics.deltaTime
        if (life >= lifeSpan) kill()
        if(dist2(pos, center) > dist2(centerX, centerY) + 15 && life > 15)
            kill()



        healthBar = if (healthBar == null) HealthBar(this) else healthBar
        healthBar!!.update()
    }

    protected fun rotate(angleSpeed: Float) {
        sprite.rotate(angleSpeed*Gdx.graphics.deltaTime)
    }

    private fun updateHitBoxes(){
        hitBox.setPosition(pos.x - hitBox.width/2, pos.y - hitBox.height/2)
        hitSphere.setPosition(pos.x, pos.y)
    }


    abstract fun update()

    fun updateCollision() {
        if (SpaceCraft.isShielded) {
            if (SpaceCraft.shieldCircle.overlaps(hitSphere)) {
                kill()
                onDestroy()
            }
        } else if (SpaceCraft.hitBox.overlaps(hitBox) && collisional) {
            giveDamage(SpaceCraft)
            SpaceCraft.giveDamage(this) // Body damage
        }
    }

    open fun onDestroy() {}

    open fun render(shapeRenderer: ShapeRenderer){
        healthBar!!.render(shapeRenderer)
    }

    protected fun applyVelocity(vel: Vector2) {
        velocity.add(vel)
    }

    fun pushAway(pushingSpeed: Float){
        applyVelocity(targetVector.cpy().scl(-1f).setLength(pushingSpeed*Gdx.graphics.deltaTime))
    }

}