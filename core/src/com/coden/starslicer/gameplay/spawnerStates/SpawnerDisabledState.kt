package com.coden.starslicer.gameplay.spawnerStates

import com.coden.starslicer.commands.CommandQueue
import com.coden.starslicer.gameplay.Spawner
import com.coden.starslicer.util.Log


class SpawnerDisabledState(val spawner: Spawner) : SpawnerState {
    override fun execute(queue: CommandQueue): SpawnerState? {
        if (spawner.waveNum >= spawner.startWave){
            initialize()
            return SpawnerDelayState(spawner)
        }
        return null
    }
    override fun update(queue: CommandQueue): SpawnerState? = execute(queue)

    fun initialize(){
        with(spawner){
            Log.info("Setting up the following Spawner for ${spawnCommand} with wave $waveNum " +
                    "for ${lifeSpanGrowth.init} -> $lifeSpan seconds with delay ${delayGrowth.init} -> $delay", Log.LogType.SPAWN)

            Log.info("Max: $maxNumber , NPP: ${numberGrowth.init} -> $number, Period: ${periodGrowth.init} -> $period", Log.LogType.SPAWN)
        }
    }
}