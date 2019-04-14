package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUp.Companion.hpboosts
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.spaceCraftX

class DifficultyController(val data: EntityData) {
    val queue = CommandQueue(data)
    val currentWave = Wave(5, queue)

    val executeDelta = 0.2f
    var currentExecuteDelta = 0.0f

    fun update(){

        currentWave.update()

        adapt()

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

    fun adapt(){
        if (SpaceCraft.health < 15){
            if (MathUtils.random(1,100) == 100 && hpboosts.size == 0){
                queue.add(SpawnContainer(0, PowerUp.PowerUpType.HPBOOST))
            }
        }
    }
}