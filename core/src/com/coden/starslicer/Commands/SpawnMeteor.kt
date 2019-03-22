package com.coden.starslicer.Commands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Meteor
import com.coden.starslicer.util.generateRandomSpawnPoint

class SpawnMeteor(val state: Int = -1, val size: Int = -1):Command {
    override fun execute(data: EntityData) {
        val newState = if (state == -1) MathUtils.random(0, 1) * MathUtils.random(0, 1) else state
        val newSize = if (size == -1) MathUtils.random(0, 2) else size

        if (Meteor.current[newSize] >= data.maxMeteors[newSize]) return

        val spawnPoint = generateRandomSpawnPoint()
        val meteor = Meteor(spawnPoint, newState, newSize, data.attackerAssets)
}
}