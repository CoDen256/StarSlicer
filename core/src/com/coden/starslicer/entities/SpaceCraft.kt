package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.util.xRatio
import com.coden.starslicer.util.yRatio

class SpaceCraft {

    val spaceCraftTexture: Texture = Texture("spacecraft.png")

    val height = spaceCraftTexture.height
    val width = spaceCraftTexture.width

    val x = Gdx.graphics.width/2+0f
    val y = Gdx.graphics.height/2+0f


    fun render(batch: SpriteBatch) {

        val relativeHeight = yRatio * height
        val relativeWidth = xRatio * width

        batch.draw(spaceCraftTexture, x-relativeHeight/2, y-relativeWidth/2, relativeHeight, relativeWidth)
    }

    fun update() {

    }
}