package com.coden.starslicer.Commands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.NuclearBomb
import com.coden.starslicer.util.generateRandomSpawnPoint

class SpawnNuclearBomb(val nuclearBombState: Int = -1): Command {
    override fun execute(data: EntityData) {
        val newState = if (nuclearBombState == -1) MathUtils.random(0, 1) else nuclearBombState

        if (NuclearBomb.current[newState] >= NuclearBomb.maxAlive[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val nuclearBomb = NuclearBomb(spawnPoint, newState, data.attackerAssets)
    }
}