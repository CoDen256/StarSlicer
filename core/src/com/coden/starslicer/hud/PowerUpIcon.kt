package com.coden.starslicer.hud

import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.*

class PowerUpIcon(val type: PowerUp.PowerUpType, private val texture: TextureRegion?) {

    var pos = Vector2()
    var width = 0f

    var amount: Int = 0

    var topright: Vector2
    get() = Vector2(pos.x + width, pos.y + width)
    set(value) {}

    var hitBox: Rectangle
    get() = Rectangle(pos.x, pos.y, width, width)
    set(value) {}

    val font = BitmapFont()
    val glyphLayout = GlyphLayout()

    fun initialize(x: Float, y: Float, size: Float) {
        pos = Vector2(x, y)
        this.width = size
    }

    fun draw(batch: SpriteBatch) {
        batch.draw(texture, pos.x, pos.y, width, width)

        font.data.scaleX = 1.5f*xRatio
        font.data.scaleY = 1.5f*yRatio

        glyphLayout.setText(font, amount.toString())

        font.draw(batch, glyphLayout, topright.x-glyphLayout.width/2, topright.y+glyphLayout.height/3)
    }

}