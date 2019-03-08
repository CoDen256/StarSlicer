package com.coden.starslicer.entities.powerups

import com.coden.starslicer.entities.Entity
import com.coden.starslicer.util.textureMap

abstract class PowerUp(name: String) : Entity {
    val texture = textureMap[name]
    override var health = 1f
    override val maxHealth = 1f
    override var isDead = false

    abstract val continuous: Boolean

}