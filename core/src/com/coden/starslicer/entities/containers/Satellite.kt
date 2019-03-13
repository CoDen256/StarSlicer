package com.coden.starslicer.entities.containers

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.*

class Satellite(
        override val initialPos: Vector2,
        override val state: Int,
        override val content: PowerUp.PowerUpType,
        assets: Assets.ContainerAssets) : Container, Attacker(){

    // TODO: Content of container decided on conditions of current situation

    private val log = Logger("Satellite", Logger.NONE)

    // Container properties
    // Life

    override val name = AttackerType.SATELLITE

    override val lifeSpan = 60f
    override val maxHealth = 500f
    override var health = maxHealth
    override var damage = 0f

    // Speed constants
    override val movementSpeed = 0.75f * sqRatio

    // Movement
    override var pos: Vector2 = initialPos
    val angleSpeed = MathUtils.random(0.05f, 0.5f)

    var velocity: Vector2

    // SPRITE

    override val spriteTexture = assets.getTexture(name)
    override val sprite = Sprite(spriteTexture)

    private val w = spriteTexture!!.regionWidth * xRatio
    private val h = spriteTexture!!.regionHeight * yRatio

    override val collisional: Boolean = true

    override var hitBox : Rectangle
        get() = Rectangle(pos.x - w/2, pos.y - h/2, w , h )
        set(value) {}

    override var roundHitBox: Circle
        get() = Circle(pos.x, pos.y, minOf(w, h)/2)
        set(value) {}

    init {
        velocity = when (state) {
            0 -> targetVector.rotate(MathUtils.random(15, 45)* MathUtils.randomSign().toFloat()).setLength(movementSpeed)
            1 -> initialPos.cpy().sub(spaceCraftCenter).scl(-1f).setLength(movementSpeed)
            else -> Vector2()
        }

        log.info("Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(velocity.angle())
    }

    override fun update() {
        updateLife()
        pos = pos.add(velocity)
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

        sprite.rotate(angleSpeed)
    }


}