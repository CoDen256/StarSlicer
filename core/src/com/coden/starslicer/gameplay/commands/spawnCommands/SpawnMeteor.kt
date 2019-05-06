package com.coden.starslicer.gameplay.commands.spawnCommands

import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.gameObjects.attackers.Meteor
import com.coden.starslicer.gameplay.events.EventType

class SpawnMeteor(val state: Int = -1, val size: Int = -1): SpawnCommand() {
    override fun execute() {
        val newState = if (state == -1) MathUtils.random(0, 1) * MathUtils.random(0, 1) else state
        val newSize = if (size == -1) MathUtils.random(0, 2) else size

        val attacker = Meteor.spawn(newState, newSize)
        notify(EventType.SPAWNED, attacker)
}
    override fun toString(): String {
        return "SpawnMeteor(State:$state, Size:$size)"
    }
}