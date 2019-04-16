package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.spawnCommands.SpawnContainer
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.entities.attackers.PowerUpContainer
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUp.Companion.hpboosts
import com.coden.starslicer.entities.powerups.PowerUp.Companion.shields
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.events.EventType
import com.coden.starslicer.events.Observer
import com.coden.starslicer.events.Subject
import com.coden.starslicer.events.SubjectAdapter
import com.coden.starslicer.gameplay.waveStates.WaveBeginState
import com.coden.starslicer.util.Log

class DifficultyController(val data: EntityData):SubjectAdapter() {
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

        val waveState = currentWave.currentState
        if (waveState is WaveBeginState){
            notify(EventType.START_GAME, waveState.time)
        }

    }

    fun render(batch: SpriteBatch, font: BitmapFont){
        font.draw(batch, "Wave #${currentWave.number}", 50f, Gdx.graphics.height - 50f)
        currentWave.render(batch, font)
    }

    fun adapt(){
        if (SpaceCraft.health < 15 && hpboosts.size == 0){
            if (MathUtils.randomBoolean(0.01f) && attackers.count { it is PowerUpContainer  && it.content == PowerUp.PowerUpType.HPBOOST} == 0){
                Log.info("SPAWNING BOOST", Log.LogType.DEBUG)
                queue.add(SpawnContainer(0, PowerUp.PowerUpType.HPBOOST))
            }
        }
        if (attackers.count{it is Missile} > 25 && shields.size == 0 ){
            if (MathUtils.randomBoolean(0.01f) && attackers.count{it is PowerUpContainer && it.content == PowerUp.PowerUpType.SHIELD} == 0){
                Log.info("SPAWNING SHIELD", Log.LogType.DEBUG)
                queue.add(SpawnContainer(0, PowerUp.PowerUpType.SHIELD))
            }

        }
    }
}