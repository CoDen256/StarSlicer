package com.coden.starslicer.Commands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.PowerUpContainer
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.generateRandomSpawnPoint

class SpawnContainer(val state: Int = -1, val type: PowerUp.PowerUpType? = null): Command {
    override fun execute(data: EntityData) {
        val newState = if (state == -1) MathUtils.random(0, 0) else state
        val content = if (type == null) PowerUp.PowerUpType.values()[MathUtils.random(0,2)] else type
        if (PowerUpContainer.current[newState] >= PowerUpContainer.maxAlive[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val powerUpContainer = PowerUpContainer(spawnPoint, newState, content, data.attackerAssets)
    }
}