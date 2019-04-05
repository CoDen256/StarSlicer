package com.coden.starslicer.Commands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.util.generateRandomSpawnPoint

class SpawnMissile(val missileState: Int = -1) : Command{

    override fun execute(data: EntityData){
        val newState = if (missileState == -1) MathUtils.random(0, 3) else missileState
        Missile.spawn(newState, data.attackerAssets)
    }

    override fun toString(): String {
        return "SpawnMissile(State:$missileState)"
    }
}