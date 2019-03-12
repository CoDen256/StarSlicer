package com.coden.starslicer.entities.containers

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.*

class PowerUpContainer(
        override val initialPos: Vector2,
        override val state: Int,
        override val content: PowerUp.PowerUpType,
        assets: Assets.ContainerAssets) : Container(){
    // TODO: Content of container decided on conditions of current situation

    private val log = Logger("PowerUpContainer", Logger.NONE)

    // Container properties
    // Life
    override val type = ContainerType.POWERUP_CONTAINER

    override val lifeSpan = 5f
    override val maxHealth = 150f
    override var health = maxHealth
    override var damage = 0f

    // Speed constants
    override val movementSpeed = 8 * sqRatio

    // Movement
    override var pos: Vector2 = initialPos
    override val angleSpeed = MathUtils.random(5f, 50f)

    override lateinit var velocity: Vector2

    // SPRITE

    override val spriteTexture = assets.getTexture(type)
    override val sprite = Sprite(spriteTexture)

    private val w = spriteTexture!!.regionWidth * 1f
    private val h = spriteTexture!!.regionHeight * 1f

    override val collisional: Boolean = false

    override var hitBox : Rectangle
        get() = Rectangle(pos.x - h * yRatio /2, pos.y - h * yRatio /2, h * yRatio, h * yRatio)
        set(value) {}

    override var roundHitBox: Circle
        get() = Circle(pos.x, pos.y, minOf(w, h)/2)
        set(value) {}

    init {
        velocity = when (state) {
            0 -> targetVector.rotate(MathUtils.random(8, 45)* MathUtils.randomSign().toFloat()).setLength(movementSpeed)
            1 -> initialPos.cpy().sub(spaceCraftCenter).scl(-1f).setLength(movementSpeed)
            else -> Vector2()
        }

        log.info("Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(velocity.angle())
    }


}