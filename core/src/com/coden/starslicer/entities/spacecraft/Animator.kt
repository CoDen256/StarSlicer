package com.coden.starslicer.entities.spacecraft

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

class Animator{

    var stateTime = 0f
    val rows = 6
    val cols = 2

    val filePath = "entities/animation/spacecraft/spacecraft_anim.png"
    val animTexture = Texture(filePath)

    lateinit var animation: Animation<TextureRegion>

    val width= animTexture.width/cols
    val height = animTexture.height/rows


    fun render(batch: SpriteBatch, x: Float, y: Float, width: Float, height: Float){
        stateTime += Gdx.graphics.deltaTime
        val currentFrame = animation.getKeyFrame(stateTime, true)
        batch.draw(currentFrame, x, y, width, height)
    }

     fun create() {
        val tmp = TextureRegion.split(animTexture, animTexture.width/cols, animTexture.height/rows)
        val frames = arrayListOf<TextureRegion>()
        var index = 0
        for (row in tmp){
            for (tex in row){
                frames.add(tex)
            }
        }
        animation = Animation(0.025f, *frames.toTypedArray())

    }

    fun dispose() {
    }

}