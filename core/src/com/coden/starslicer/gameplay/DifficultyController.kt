package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.util.Log

class DifficultyController(val data: EntityData) {
    val queue = CommandQueue(data)
    val firstWave = Wave(1)

    val executeDelta = 0f
    var currentExecuteDelta = 0f



    fun update(){

        if (firstWave.update(queue)){

        }


        currentExecuteDelta += Gdx.graphics.deltaTime
        if (currentExecuteDelta >= executeDelta && !queue.isEmpty) {
            currentExecuteDelta = 0f
            queue.executeNext()
        }

    }
}