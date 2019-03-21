package com.coden.starslicer.entities.entityInterfaces

import com.coden.starslicer.entities.powerups.PowerUp

interface Container {
    val content: PowerUp.PowerUpType
}