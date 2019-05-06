package com.coden.starslicer.gameplay.commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.gameObjects.attackers.PowerUpContainer
import com.coden.starslicer.gameplay.events.EventType
import com.coden.starslicer.gameObjects.powerups.PowerUpType

class SpawnContainer(val state: Int = -1, val type: PowerUpType): SpawnCommand(){

    override fun execute() {
        val newState = if (state == -1) MathUtils.random(0, 0) else state
        val content = if (type == PowerUpType.RANDOM) PowerUpType.values()[MathUtils.random(0,2)] else type
        val attacker = PowerUpContainer.spawn(newState, content)
        notify(EventType.SPAWNED, attacker)
    }

    override fun toString(): String {
        return "SpawnContainer(State:$state, Content:$type)"
    }
}