package com.coden.starslicer.entities.powerups

import com.coden.starslicer.entities.Entity

class ShockWave: PowerUp("shockwave") {
    override val damage = 50f

    override val continuous = true
}
