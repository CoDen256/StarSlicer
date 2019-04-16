package com.coden.starslicer.hud.HUDElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.entityInterfaces.Mortal

class ExclamationMark(val pos: Vector2): Mortal{
    override var isDead = false
    var lifeSpan = 5f

    private var font = BitmapFont()
    init {
        font.data.setScale(3f)
        font.setColor(1f, 0f, 0f,1f)
    }
    fun render(batch: SpriteBatch){
        font.draw(batch, "!", pos.x, pos.y)
    }
    fun update(){
        lifeSpan -= Gdx.graphics.deltaTime
        if (lifeSpan < 0){
            kill()
        }
    }

}