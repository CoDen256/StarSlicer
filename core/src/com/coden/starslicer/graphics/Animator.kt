package com.coden.starslicer.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animator(path: String, cols: Int, rows:Int, duration:Float){

    private var stateTime = 0f

    private val filePath = path
    private val animTexture = Texture(filePath)

    val frameWidth= animTexture.width/cols
    val frameHeight = animTexture.height/rows

    private var animation: Animation<TextureRegion>


     init {
         val tmp = TextureRegion.split(animTexture, frameWidth, frameHeight)
         val frames = arrayListOf<TextureRegion>()
         for (row in tmp){
             for (textureRegion in row){
                 frames.add(textureRegion)
             }
         }
         animation = Animation(duration, *frames.toTypedArray())
     }

    fun render(batch: SpriteBatch, x: Float, y: Float, width: Float=frameWidth+0f, height: Float=frameHeight+0f){
        stateTime += Gdx.graphics.deltaTime
        val currentFrame = animation.getKeyFrame(stateTime, true)
        batch.draw(currentFrame, x, y, width, height)
    }

    fun render(batch: SpriteBatch, sprite: Sprite){
        stateTime += Gdx.graphics.deltaTime
        val currentFrame = animation.getKeyFrame(stateTime, true)
        sprite.setRegion(currentFrame)
        sprite.draw(batch)
    }

    fun dispose() {
    }

}