package com.coden.starslicer.Commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.Commands.Command
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Satellite
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.events.EventType
import com.coden.starslicer.events.Observer

class SpawnSatellite(val stateSatellite: Int = -1, val type: PowerUp.PowerUpType): SpawnCommand() {

    override fun execute(data: EntityData) {
        val newState = if (stateSatellite == -1) MathUtils.random(0, 1) else stateSatellite
        val content = if (type == PowerUp.PowerUpType.RANDOM) PowerUp.PowerUpType.values()[MathUtils.random(0,2)] else type

        val attacker = Satellite.spawn(newState, content, data.attackerAssets)
        notify(EventType.SPAWNED, attacker)
    }

    override fun toString(): String {
        return "SpawnSatellite(State: $stateSatellite, Content:$type)"
    }

}