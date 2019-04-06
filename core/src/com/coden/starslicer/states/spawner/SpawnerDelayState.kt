package com.coden.starslicer.states.spawner

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.gameplay.Spawner
import com.coden.starslicer.util.Log

class SpawnerDelayState (val spawner: Spawner): SpawnerState {
    var timePassed = 0f
    override fun execute(queue: CommandQueue): SpawnerState? {
        if(timePassed >= spawner.delay){
            Log.info("Setting Enabled State", Log.LogType.SPAWN)
            return SpawnerEnabledState(spawner, spawner.period)
        }
        return null
    }

    override fun update(queue: CommandQueue): SpawnerState? {
        timePassed += Gdx.graphics.deltaTime
        return execute(queue)
    }
}