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

    var speed = 25f * dist2(xRatio, yRatio)
    var varSpeed = speed
    // Lifespan and current life of a missile
    var life = 0f
    val lifeSpan = 50f

    var isDead: Boolean = false
        get() = life >= lifeSpan


    // Orbiting
    val radius = 10f
    val initialDt = 50f
    var dt = initialDt

    var prevVel = Vector2(0f,0f)
    var previousAngle = 0f

    var instantAngle = 0f
        get() = targetVector.cpy().rotate90(1).angle()

    var dtime = 0f


    // Movement
    var pos  = initialPos.cpy() //= Vector2(300f, 300f)
    var velocity: Vector2

    var angle = 0f
        get() = velocity.angle()

    var targetVector = Vector2(0f, 0f)
        get() = pos.cpy().sub(Gdx.graphics.width/2f, Gdx.graphics.height/2f).scl(-1f)

    var perpVector = Vector2(0f, 0f)
        get() = targetVector.cpy().rotate90(-1)

    // Sprite properties
    val missleTexture = Texture("missile.png")
    val sprite = Sprite(missleTexture)

    val height = missleTexture.height
    val width = missleTexture.width

    /*
    states:
    0 : Miss
    1 : Orbiting
    2 : Spiraling
    3 : Direct
     */
    init {

        velocity = when {
            state == 0 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                                   MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(speed)
            state == 3 -> initialPos.cpy().sub(Gdx.graphics.width/2f, Gdx.graphics.height/2f).scl(-1f).setLength(speed)
            else -> Vector2(0f,0f)
        }

        Gdx.app.log("missle.init", "Launched at Vel:$velocity Angle:$angle Init:$initialPos")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(if (state == 1) 180f else angle)

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

    fun getOrbitalX(t: Float) = (radius * t * Math.cos(t.toDouble())).toFloat() + Gdx.graphics.width/2f
    fun getOrbitalY(t: Float) = (radius * t * Math.sin(t.toDouble())).toFloat() + Gdx.graphics.height/2f
    fun getOrbitalPos(t: Float) = Vector2(getOrbitalX(t), getOrbitalY(t))

    fun moveOribting() {
        //varSpeed = speed * dist2(pos, center) / dist2(initialPos, center)

        pos = pos.add(prevVel)

        val dAngle = instantAngle - previousAngle
        prevVel = perpVector.setLength(1.5f*varSpeed).rotate(dAngle)

        sprite.rotate(instantAngle-previousAngle)
        previousAngle = instantAngle
    }

    fun moveSpiral() {
        dt -= 5*Gdx.graphics.deltaTime


        pos = getOrbitalPos(dt)

        sprite.rotate(instantAngle-previousAngle)
        previousAngle = instantAngle

    }




}
