package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.Command
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.util.GrowthResolver
import com.coden.starslicer.util.Log

data class Spawner(private val maxNumberGrowth: GrowthResolver,
                   private val numberGrowth: GrowthResolver,
                   private val periodGrowth: GrowthResolver,
                   private val delayGrowth: GrowthResolver,
                   private val startWave: Int,
                   val spawnCommand: Command,
                   private var waveNum: Int = 0): Mortal {



    private val maxNumber get() =  maxNumberGrowth.resolve(waveNum).toInt()
    private val number get() = numberGrowth.resolve(waveNum).toInt()
    private val period get() = periodGrowth.resolve(waveNum)
    private val delay get() = delayGrowth.resolve(waveNum)

    private val timelimit get() = period*(maxNumber/number - 1)
    private var timePassed = 0f

    private var spawned = 0
    override var isDead = false

    var active = false

    init {
        init()
    }

    fun evolve(){
        waveNum ++
        init()
    }

    fun init() {
        active = false
        isDead = false
        spawned = 0
        Log.info("Setting up the following Spawner for ${spawnCommand} with wave $waveNum for $delay + $timelimit seconds", Log.LogType.SPAWN)
        Log.info("Max: ${maxNumberGrowth.init} -> $maxNumber, NPP: ${numberGrowth.init} -> $number, Period: ${periodGrowth.init} -> $period", Log.LogType.SPAWN)
    }

    override fun toString() = spawnCommand.toString()

    fun update(queue: CommandQueue){
        timePassed += Gdx.graphics.deltaTime

        if (!active){
            if(timePassed >= delay){
                timePassed = period
                active = true
            }
            return
        }

        if (spawned >= maxNumber && !isDead) {
            Log.info("Spawner $this is dead", Log.LogType.SPAWN)
            kill()
        }

        if (isDead) return

        if (timePassed >= period){
            timePassed = 0f
            spawned += number
            queue.add(spawnCommand, number)
        }

    }


}