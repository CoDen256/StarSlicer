package com.coden.starslicer.commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.attackers.PowerUpContainer
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.events.EventType

class SpawnContainer(val state: Int = -1, val type: PowerUp.PowerUpType): SpawnCommand(){

    override fun execute() {
        val newState = if (state == -1) MathUtils.random(0, 0) else state
        val content = if (type == PowerUp.PowerUpType.RANDOM) PowerUp.PowerUpType.values()[MathUtils.random(0,2)] else type
        val attacker = PowerUpContainer.spawn(newState, content)
        notify(EventType.SPAWNED, attacker)
    }

    override fun toString(): String {
        return "SpawnContainer(State:$state, Content:$type)"
    }
}