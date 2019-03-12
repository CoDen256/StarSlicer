package com.coden.starslicer.entities.containers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.spaceCraftCenter
import com.coden.starslicer.util.xRatio
import com.coden.starslicer.util.yRatio

abstract class Container : Attacker() {

    enum class ContainerType{
        SATELLITE, POWERUP_CONTAINER
    }

    abstract val type: ContainerType
    abstract val content: PowerUp.PowerUpType

    abstract var velocity: Vector2
    abstract val angleSpeed: Float


    override fun update() {
        updateLife()
        pos = pos.add(velocity)
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)
        sprite.rotate(angleSpeed)
    }

}