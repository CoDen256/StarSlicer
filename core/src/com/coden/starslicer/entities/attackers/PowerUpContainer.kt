package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.entityInterfaces.Container
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.*

// TODO: show which powerup contains
class PowerUpContainer private constructor(
        override val initialPos: Vector2,
        state: Int,
        override val content: PowerUp.PowerUpType,
        assets: Assets.AttackerAssets): Attacker(snapshot, state, assets), Container {

    companion object {
        val snapshot = Attacker.loader.load(AttackerType.POWERUP_CONTAINER)

        fun spawn(state: Int, content: PowerUp.PowerUpType, assets: Assets.AttackerAssets): Attacker{
            val initialPos = generateRandomSpawnPoint()
            val container = PowerUpContainer(initialPos, state, content, assets)
            attackers.add(container)
            return container
        }
    }


    override val id = "puc$state" + PowerUp.convert(content)
    // Movement
    override var pos: Vector2 = initialPos
    override var velocity = Vector2()

    private val angleSpeed = MathUtils.random(snapshot.minAngleSpeed, snapshot.maxAngleSpeed)

    // SPRITE
    override val hitBox  = Rectangle(0f, 0f, height, height)
    override val hitSphere = Circle(0f, 0f, height/2)

    init {
        velocity = when (state) {
            0 -> targetVector.cpy().rotate(MathUtils.random(8, 20)* MathUtils.randomSign().toFloat()).setLength(maxMovementSpeed)
            1 -> targetVector.cpy().setLength(maxMovementSpeed)
            else -> Vector2()
        }

        Log.info("PUC Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state", Log.LogType.ATTACKERS)

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(velocity.angle())
    }

    override fun update() {
        updateLife()
        pos.add(velocity.cpy().scl(Gdx.graphics.deltaTime))
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

        rotate(angleSpeed)
    }



}