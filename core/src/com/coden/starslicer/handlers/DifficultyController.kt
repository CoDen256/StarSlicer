package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.util.Log

class DifficultyController(val data: EntityData) {
    val queue = CommandQueue(data)
    var currentWave = 0
    var time = 20f

    var waveLife = 0f

    var life = 0f

    val maxContainer = 10

    val timeSpawn = time / maxContainer

    fun update(){

        waveLife += Gdx.graphics.deltaTime
        life += Gdx.graphics.deltaTime
        //if (waveLife > time) Log.info("Time of wave is exposed")

        if (Math.abs(waveLife - timeSpawn) < 0.01){
            Log.info("Spawning Container")
            Log.info("Time passed: $life")
            queue.add(SpawnContainer())
            waveLife = 0f
        }


        queue.executeNext()



    }
}