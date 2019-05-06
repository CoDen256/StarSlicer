package com.coden.starslicer.gameplay.commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.gameObjects.attackers.Missile
import com.coden.starslicer.gameplay.events.EventType

class SpawnMissile(val missileState: Int = -1) : SpawnCommand() {

    override fun execute(){
        val newState = if (missileState == -1) MathUtils.random(0, 3) else missileState
        val attacker = Missile.spawn(newState)
        notify(EventType.SPAWNED, attacker)
    }

    override fun toString(): String {
        return "SpawnMissile(State:$missileState)"
    }
}