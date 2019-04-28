package com.coden.starslicer.hud.HUDElements

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2


class Label(val font: BitmapFont){
    val layout = GlyphLayout()
    lateinit var pos: Vector2

    fun setText(text: String){
        layout.setText(font, text)
    }

    fun setPosition(x: Float, y: Float){
        pos = Vector2(x, y)
    }

    fun render(batch: SpriteBatch, text: String = ""){
        if (text.isNotEmpty()){
            setText(text)
        }
        font.draw(batch, layout, pos.x, pos.y)
    }
}