package com.coden.starslicer.gameplay.spawnerStates

import com.badlogic.gdx.Gdx
import com.coden.starslicer.commands.CommandQueue
import com.coden.starslicer.gameplay.Spawner

class SpawnerDelayState (val spawner: Spawner): SpawnerState {
    var timePassed = 0f
    override fun execute(queue: CommandQueue): SpawnerState? {
        if(timePassed >= spawner.delay){
            return SpawnerEnabledState(spawner, spawner.period)
        }
        return null
    }

    override fun update(queue: CommandQueue): SpawnerState? {
        timePassed += Gdx.graphics.deltaTime
        return execute(queue)
    }
}