package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.util.*
import com.coden.starslicer.util.loaders.AttackerLoader
import java.lang.Float.min
import javax.swing.text.html.parser.Entity

class Meteor private constructor(override val initialPos: Vector2,
             state: Int,
             val size: Int,
             assets: Assets.AttackerAssets): Attacker(snapshots[size], state, assets) {

    companion object {
        val snapshots = arrayOf(
                Attacker.loader.load(AttackerType.SMALL_METEOR),
                Attacker.loader.load(AttackerType.MEDIUM_METEOR),
                Attacker.loader.load(AttackerType.LARGE_METEOR)
        )

        fun spawn(state: Int, size: Int, assets: Assets.AttackerAssets): Attacker {
            val initialPos = generateRandomSpawnPoint()
            val meteor = Meteor(initialPos, state, size, assets)
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