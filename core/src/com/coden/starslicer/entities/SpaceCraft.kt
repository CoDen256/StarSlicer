package com.coden.starslicer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.BladePoint
import com.coden.starslicer.util.*
import com.coden.util.swipe.SwipeHandler

object SpaceCraft : Entity { // TODO: Has to be Singleton


    var isShielded = false
    var shieldRadius = 0f
    var shieldCircle: Circle
    get() = Circle(x, y, shieldRadius)
    set(value) {}

    override val maxHealth = 100f
    override var health = maxHealth

    override val damage = 5f
    override var isDead = false

    override var pos: Vector2
    get() = Vector2(x, y)
    set(value) {}

    val spaceCraftTexture = Assets.SpaceCraftAssets.spaceCraftTexture

    val height = spaceCraftTexture.regionHeight
    val width = spaceCraftTexture.regionWidth

    val x = spaceCraftX
    val y = spaceCraftY

    val relativeHeight = yRatio * height
    val relativeWidth = xRatio * width

    val relativeX = x-relativeWidth/2
    val relativeY = y-relativeHeight/2

    override var hitBox = Rectangle(relativeX, relativeY, relativeWidth, relativeHeight)
    override var hitCircle = Circle(x, y, (relativeHeight+relativeWidth)/2)

    private var blades = arrayOf(BladePoint(0), BladePoint(1))

    val firstBlade = blades[0]
    val secondBlade = blades[1]


    fun render(batch: SpriteBatch) {

        batch.draw(spaceCraftTexture, relativeX, relativeY, relativeWidth, relativeHeight)
    }

    fun update(swipe: SwipeHandler) {

        for (blade in blades) {
            blade.update(swipe)
        }

        updateInput()

    }

    fun updateInput() {
        if (Gdx.input.isTouched(0)) {
            firstBlade.active = true

        }
        if (Gdx.input.isTouched(1)) {
            secondBlade.active = true
        }
    }


    fun dispose() {
        Assets.SpaceCraftAssets.dispose()
    }


}