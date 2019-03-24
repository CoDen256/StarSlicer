package com.coden.starslicer.entities.spacecraft

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.entityInterfaces.Collisional
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.entities.entityInterfaces.DamageTaker
import com.coden.starslicer.util.*
import com.coden.starslicer.util.EntityLoader.loadSpaceCraft
import com.coden.util.swipe.SwipeHandler

object SpaceCraft: DamageTaker, DamageGiver {
    override var isDead = false

    private val snapshot = loadSpaceCraft()

    val xProportion = snapshot.xProportion
    val yProportion = snapshot.yProportion
    override val damage = snapshot.damage

    // Health
    override val maxHealth = snapshot.maxHealth
    override var health = maxHealth

    // Positioning
    override var pos
        get() = Vector2(x, y)
        set(value) {}

    val spaceCraftTexture = Assets.SpaceCraftAssets.spaceCraftTexture

    override val width = xRatio * spaceCraftTexture.regionWidth
    override val height = yRatio * spaceCraftTexture.regionHeight

    val x = Gdx.graphics.width * xProportion
    val y = Gdx.graphics.height * yProportion

    val centerX = x - width /2
    val centerY = y - height /2

    // Sprite
    override val hitBox = Rectangle(centerX, centerY, width, height)
    override val hitSphere = Circle(x, y, minOf(height, width)/2)

    // Shield
    var isShielded = false
    var shieldRadius = 0f

    var shieldCircle: Circle
        get() = Circle(x, y, shieldRadius)
        set(value) {}

    //Blades
    private var blades = arrayOf(BladePoint(0), BladePoint(1))

    val firstBlade = blades[0]
    val secondBlade = blades[1]


    override fun toString()= "SPACECRAFT"


    fun render(batch: SpriteBatch) {

        batch.draw(spaceCraftTexture, centerX, centerY, width, height)
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