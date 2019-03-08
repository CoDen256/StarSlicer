package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.util.*

class Meteor(override val initialPos: Vector2,
             override val state: Int,
             size: Int): Attacker(when(size){
                                    0 -> "smallMeteor"
                                    1 -> "mediumMeteor"
                                    2 -> "largeMeteor"
                                    else -> throw IllegalArgumentException()
}){
    // Life
    override val lifeSpan = 20f
    override val maxHealth = when (size) {
        0 -> 30f
        1 -> 70f
        2 -> 150f
        else -> throw IllegalArgumentException()
    }

    override var health = maxHealth

    override var damage = when (size) {
        0 -> 30f
        1 -> 50f
        2 -> 90f
        else -> throw IllegalArgumentException()
    }

    // Speed constants
    override val movementSpeed = when (size) {
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


    // Sprite
    private val width = spriteTexture?.width!! * xRatio/1f
    private val height = spriteTexture?.height!! * yRatio/1f

    override val collisional = true

    override var hitBox = Rectangle(0f, 0f, width, height)
        get() = Rectangle(pos.x-width/2, pos.y-height/2, width, height)

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

        Gdx.app.log("meteor.init", "Launched at Vel:$velocity Init:$initialPos State:$state Size:$size")

        sprite.setCenter(pos.x,pos.y)
    }

    override fun update() {
        updateLife()
        pos.add(velocity)
        sprite.setScale(yRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

        rotate()

}

    private fun rotate() {
        sprite.rotate(angleSpeed*Gdx.graphics.deltaTime)
    }
}