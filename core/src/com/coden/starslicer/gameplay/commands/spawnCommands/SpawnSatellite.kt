package com.coden.starslicer.gameplay.commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.gameObjects.attackers.Satellite
import com.coden.starslicer.gameplay.events.EventType
import com.coden.starslicer.gameObjects.powerups.PowerUpType

class SpawnSatellite(val stateSatellite: Int = -1, val type: PowerUpType): SpawnCommand() {

    override fun execute() {
        val newState = if (stateSatellite == -1) MathUtils.random(0, 1) else stateSatellite
        val content = if (type == PowerUpType.RANDOM) PowerUpType.values()[MathUtils.random(0,2)] else type

        val attacker = Satellite.spawn(newState, content)
        notify(EventType.SPAWNED, attacker)
    }

    override fun toString(): String {
        return "SpawnSatellite(State: $stateSatellite, Content:$type)"
    }

}