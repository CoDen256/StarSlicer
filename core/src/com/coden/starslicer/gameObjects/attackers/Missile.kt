package com.coden.starslicer.gameObjects.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.graphics.Animator

import com.coden.starslicer.util.*

class Missile private constructor(override val initialPos: Vector2,
               state: Int): Attacker(snapshot, state){

    companion object {
        val snapshot = Attacker.loader.load(AttackerType.MISSILE)

        fun spawn(state: Int): Attacker {
            val initialPos = generateRandomSpawnPoint()
            val missile = Missile(initialPos, state)
            attackers.add(missile)
            return missile
        }
    }


    override val id = "mis$state"


    // Movement
    override var pos = initialPos
    override var velocity = Vector2()


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
    override val hitBox = Rectangle(0f, 0f, height*1.5f, height*1.5f)
    override val hitSphere = Circle(0f, 0f, minOf(height, width)*1.5f/2)

    /*
    states:
    0 : Miss
    1 : Direct
    2 : Orbiting (counter clockwise)
    3 : Spiraling (clockwise)

     */
    val animator = Animator("entities/animation/attackers/Missile/missile_anim.png", 1, 31, 0.05f)
    init {
        // TODO: Spritaling around another point not the spacecraft exactly
        velocity = when (state) {
            0 -> targetVector.cpy().rotate(MathUtils.random(5, 45)*MathUtils.randomSign().toFloat()).setLength(maxMovementSpeed)
            1 -> targetVector.cpy().setLength(maxMovementSpeed)
            else -> Vector2()
        }

        Log.info("Missile $id Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state", Log.LogType.ATTACKERS)

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(if (state == 2) 0f else 180f+velocity.angle())

    }

    override fun render(batch: SpriteBatch) {
        animator.render(batch, sprite)
    }

    override fun update() {
        updateLife()
        when (state){
            0, 1 -> pos.add(velocity.cpy().scl(Gdx.graphics.deltaTime))
            2 -> moveOribting()
            3 -> moveSpiral()
        }



        sprite.setScale(1.2f*yRatio, 1.2f*yRatio)
        sprite.setCenter(pos.x, pos.y)

    }

    private fun moveOribting() {
        //currentOrbitingSpeed = oribtingSpeed * dist2(pos, center)/dist2(initialPos, center)

        pos = pos.add(nextVelocity.cpy().scl(Gdx.graphics.deltaTime))

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
