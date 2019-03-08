package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.spaceCraftCenter
import com.coden.starslicer.util.spaceCraftX
import com.coden.starslicer.util.spaceCraftY
import com.coden.starslicer.util.sqRatio

class ShockWave: PowerUp("shockwave") {
    override val damage = 70f

    override val continuous = true
    override var hitBox: Rectangle = Rectangle(0f, 0f,0f,0f)
        get() = Rectangle(0f, 0f,0f,0f)

    override var pos: Vector2 = Vector2(0f, 0f)

    val maxRadius = 1000 * sqRatio
    var radius = 0f

    var shockWaveHitCircle = Circle(0f, 0f, 0f)
    get() = Circle(spaceCraftX, spaceCraftY, radius)

    var active = false

    fun applyEffect() {
        active = true
    }

    fun update() {
        if (radius < maxRadius) radius += maxRadius/15
        else kill()

        val iterator = entities.iterator()
        Gdx.app.log("updateing shockwave", "$entities")
        while (iterator.hasNext()) {
            val entity = iterator.next()
            entity.takeDamage(damage)
        }


    }
}
