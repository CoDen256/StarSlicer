package com.coden.starslicer.Commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.NuclearBomb
import com.coden.starslicer.events.EventType


class SpawnNuclearBomb(val nuclearBombState: Int = -1): SpawnCommand() {

    override fun execute(data: EntityData) {
        val newState = if (nuclearBombState == -1) MathUtils.random(0, 1) else nuclearBombState
        val attacker = NuclearBomb.spawn(newState, data.attackerAssets)
        notify(EventType.SPAWNED, attacker)
    }

    override fun toString(): String {
        return "SpawnNuclearBomb(State:$nuclearBombState)"
    }
}