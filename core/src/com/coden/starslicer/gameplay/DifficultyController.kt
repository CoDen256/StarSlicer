package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.commands.CommandQueue
import com.coden.starslicer.commands.spawnCommands.SpawnContainer
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.entities.attackers.PowerUpContainer
import com.coden.starslicer.entities.powerups.PowerUp

import com.coden.starslicer.events.EventType
import com.coden.starslicer.events.SubjectAdapter
import com.coden.starslicer.gameplay.waveStates.WaveBeginState
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
        if (Locator.spaceCraft.health < 15 && PowerUp.isEmpty(PowerUp.PowerUpType.HPBOOST)){
            if (MathUtils.randomBoolean(0.01f) && attackers.count { it is PowerUpContainer  && it.content == PowerUp.PowerUpType.HPBOOST} == 0){
                queue.add(SpawnContainer(0, PowerUp.PowerUpType.HPBOOST))
            }
        }
        if (attackers.count{it is Missile} > 25 && PowerUp.isEmpty(PowerUp.PowerUpType.SHIELD)){
            if (MathUtils.randomBoolean(0.01f) && attackers.count{it is PowerUpContainer && it.content == PowerUp.PowerUpType.SHIELD} == 0){
                queue.add(SpawnContainer(0, PowerUp.PowerUpType.SHIELD))
            }

        }
    }
}