package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.Command
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.util.GrowthResolver
import com.coden.starslicer.util.Log

data class Spawner(private val numberGrowth: GrowthResolver,
                   private val periodGrowth: GrowthResolver,
                   private val delayGrowth: GrowthResolver,
                   private val startWave: Int,
                   val spawnCommand: Command,
                   private val lifeSpanGrowth: GrowthResolver= GrowthResolver(30.0f, 5.7f, GrowthResolver.GrowthType.POLYNOMIAL)): Mortal {



    private val lifeSpan get() =  lifeSpanGrowth.resolve(waveNum).toInt()
    private val number get() = numberGrowth.resolve(waveNum).toInt()
    private val period get() = periodGrowth.resolve(waveNum)
    private val delay get() = delayGrowth.resolve(waveNum)

    private val maxNumber get() = number*(lifeSpan/period + 1)
    private var timePassed = 0f

    private var spawned = 0
    override var isDead = false

    var waveNum: Int = 0

    var active = false

    init {
        initialize(0)
    }

    fun evolve(){
        initialize(waveNum + 1)
    }

    fun initialize(num: Int = 0) {
        waveNum = num

        active = false
        isDead = false
        spawned = 0
        if (waveNum >= startWave) {
            Log.info("Setting up the following Spawner for ${spawnCommand} with wave $waveNum " +
                    "for ${lifeSpanGrowth.init} -> $lifeSpan seconds with delay ${delayGrowth.init} -> $delay", Log.LogType.SPAWN)

            Log.info("Max: $maxNumber , NPP: ${numberGrowth.init} -> $number, Period: ${periodGrowth.init} -> $period", Log.LogType.SPAWN)
        }
    }

    override fun toString() = spawnCommand.toString()

    fun update(queue: CommandQueue){
        timePassed += Gdx.graphics.deltaTime

        if (isDead) return

        if (startWave > waveNum){
            kill()
            return
        }

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
            return
        }


        if (timePassed >= period){
            timePassed = 0f
            spawned += number
            queue.add(spawnCommand, number)
        }

    }


}