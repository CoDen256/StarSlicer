package com.coden.starslicer.hud.HUDElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.entityInterfaces.Mortal

class ExclamationMark(val pos: Vector2): UIObject{
    override var isDead = false
    var lifeSpan = 2f

    private var font = BitmapFont()
    init {
        font.data.setScale(4f)
        font.setColor(1f, 0f, 0f,1f)
    }

    override fun render(shapeRenderer: ShapeRenderer) {

    }

    override fun render(batch: SpriteBatch){
        font.draw(batch, "!", pos.x, pos.y)
    }

    override fun update(){
        lifeSpan -= Gdx.graphics.deltaTime
        if (lifeSpan < 0){
            kill()
        }
    }

    override fun dispose() {
        font.dispose()
    }
}