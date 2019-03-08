package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.coden.starslicer.util.xRatio
import com.coden.starslicer.util.yRatio
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.util.spaceCraftX
import com.coden.starslicer.util.spaceCraftY

class SpaceCraft : Entity {

    val spaceCraftTexture: Texture = Texture("spacecraft.png")

    var isShielded = false
    var shieldRadius = 0f
    var shieldCircle = Circle(0f, 0f, 0f)
    get() = Circle(x, y, shieldRadius)

    override val maxHealth = 100f
    override var health = maxHealth

    override val damage = 5f
    override var isDead = false

    override var pos = Vector2(0f,0f)
    get() = Vector2(x, y)

    val height = spaceCraftTexture.height
    val width = spaceCraftTexture.width

    val x = spaceCraftX
    val y = spaceCraftY

    val relativeHeight = yRatio * height
    val relativeWidth = xRatio * width

    val relativeX = x-relativeWidth/2
    val relativeY = y-relativeHeight/2

    override var hitBox = Rectangle(relativeX, relativeY, relativeWidth, relativeHeight)

    fun render(batch: SpriteBatch) {

        batch.draw(spaceCraftTexture, relativeX, relativeY, relativeWidth, relativeHeight)
    }

    fun update() {

    }



}