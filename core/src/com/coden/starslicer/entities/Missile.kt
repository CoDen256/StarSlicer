package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

import com.coden.starslicer.util.*

class Missile (override val initialPos: Vector2,
               override val state: Int): Attacker("missile.png"){

    // Speed constants
    override val movementSpeed = 25f * sqRatio // direct missile
    private val oribtingSpeed = 19f * sqRatio // orbiting
    private val spiralSpeedStep = 3f * sqRatio // spiral

    // Life
    override val lifeSpan = 40f

    // Movement
    override var pos = initialPos
    private var velocity: Vector2


    // Spiral Movement
    private val radius = 20f * sqRatio
    private val initialDt = 80f * sqRatio

    private var dt = initialDt

    // Orbit Movement
    private var currentOrbitingSpeed = oribtingSpeed

    private var nextVelocity = Vector2(0f,0f)
    private var previousAngle = 0f

    private var instantAngle = 0f
        get() = targetVector.cpy().rotate90(1).angle()


    // Sprite
    private val w = spriteTexture.width
    private val h = spriteTexture.height
    private val states = mapOf(0 to "Missing", 1 to "Orbiting", 2 to "Spiraling", 3 to "Direct")

    override var hitBox = Rectangle(0f, 0f, 0f, 0f)
        get() = Rectangle(pos.x - h * yRatio/2, pos.y - h * yRatio/2, h * yRatio, h * yRatio)


    /*
    states:
    0 : Miss
    1 : Direct
    2 : Orbiting (counter clockwise)
    3 : Spiraling (clockwise)

     */
    init {
        velocity = when (state) {
            0 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                    MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(movementSpeed)
            1 -> initialPos.cpy().sub(center).scl(-1f).setLength(movementSpeed)
            else -> Vector2()
        }

        Gdx.app.log("missile.init", "Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state - ${states[state]}")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(if (state == 2) 180f else velocity.angle())

    }



    override fun update() {
        updateLife()

        when (state){
            0, 1 -> pos = pos.add(velocity)
            2 -> moveOribting()
            3 -> moveSpiral()
        }

        sprite.setScale(yRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

    }


    private fun moveOribting() {
        //currentOrbitingSpeed = oribtingSpeed * dist2(pos, center)/dist2(initialPos, center)

        pos = pos.add(nextVelocity)

        val dAngle = instantAngle - previousAngle

        nextVelocity = perpendicularVector.setLength(currentOrbitingSpeed).rotate(dAngle)

        sprite.rotate(dAngle)
        previousAngle = instantAngle
    }

    private fun moveSpiral() {
        dt -= spiralSpeedStep*Gdx.graphics.deltaTime

        pos = getOrbitalPos(dt)

        sprite.rotate(instantAngle-previousAngle)
        previousAngle = instantAngle

    }


    private fun getOrbitalX(t: Float) = (radius * t * Math.cos(t.toDouble())).toFloat() + centerX
    private fun getOrbitalY(t: Float) = (radius * t * Math.sin(t.toDouble())).toFloat() + centerY
    private fun getOrbitalPos(t: Float) = Vector2(getOrbitalX(t), getOrbitalY(t))



}
