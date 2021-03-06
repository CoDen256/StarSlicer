package com.coden.starslicer.gameObjects.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.graphics.Animator
import com.coden.starslicer.util.*

class Meteor private constructor(
             override val initialPos: Vector2,
             state: Int,
             val size: Int): Attacker(snapshots[size], state) {

    companion object {
        val snapshots = arrayOf(
                Attacker.loader.load(AttackerType.SMALL_METEOR),
                Attacker.loader.load(AttackerType.MEDIUM_METEOR),
                Attacker.loader.load(AttackerType.LARGE_METEOR)
        )

        fun spawn(state: Int, size: Int): Attacker {
            val initialPos = generateRandomSpawnPoint()
            val meteor = Meteor(initialPos, state, size)
            attackers.add(meteor)
            return meteor
        }
    }


    override val id = "met$size$state"
    // Constant Speeds
    private val movementSpeed= MathUtils.random(50f, maxMovementSpeed)
    private val angleSpeed = MathUtils.random(snapshot.minAngleSpeed, snapshot.maxAngleSpeed)

    // Movement
    override var pos = initialPos
    override var velocity = Vector2()


    override val hitBox = Rectangle(0f, 0f, width, height)
    override val hitSphere = Circle(0f, 0f, minOf(width, height)/2)

    // state: 0 - miss
    // state: 1 - direct
    // size : 1,2,3 - small, medium, large

    init {
        velocity = when (state) {
            0 -> targetVector.cpy().rotate(MathUtils.random(5, 45)*MathUtils.randomSign().toFloat()).setLength(maxMovementSpeed)
            1 -> targetVector.cpy().setLength(movementSpeed)
            else -> Vector2()
        }

        Log.info("Meteor $id Launched at Vel:$velocity Init:$initialPos State:$state Size:$size", Log.LogType.ATTACKERS)

        sprite.setCenter(pos.x,pos.y)
    }

    override fun update() {
        updateLife()
        pos.add(velocity.cpy().scl(Gdx.graphics.deltaTime))
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

        rotate(angleSpeed)
}

}