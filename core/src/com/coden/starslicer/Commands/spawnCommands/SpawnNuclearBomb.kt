package com.coden.starslicer.Commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.Commands.Command
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.NuclearBomb

class SpawnNuclearBomb(val nuclearBombState: Int = -1): Command {
    override fun execute(data: EntityData) {
        val newState = if (nuclearBombState == -1) MathUtils.random(0, 1) else nuclearBombState

        NuclearBomb.spawn(newState, data.attackerAssets)
    }

    override fun toString(): String {
        return "SpawnNuclearBomb(State:$nuclearBombState)"
    }
}