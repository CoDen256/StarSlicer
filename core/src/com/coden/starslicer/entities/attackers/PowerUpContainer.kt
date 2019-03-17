package com.coden.starslicer.entities.attackers

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
        state: Int,
        val content: PowerUp.PowerUpType,
        assets: Assets.ContainerAssets): Attacker(snapshot, state){

    // TODO: Content of container decided on conditions of current situation

    companion object {
        val snapshot = EntityLoader.loadAttacker("PowerUpContainer.json")
    }

    private val log = Logger("PowerUpContainer", Logger.NONE)

    // Speed constants
    // Movement
    override var pos: Vector2 = initialPos
    val angleSpeed = MathUtils.random(1f, 4f)

    var velocity: Vector2

    // SPRITE

    override val spriteTexture = assets.getTexture(type)
    override val sprite = Sprite(spriteTexture)

    private val w = spriteTexture!!.regionWidth * 1f
    private val h = spriteTexture!!.regionHeight * 1f

    override var hitBox : Rectangle
        get() = Rectangle(pos.x - h * yRatio /2, pos.y - h * yRatio /2, h * yRatio, h * yRatio)
        set(value) {}

    override var hitCircle: Circle
        get() = Circle(pos.x, pos.y, minOf(w, h)/2)
        set(value) {}

    init {
        velocity = when (state) {
            0 -> targetVector.rotate(MathUtils.random(8, 45)* MathUtils.randomSign().toFloat()).setLength(maxMovementSpeed)
            1 -> initialPos.cpy().sub(spaceCraftCenter).scl(-1f).setLength(maxMovementSpeed)
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