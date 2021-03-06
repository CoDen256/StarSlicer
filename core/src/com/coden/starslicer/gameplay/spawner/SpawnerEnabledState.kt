package com.coden.starslicer.gameplay.spawner

import com.badlogic.gdx.Gdx
import com.coden.starslicer.gameplay.commands.CommandQueue
import com.coden.starslicer.util.Log

class SpawnerEnabledState (val spawner: Spawner, start: Float): SpawnerState {
    var timePassed = start
    var spawned = 0

    override fun execute(queue: CommandQueue): SpawnerState? {
        if (timePassed >= spawner.period){
            timePassed = 0f
            spawned += spawner.number
            queue.add(spawner.spawnCommand, spawner.number)
        }
        if (spawned >= spawner.maxNumber){
            Log.info("Spawner $spawner is dead", Log.LogType.SPAWN)
            return SpawnerDisabledState(spawner)
        }
        return null
    }

    override fun update(queue: CommandQueue): SpawnerState? {
        timePassed += Gdx.graphics.deltaTime
        return execute(queue)
    }
}