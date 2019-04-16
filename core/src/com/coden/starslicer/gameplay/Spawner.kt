package com.coden.starslicer.gameplay

import com.coden.starslicer.Commands.Command
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.gameplay.spawnerStates.SpawnerDisabledState
import com.coden.starslicer.gameplay.spawnerStates.SpawnerState
import com.coden.starslicer.util.GrowthResolver

data class Spawner(val numberGrowth: GrowthResolver,
                   val periodGrowth: GrowthResolver,
                   val delayGrowth: GrowthResolver,
                   val startWave: Int,
                   val spawnCommand: Command, // TODO: has to be already initialized commands
                   val lifeSpanGrowth: GrowthResolver) {


    val lifeSpan get() =  lifeSpanGrowth.resolve(waveNum).toInt()
    val number get() = numberGrowth.resolve(waveNum).toInt()
    val period get() = periodGrowth.resolve(waveNum)
    val delay get() = delayGrowth.resolve(waveNum)
    val maxNumber get() = number*(lifeSpan/period + 1)

    var isDead: Boolean = false
    var currentState: SpawnerState = SpawnerDisabledState(this)

    var waveNum: Int = 0

    fun evolveTo(num: Int){
        waveNum = num
        isDead = false
    }

    override fun toString() = spawnCommand.toString()

    fun update(queue: CommandQueue){
        if (isDead) return

        currentState = currentState.update(queue) ?: currentState

        isDead = currentState is SpawnerDisabledState
    }


}