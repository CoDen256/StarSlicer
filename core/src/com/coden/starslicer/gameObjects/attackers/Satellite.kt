package com.coden.starslicer.gameObjects.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
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

class Satellite private constructor(
        override val initialPos: Vector2,
        state: Int,
        override val content: PowerUpType) : Attacker(snapshot, state), Container {

    companion object {
        val snapshot = Attacker.loader.load(AttackerType.SATELLITE)

        fun spawn(state: Int, content: PowerUpType): Attacker{
            val initialPos = generateRandomSpawnPoint()
            val satellite = Satellite(initialPos, state, content)
            attackers.add(satellite)
            return satellite
        }
    }


    override val id = "sat$state$" + PowerUpType.convert(content)
    // Movement
    override var pos: Vector2 = initialPos
    override var velocity = Vector2()

    val angleSpeed = MathUtils.random(snapshot.minAngleSpeed, snapshot.maxAngleSpeed)



    // SPRITE

    override val hitBox = Rectangle(pos.x - width/2, pos.y - height/2, width , height )
    override val hitSphere = Circle(pos.x, pos.y, minOf(width, height)/2)

    val animator = Animator("entities/animation/attackers/Satellite/satellite_anim.png", 3, 9, 0.025f)
    init {
        velocity = when (state) {
            0 -> targetVector.cpy().rotate(MathUtils.random(15, 35)* MathUtils.randomSign().toFloat()).setLength(maxMovementSpeed)
            1 -> targetVector.cpy().setLength(maxMovementSpeed)
            else -> Vector2()
        }

        Log.info("Satellite $id Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state", Log.LogType.ATTACKERS)

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