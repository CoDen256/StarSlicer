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
    val oribtingSpeed = 19f * sqRatio // orbiting
    val spiralSpeedStep = 3f * sqRatio // spiral

    override val lifeSpan = 40f


    // Vectors
    override var pos = initialPos


    // Spiral Movement
    val radius = 20f * sqRatio
    val initialDt = 80f * sqRatio

    var dt = initialDt

    // Orbit Movement
    var currentOrbitingSpeed = oribtingSpeed

    var nextVelocity = Vector2(0f,0f)
    var previousAngle = 0f

    var instantAngle = 0f
        get() = targetVector.cpy().rotate90(1).angle()


    // Direct Movement
    val size = spriteTexture.height

    override var hitBox = Rectangle(0f, 0f, 0f, 0f)
        get() = Rectangle(pos.x - size * yRatio/2, pos.y - size * yRatio/2, size * yRatio, size * yRatio)

    val states = mapOf(0 to "Missing", 1 to "Orbiting", 2 to "Spiraling", 3 to "Direct")
    var velocity: Vector2
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

        Gdx.app.log("missle.init", "Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state - ${states[state]}")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(if (state == 2) 180f else velocity.angle())

    }



    fun update() {
        updateLife()

        when (state){
            0, 1 -> pos = pos.add(velocity)
            2 -> moveOribting()
            3 -> moveSpiral()
        }

        sprite.setScale(yRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

    }


    fun moveOribting() {
        //currentOrbitingSpeed = oribtingSpeed * dist2(pos, center)/dist2(initialPos, center)

        pos = pos.add(nextVelocity)

        val dAngle = instantAngle - previousAngle

        nextVelocity = perpendicularVector.setLength(currentOrbitingSpeed).rotate(dAngle)

        sprite.rotate(dAngle)
        previousAngle = instantAngle
    }

    fun moveSpiral() {
        dt -= spiralSpeedStep*Gdx.graphics.deltaTime

        pos = getOrbitalPos(dt)

        sprite.rotate(instantAngle-previousAngle)
        previousAngle = instantAngle

    }


    fun getOrbitalX(t: Float) = (radius * t * Math.cos(t.toDouble())).toFloat() + centerX
    fun getOrbitalY(t: Float) = (radius * t * Math.sin(t.toDouble())).toFloat() + centerY
    fun getOrbitalPos(t: Float) = Vector2(getOrbitalX(t), getOrbitalY(t))



}
