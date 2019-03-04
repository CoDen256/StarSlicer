package com.mygdx.slicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.mygdx.slicer.util.dist2
import com.mygdx.slicer.util.xRatio
import com.mygdx.slicer.util.yRatio
import java.lang.Math.cos

class Missile (var initialPos: Vector2,
               val state: Int){

    var life = 0f
    var angle = 0f
    var pos = initialPos

    val missleTexture = Texture("missile.png")
    val sprite = Sprite(missleTexture)
    val initT = 50f

    var t = initT


    val speed = 17f*dist2(xRatio, yRatio).toFloat()
    val acc = 10f*dist2(xRatio, yRatio).toFloat()
    val lifeSpan = 20f

    val height = missleTexture.height
    val width = missleTexture.width

    var velocity: Vector2
    var accelaration = Vector2(0f,0f)
    get() = pos.cpy().sub(Gdx.graphics.width/2f, Gdx.graphics.height/2f).scl(-1f).setLength(0.5f)

    var accelaration2 = Vector2(0f,0f)
    /*
    states:
    -1 : Miss
    0 : Orbiting
    1 : Direct
     */
    init {
        initialPos = Vector2(500f, 10f)
        velocity = when {
            state == -1 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                                   MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(speed)
            state == 0 -> Vector2(MathUtils.random(20, Gdx.graphics.width-20)+0f,
                    MathUtils.random(20, Gdx.graphics.height-20)+0f).sub(initialPos).setLength(speed)
            state == 1 -> initialPos.cpy().sub(Gdx.graphics.width/2f, Gdx.graphics.height/2f).scl(-1f).setLength(speed)
            else -> Vector2(0f,0f)
        }

        angle = velocity.angle()
        Gdx.app.log("missle.init", "Launched at Vel:$velocity Angle:$angle Init:$initialPos")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(angle)
    }



    fun update() {
        life += Gdx.graphics.deltaTime
        t -= 5*Gdx.graphics.deltaTime



        if (state != 0) {
            pos = pos.add(velocity)
        } else {
            //pos.x = (10*t * Math.cos(t.toDouble())).toFloat() + Gdx.graphics.width/2
            //pos.y = (10*t * Math.sin(t.toDouble())).toFloat() + Gdx.graphics.height/2
            //println("$pos: $t")
            velocity.add(accelaration.cpy())//.scl(0.05f))
            pos = pos.add(velocity)

            Gdx.app.log("missile.update", "Vel:$velocity, Pos:$pos, Acceleration:$accelaration")


        }
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)


    }

    fun render(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    var isDead: Boolean = false
    get() = life >= lifeSpan

}
