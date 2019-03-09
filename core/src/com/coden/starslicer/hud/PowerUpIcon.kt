package com.coden.starslicer.hud

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.*

class PowerUpIcon(val x: Float, val y: Float, val spriteTexture: TextureRegion?) {

    var amount: Int? = 0

    val width = spriteTexture?.regionWidth !! * sqRatio
    val height = spriteTexture?.regionHeight !! * sqRatio

    val hitBox = Rectangle(x-width/2, y-height/2, width, height)

    fun draw(batch: SpriteBatch) {
        batch.draw(spriteTexture, x, y, width, height)
    }

}