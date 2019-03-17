package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.EntityData

import com.coden.starslicer.util.*

class Missile (override val initialPos: Vector2,
               state: Int,
               assets: Assets.AttackerAssets): Attacker(snapshot, state, assets){

    companion object {
        val snapshot = EntityLoader.loadAttacker(AttackerType.MISSILE)
    }


    // Movement
    override var pos = initialPos
    private var velocity: Vector2


    // Spiral Movement
    private val radius = snapshot.spiralRadius * sqRatio
    private val initialDt = snapshot.spiralInitialCount * sqRatio

    private var dt = initialDt

    // Orbit Movement
    private var currentOrbitingSpeed = maxMovementSpeed

    private var nextVelocity = Vector2(0f,0f)
    private var previousAngle = 0f

    private var instantAngle: Float
        get() = targetVector.cpy().rotate90(1).angle()
        set(value) {}


    // Sprite
    override var hitBox : Rectangle
        get() = Rectangle(pos.x - height*1.5f/2, pos.y - height*1.5f /2, height*1.5f, height*1.5f)
        set(value) {}

    override var hitCircle: Circle
        get() = Circle(pos.x, pos.y, height/2)
        set(value) {}

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
                    MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(maxMovementSpeed)
            1 -> initialPos.cpy().sub(spaceCraftCenter).scl(-1f).setLength(maxMovementSpeed) // TODO: To spesialized vector change
            else -> Vector2()
        }

        Log.info("Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state")

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
        dt -= maxMovementSpeed*Gdx.graphics.deltaTime

        pos = getOrbitalPos(dt)

        sprite.rotate(instantAngle-previousAngle)
        previousAngle = instantAngle

    }


    private fun getOrbitalX(t: Float) = (radius * t * Math.cos(t.toDouble())).toFloat() + spaceCraftX
    private fun getOrbitalY(t: Float) = (radius * t * Math.sin(t.toDouble())).toFloat() + spaceCraftY
    private fun getOrbitalPos(t: Float) = Vector2(getOrbitalX(t), getOrbitalY(t))



}
