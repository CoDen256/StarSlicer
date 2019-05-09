package com.coden.starslicer.gameObjects.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.gameObjects.Container
import com.coden.starslicer.gameObjects.powerups.PowerUp
import com.coden.starslicer.gameObjects.powerups.PowerUpType
import com.coden.starslicer.graphics.Animator
import com.coden.starslicer.util.*

// TODO: show which powerup contains
class PowerUpContainer private constructor(
        override val initialPos: Vector2,
        state: Int,
        override val content: PowerUpType): Attacker(snapshot, state), Container {

    companion object {
        val snapshot = Attacker.loader.load(AttackerType.POWERUP_CONTAINER)

        fun spawn(state: Int, content: PowerUpType): Attacker{
            val initialPos = generateRandomSpawnPoint()
            val container = PowerUpContainer(initialPos, state, content)
            attackers.add(container)
            return container
        }
    }


    override val id = "puc$state" + PowerUpType.convert(content)
    // Movement
    override var pos: Vector2 = initialPos
    override var velocity = Vector2()

    private val angleSpeed = MathUtils.random(snapshot.minAngleSpeed, snapshot.maxAngleSpeed)

    // SPRITE
    override val hitBox  = Rectangle(0f, 0f, height, height)
    override val hitSphere = Circle(0f, 0f, height/2)

    val anim = mapOf(0 to Pair("shield", 11),
                                    1 to Pair("hpboost", 10),
                                    2 to Pair("shockwave", 10))[PowerUpType.convert(content)]!!

    val animator = Animator("entities/animation/attackers/PowerUpContainer/${anim.first}_anim.png", 4, anim.second, 0.025f)
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

    override fun render(batch: SpriteBatch) {
        animator.render(batch, sprite)
    }

    override fun update() {
        updateLife()
        pos.add(velocity.cpy().scl(Gdx.graphics.deltaTime))
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

        rotate(angleSpeed)
    }



}