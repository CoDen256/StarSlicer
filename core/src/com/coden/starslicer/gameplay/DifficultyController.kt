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
    val firstWave = Wave(99)

    val executeDelta = 0.2f
    var currentExecuteDelta = 0.0f

    var waiting = false

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
                waiting = false
                firstWave.evolve()
            }else{
                waiting = true
                //Log.info("Waiting for all attackers to be destroyed", Log.LogType.SPAWN)
            }
        }


        currentExecuteDelta += Gdx.graphics.deltaTime
        if (currentExecuteDelta >= executeDelta && !queue.isEmpty) {
            currentExecuteDelta = 0f
            queue.executeNext()
        }

    }

    fun render(batch: SpriteBatch, font: BitmapFont){
        font.draw(batch, "Wave #${firstWave.number}", 50f, Gdx.graphics.height - 50f)
        if (waiting){
            font.draw(batch, "Waiting for all attackers to be destroyed to start new wave...", 50f, Gdx.graphics.height - 100f)
        }
    }
}