package com.coden.starslicer.Commands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Meteor
import com.coden.starslicer.entities.attackers.Satellite
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.generateRandomSpawnPoint

class SpawnSatellite(val stateSatellite: Int = -1, val type: PowerUp.PowerUpType? = null): Command{

    override fun execute(data: EntityData) {
        val newState = if (stateSatellite == -1) MathUtils.random(0, 1) else stateSatellite
        val content = if (type == null) PowerUp.PowerUpType.values()[MathUtils.random(0,2)] else type
        //if (Satellite.current[newState] >= Satellite.maxAlive[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val satellite = Satellite(spawnPoint, newState, content, data.attackerAssets)
    }

    override fun toString(): String {
        return "SpawnSatellite(State: $stateSatellite)"
    }

}