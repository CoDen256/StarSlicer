package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.util.*

class Missile (var initialPos: Vector2,
               val state: Int){

    // Speed constants
    val movementSpeed = 25f * xyRatio // direct missile
    val oribtingSpeed = 20f * xyRatio // orbiting
    val spiralSpeedStep = 8f * xyRatio // spiral


    // Vectors
    var targetVector = Vector2(0f, 0f)
        get() = pos.cpy().sub(center).scl(-1f)

    var perpendicularVector = Vector2(0f, 0f)
        get() = targetVector.cpy().rotate90(-1)

    // Spiral Movement
    val radius = 10f
    val initialDt = 40f

    var dt = initialDt

    // Orbit Movement
    var currentOrbitingSpeed = oribtingSpeed

    var nextVelocity = Vector2(0f,0f)
    var previousAngle = 0f

    var instantAngle = 0f
        get() = targetVector.cpy().rotate90(1).angle()

    // Direct Movement
    var pos  = initialPos.cpy()
    var velocity: Vector2


    // Lifespan and current life of a missile
    val lifeSpan = 40f
    var life = 0f

    var isDead: Boolean = false
        get() = life >= lifeSpan

    // Sprite properties
    val missileTexture = Texture("missile.png")
    val sprite = Sprite(missileTexture)

    val height = missileTexture.height
    val width = missileTexture.width

    val states = mapOf(0 to "Missing", 1 to "Orbiting", 2 to "Spiraling", 3 to "Direct")

    /*
    states:
    0 : Miss
    1 : Orbiting
    2 : Spiraling
    3 : Direct
     */
    init {

        velocity = when (state) {
            0 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                         MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(movementSpeed)
            3 -> initialPos.cpy().sub(center).scl(-1f).setLength(movementSpeed)
            else -> Vector2(0f,0f)
        }

        Gdx.app.log("missle.init", "Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state - ${states[state]}")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(if (state == 1) 180f else velocity.angle())

    }



    fun update() {
        life += Gdx.graphics.deltaTime

        when (state){
            0, 3 -> pos = pos.add(velocity)
            1 -> moveOribting()
            2 -> moveSpiral()
        }

        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

    }

    fun render(batch: SpriteBatch) {
        sprite.draw(batch)
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

    fun getOrbitalX(t: Float) = (radius * t * Math.cos(t.toDouble())).toFloat() + Gdx.graphics.width/2f
    fun getOrbitalY(t: Float) = (radius * t * Math.sin(t.toDouble())).toFloat() + Gdx.graphics.height/2f
    fun getOrbitalPos(t: Float) = Vector2(getOrbitalX(t), getOrbitalY(t))





}
