package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.util.dist2
import com.coden.starslicer.util.xRatio
import com.coden.starslicer.util.yRatio

class Missile (var initialPos: Vector2,
               val state: Int){

    val speed = 17f* dist2(xRatio, yRatio).toFloat()
    // Lifespan and current life of a missile
    var life = 0f
    val lifeSpan = 20f

    var isDead: Boolean = false
        get() = life >= lifeSpan


    // Orbiting
    val radius = 200f
    val initialDt = 50f
    var dt = initialDt


    var previousAngle = 0f
    var instantAngle = 0f
        get() = targetVector.cpy().rotate90(1).angle()


    // Movement
    var pos = initialPos
    var velocity: Vector2

    var angle = 0f
        get() = velocity.angle()

    var targetVector = Vector2(0f, 0f)
        get() = pos.cpy().sub(Gdx.graphics.width/2f, Gdx.graphics.height/2f).scl(-1f)

    // Sprite properties
    val missleTexture = Texture("missile.png")
    val sprite = Sprite(missleTexture)

    val height = missleTexture.height
    val width = missleTexture.width

    /*
    states:
    -1 : Miss
    0 : Orbiting
    1 : Direct
     */
    init {

        velocity = when {
            state == -1 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                                   MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(speed)
            state == 0 -> Vector2(0f, 0f)
            state == 1 -> initialPos.cpy().sub(Gdx.graphics.width/2f, Gdx.graphics.height/2f).scl(-1f).setLength(speed)
            else -> Vector2(0f,0f)
        }

        Gdx.app.log("missle.init", "Launched at Vel:$velocity Angle:$angle Init:$initialPos")
        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(angle)
    }



    fun update() {
        life += Gdx.graphics.deltaTime


        if (state != 0) {
            pos = pos.add(velocity)
        } else{
            moveSpiral()
        }

        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)

    }

    fun render(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    fun getOrbitalX(t: Float) = (radius * Math.cos(t.toDouble())).toFloat() + Gdx.graphics.width / 2
    fun getOrbitalY(t: Float) = (radius * Math.sin(t.toDouble())).toFloat() + Gdx.graphics.height / 2
    fun getOrbitalPos(t: Float) = Vector2(getOrbitalX(t), getOrbitalY(t))

    fun moveSpiral() {
        dt -= 5*Gdx.graphics.deltaTime

        pos = getOrbitalPos(dt)

        sprite.rotate(instantAngle-previousAngle)
        previousAngle = instantAngle

    }




}
