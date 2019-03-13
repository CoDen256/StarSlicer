package com.coden.starslicer.entities.containers

import com.coden.starslicer.entities.powerups.PowerUp
interface Container {
    val content: PowerUp.PowerUpType
}