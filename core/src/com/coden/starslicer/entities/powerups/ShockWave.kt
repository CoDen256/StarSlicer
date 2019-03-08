package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.*

class ShockWave: PowerUp("shockwave") {


    override val continuous = true
    override var hitBox: Rectangle = Rectangle(0f, 0f,0f,0f)
        get() = Rectangle(0f, 0f,0f,0f)

    override var pos: Vector2 = Vector2(0f, 0f)

    val maxRadius = 1000 * sqRatio
    var radius = 0f

    var iterations = 18
    var life = 0f
    var maxLife = iterations/60f // 0.333 seconds of life

    override val damage = 100f / iterations // 100 damage by 18 iterations

    override var active = false

    fun applyEffect() {
        active = true
    }

    override fun update() {
        life += Gdx.graphics.deltaTime
        //Gdx.app.log("updateing", "$life")
        if (life < maxLife) radius += maxRadius/15
        else kill()

        val iterator = entities.iterator()
        while (iterator.hasNext()) {
            val entity = iterator.next()
            if (dist2(entity.pos, spaceCraftCenter) < radius){
                entity.takeDamage(damage)
            }


        }


    }
}
