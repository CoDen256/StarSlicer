package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.util.Log

class DifficultyController(val data: EntityData) {
    val queue = CommandQueue(data)
    val firstWave = Wave(0)

    val executeDelta = 0.2f
    var currentExecuteDelta = 0.0f

    // TODO: STATES FOR WAVE - ENDING WAVE - PAUSE - NEW WAVE
    fun update(){

        if (firstWave.update(queue)){

        }else{
            if (!firstWave.isDead){
                Log.info("wave is dead", Log.LogType.SPAWN)
                firstWave.isDead = true
            }
            if (attackers.isEmpty()){
                Log.info("evolving wave", Log.LogType.SPAWN)
                firstWave.evolve()
            }
        }


        currentExecuteDelta += Gdx.graphics.deltaTime
        if (currentExecuteDelta >= executeDelta && !queue.isEmpty) {
            currentExecuteDelta = 0f
            queue.executeNext()
        }

    }
}