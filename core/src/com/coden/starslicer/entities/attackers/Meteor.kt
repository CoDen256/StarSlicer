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
import java.lang.Float.min
import javax.swing.text.html.parser.Entity

class Meteor(override val initialPos: Vector2,
             state: Int,
             val size: Int,
             assets: Assets.AttackerAssets): Attacker(snapshots[size]!!, state, assets) {

    companion object {
        val snapshots = mapOf(
                0 to EntityLoader.loadAttacker(AttackerType.SMALL_METEOR),
                1 to EntityLoader.loadAttacker(AttackerType.MEDIUM_METEOR),
                2 to EntityLoader.loadAttacker(AttackerType.LARGE_METEOR)
        )
    }

    // Constant Speeds
    private val movementSpeed= MathUtils.random(50f, maxMovementSpeed)
    private val angleSpeed = MathUtils.random(snapshot.minAngleSpeed, snapshot.maxAngleSpeed)

    // Movement
    override var pos = initialPos
    private var velocity = Vector2()


    override var hitBox :Rectangle
        get() = Rectangle(pos.x-width/2, pos.y-height/2, width, height)
        set(value) {}

    override var hitCircle: Circle
        get() = Circle(pos.x, pos.y, minOf(width, height)/2)
        set(value) {}

    // state: 0 - miss
    // state: 1 - direct
    // size : 1,2,3 - small, medium, large

    init {
        velocity = when (state) {
            0 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                         MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(movementSpeed)
            1 -> targetVector.cpy().setLength(movementSpeed)
            else -> Vector2()
        }

        Log.info("Meteor Launched at Vel:$velocity Init:$initialPos State:$state Size:$size")

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