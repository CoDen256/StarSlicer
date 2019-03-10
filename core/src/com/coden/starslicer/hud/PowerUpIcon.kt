package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.*

class PowerUpIcon(val x: Float, val y: Float,
                  val width: Float, val height: Float,
                  val type: PowerUp.PowerUpType) {

    var amount: Int = 0

    val font = BitmapFont()

    var topright = Vector2(0f, 0f)
    get() = Vector2(x + width, y + width)

    val hitBox = Rectangle(x, y, width, height)

    val glyphLayout = GlyphLayout()


    fun draw(batch: SpriteBatch, assets: Assets.PowerUpAssets) {
        batch.draw(assets.getTexture(type), x, y, width, height)

        font.data.scaleX = 1.5f*xRatio
        font.data.scaleY = 1.5f*yRatio

        glyphLayout.setText(font, amount.toString())

        font.draw(batch, glyphLayout, topright.x-glyphLayout.width/2, topright.y+glyphLayout.height/3)
    }

}