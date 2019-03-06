package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.util.xRatio
import com.coden.starslicer.util.yRatio
import com.badlogic.gdx.math.Rectangle

class SpaceCraft {

    val spaceCraftTexture: Texture = Texture("spacecraft.png")

    val height = spaceCraftTexture.height
    val width = spaceCraftTexture.width

    val x = Gdx.graphics.width/2+0f
    val y = Gdx.graphics.height/2+0f

    val relativeHeight = yRatio * height
    val relativeWidth = xRatio * width

    val relativeX = x-relativeHeight/2
    val relativeY = y-relativeWidth/2

    val hitBox = Rectangle(relativeX, relativeY, relativeWidth, relativeHeight)

    fun render(batch: SpriteBatch) {

        batch.draw(spaceCraftTexture, relativeX, relativeY, relativeWidth, relativeHeight)
    }

    fun update() {

    }
}