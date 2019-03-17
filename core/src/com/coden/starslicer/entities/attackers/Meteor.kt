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
             assets: Assets.AttackerAssets): Attacker(snapshots[size]!!, state) {

    companion object {
        val snapshots = mapOf(
                0 to EntityLoader.loadAttacker("SmallMeteor.json"),
                1 to EntityLoader.loadAttacker("MediumMeteor.json"),
                2 to EntityLoader.loadAttacker("LargeMeteor.json")
        )
    }

    private val log = Logger("Meteor", Logger.NONE)

    // Movement
    val movementSpeed= MathUtils.random(1f, maxMovementSpeed)

    private val angleSpeed = when (size) {
        0 -> MathUtils.random(1, 360)
        1 -> MathUtils.random(1, 180)
        2 -> MathUtils.random(0, 90)
        else -> throw IllegalArgumentException()
    }

    // Movement
    override var pos = initialPos
    private var velocity = Vector2()


    override val spriteTexture: TextureRegion? = assets.getTexture(type)
    override val sprite = Sprite(spriteTexture)

    private val width = spriteTexture!!.regionWidth * xRatio/1f
    private val height = spriteTexture!!.regionHeight * yRatio/1f


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
            1 -> initialPos.cpy().sub(spaceCraftCenter).scl(-1f).setLength(movementSpeed)
            else -> Vector2()
        }

        log.info("Launched at Vel:$velocity Init:$initialPos State:$state Size:$size")

        sprite.setCenter(pos.x,pos.y)
    }

    override fun update() {
        updateLife()
        pos.add(velocity)
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

        rotate()

}

    private fun rotate() {
        sprite.rotate(angleSpeed*Gdx.graphics.deltaTime)
    }
}