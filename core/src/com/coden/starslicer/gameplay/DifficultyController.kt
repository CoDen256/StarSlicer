package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.gameplay.commands.CommandQueue
import com.coden.starslicer.gameplay.commands.spawnCommands.SpawnContainer
import com.coden.starslicer.gameObjects.attackers.Attacker.Companion.attackers
import com.coden.starslicer.gameObjects.attackers.Missile
import com.coden.starslicer.gameObjects.attackers.PowerUpContainer
import com.coden.starslicer.gameObjects.powerups.PowerUp

import com.coden.starslicer.gameplay.events.EventType
import com.coden.starslicer.gameplay.events.SubjectAdapter
import com.coden.starslicer.gameObjects.powerups.PowerUpType
import com.coden.starslicer.gameplay.wave.Wave
import com.coden.starslicer.gameplay.wave.WaveBeginState
import com.coden.starslicer.util.Locator

class DifficultyController:SubjectAdapter() {
    val queue = CommandQueue()
    val currentWave = Wave(5, queue)

    val executeDelta = 0.2f
    var currentExecuteDelta = 0.0f

    init {
        addObserver(Locator.getUI())
    }

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
        if (Locator.spaceCraft.health < 15 && PowerUp.isEmpty(PowerUpType.HPBOOST)){
            if (MathUtils.randomBoolean(0.01f) && attackers.count { it is PowerUpContainer  && it.content == PowerUpType.HPBOOST} == 0){
                queue.add(SpawnContainer(0, PowerUpType.HPBOOST))
            }
        }
        if (attackers.count{it is Missile} > 25 && PowerUp.isEmpty(PowerUpType.SHIELD)){
            if (MathUtils.randomBoolean(0.01f) && attackers.count{it is PowerUpContainer && it.content == PowerUpType.SHIELD} == 0){
                queue.add(SpawnContainer(0, PowerUpType.SHIELD))
            }

        }
    }
}