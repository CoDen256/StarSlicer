package com.coden.starslicer.hud

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Assets
import com.coden.starslicer.util.textureMap
import com.coden.starslicer.util.xRatio
import com.coden.starslicer.util.yRatio

class PowerUpIcon(val pos: Vector2, val spriteTexture: TextureRegion?) {

    var amount: Int? = 0

    val width = spriteTexture?.regionWidth !! * xRatio
    val height = spriteTexture?.regionHeight !! * yRatio

    val hitBox = Rectangle(pos.x-width/2, pos.y-height/2, width, height)

    fun draw(batch: SpriteBatch) {
        batch.draw(spriteTexture, pos.x, pos.y)
    }

}