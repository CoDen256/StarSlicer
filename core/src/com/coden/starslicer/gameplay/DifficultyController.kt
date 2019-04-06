package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.spaceCraftX

class DifficultyController(val data: EntityData) {
    val queue = CommandQueue(data)
    val currentWave = Wave(0, queue)

    val executeDelta = 0.2f
    var currentExecuteDelta = 0.0f

    fun update(){

        currentWave.update()


        currentExecuteDelta += Gdx.graphics.deltaTime
        if (currentExecuteDelta >= executeDelta && !queue.isEmpty) {
            currentExecuteDelta = 0f
            queue.executeNext()
        }

    }

    fun render(batch: SpriteBatch, font: BitmapFont){
        font.draw(batch, "Wave #${currentWave.number}", 50f, Gdx.graphics.height - 50f)
        currentWave.render(batch, font)
    }
}