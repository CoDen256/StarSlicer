package com.coden.starslicer.Commands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.PowerUpContainer
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.generateRandomSpawnPoint

class SpawnContainer(val state: Int = -1, val type: PowerUp.PowerUpType): Command {
    override fun execute(data: EntityData) {
        val newState = if (state == -1) MathUtils.random(0, 0) else state
        val content = if (type == PowerUp.PowerUpType.RANDOM) PowerUp.PowerUpType.values()[MathUtils.random(0,2)] else type
        PowerUpContainer.spawn(newState, content, data.attackerAssets)
    }

    override fun toString(): String {
        return "SpawnContainer(State:$state, Content:$type)"
    }
}