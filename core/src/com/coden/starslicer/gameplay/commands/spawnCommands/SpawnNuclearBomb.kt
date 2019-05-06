package com.coden.starslicer.gameplay.commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.gameObjects.attackers.NuclearBomb
import com.coden.starslicer.gameplay.events.EventType


class SpawnNuclearBomb(val nuclearBombState: Int = -1): SpawnCommand() {

    override fun execute() {
        val newState = if (nuclearBombState == -1) MathUtils.random(0, 1) else nuclearBombState
        val attacker = NuclearBomb.spawn(newState)
        notify(EventType.SPAWNED, attacker)
    }

    override fun toString(): String {
        return "SpawnNuclearBomb(State:$nuclearBombState)"
    }
}