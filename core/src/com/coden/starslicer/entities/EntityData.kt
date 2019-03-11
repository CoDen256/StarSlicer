package com.coden.starslicer.entities

import com.coden.starslicer.handlers.AttackerHandler
import com.coden.starslicer.handlers.PowerUpHandler

data class EntityData(val powerupHandler: PowerUpHandler,
                      val attackerHandler: AttackerHandler,
                      val spaceCraft: SpaceCraft) {
}