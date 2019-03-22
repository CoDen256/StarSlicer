package com.coden.starslicer.Commands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.util.generateRandomSpawnPoint

class SpawnMissile(val missileState) : Command{
    override fun execute(data: EntityData) {
        val newState = if (state == -100) MathUtils.random(0, 3) else state

        if (data.currentMissiles[newState] >= data.maxMissiles[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val missile = Missile(spawnPoint, newState, data.attackerAssets)
        data.increment(missile.type, newState)
    }
}