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
             override val state: Int,
             val size: Int,
             assets: Assets.AttackerAssets): Attacker(snapshots[size]!!) {

    companion object {
        val snapshots = mapOf<Int, AttackerSnapshot>(
                0 to EntityLoader.loadAttacker("SmallMeteor.json"),
                1 to EntityLoader.loadAttacker("MediumMeteor.json"),
                2 to EntityLoader.loadAttacker("LargeMeteors.json")
        )
    }

    private val log = Logger("Meteor", Logger.NONE)
    // Life

    val name2 = when (size) {
        0 -> AttackerType.SMALL_METEOR
        1 -> AttackerType.MEDIUM_METEOR
        2 -> AttackerType.LARGE_METEOR
        else -> throw IllegalArgumentException()
    }

    //override val maxHealth = when (size) {
    //    0 -> 30f
    //    1 -> 70f
    //    2 -> 200f
    //    else -> throw IllegalArgumentException()
    //}

    override var health = maxHealth

    override var damage = when (size) {
        0 -> 30f
        1 -> 50f
        2 -> 90f
        else -> throw IllegalArgumentException()
    }

    // Speed constants
    val rmovementSpeed = when (size) {
        0 -> MathUtils.random(1, 8) * sqRatio
        1 -> MathUtils.random(1, 4) * sqRatio
        2 -> MathUtils.random(1, 2) * sqRatio
        else -> throw IllegalArgumentException()
    }

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

    override var roundHitBox: Circle
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