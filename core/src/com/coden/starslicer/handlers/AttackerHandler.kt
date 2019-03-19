package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.*
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.attackers.*
import com.coden.starslicer.entities.attackers.AttackerType.*
import com.coden.starslicer.entities.attackers.PowerUpContainer
import com.coden.starslicer.entities.attackers.Satellite
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.centerX
import com.coden.starslicer.util.centerY
import com.coden.starslicer.util.generateRandomSpawnPoint
import java.lang.IllegalArgumentException

class AttackerHandler(private val data: EntityData) {

    private val log = Logger("AttackerHandler", Logger.NONE)


    fun renderAll(batch: SpriteBatch) {
        for (attacker in data.attackers) {
            attacker.render(batch)
        }
    }

    fun updateAll() {
        val iterator = data.attackers.iterator()
        while (iterator.hasNext()) {
            val attacker = iterator.next()
            attacker.update()
            updateCollision(attacker)

            if (attacker.isDead) {
                log.info("${attacker.name} is dead")
                decrement(attacker.type, attacker.state)
                entities.remove(attacker) // Removing from all entities
                iterator.remove()
            }
        }
    }

    private fun updateCollision(attacker: Attacker) {
        if (SpaceCraft.isShielded) {
            if (SpaceCraft.shieldCircle.overlaps(attacker.hitCircle)) {
                if (attacker is NuclearBomb) attacker.damageAll()
                attacker.kill()
            }
        } else if (SpaceCraft.hitBox.overlaps(attacker.hitBox) && attacker.collisional) {
            attacker.giveDamage(SpaceCraft, attacker.damage)
            attacker.kill()
        }
    }

    private fun decrement(name: AttackerType?, index: Int) = when (name) {
        MISSILE -> data.currentMissiles[index]--
        NUCLEAR_BOMB -> data.currentNuclearBombs[index]--
        SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR -> data.currentMeteors[index]--
        POWERUP_CONTAINER -> data.currentPowerUpContainers[index]--
        SATELLITE -> data.currentSatellites[index] --
        else -> throw IllegalArgumentException()
    }


    // TODO: Move spawning to ProgressClass
    fun debugSpawning() {

        if (Gdx.input.justTouched()) {
            when {
                Gdx.input.x < centerX && Gdx.input.y < centerY -> spawnMissile()
                Gdx.input.x > centerX && Gdx.input.y < centerY -> spawnNuclearBomb()
                Gdx.input.x < centerX && Gdx.input.y > centerY -> spawnMeteor()
                Gdx.input.x > centerX && Gdx.input.y > centerY -> {
                    spawnSatellite(listOf(PowerUp.PowerUpType.HPBOOST,
                        PowerUp.PowerUpType.SHIELD,
                        PowerUp.PowerUpType.SHOCKWAVE)[MathUtils.random(0, 2)])
                    spawnPowerUpContainer(listOf(
                            PowerUp.PowerUpType.HPBOOST,
                            PowerUp.PowerUpType.SHIELD,
                            PowerUp.PowerUpType.SHOCKWAVE)[MathUtils.random(0, 2)])
            }}
        }

        when {
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_0) -> spawnMissile(0)
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) -> spawnMissile(1)
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) -> spawnMissile(2)
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) -> spawnMissile(3)
            Gdx.input.isKeyJustPressed(Input.Keys.B) -> spawnNuclearBomb(0)
            Gdx.input.isKeyJustPressed(Input.Keys.M) -> spawnMeteor(0)
            Gdx.input.isKeyJustPressed(Input.Keys.COMMA) -> spawnMeteor(1)
            Gdx.input.isKeyJustPressed(Input.Keys.PERIOD) -> spawnMeteor(2)
            Gdx.input.isKeyJustPressed(Input.Keys.A) -> spawnSatellite(listOf(PowerUp.PowerUpType.HPBOOST,
                    PowerUp.PowerUpType.SHIELD,
                    PowerUp.PowerUpType.SHOCKWAVE)[MathUtils.random(0, 2)])
            Gdx.input.isKeyJustPressed(Input.Keys.C) -> spawnPowerUpContainer(listOf(
                    PowerUp.PowerUpType.HPBOOST,
                    PowerUp.PowerUpType.SHIELD,
                    PowerUp.PowerUpType.SHOCKWAVE)[MathUtils.random(0, 2)])
        }
    }

    private fun spawnMissile(state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0, 3) else state

        if (data.currentMissiles[newState] >= data.maxMissiles[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val missile = Missile(spawnPoint, newState, data.attackerAssets)
        increment(missile.type, newState)
        data.attackers.add(missile)
    }

    private fun spawnNuclearBomb(state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0, 1) else state

        if (data.currentNuclearBombs[newState] >= data.maxNuclearBombs[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val nuclearBomb = NuclearBomb(spawnPoint, newState, data.attackerAssets)
        increment(nuclearBomb.type, newState)
        data.attackers.add(nuclearBomb)
    }

    private fun spawnMeteor(size: Int = -100, state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0, 1) * MathUtils.random(0, 1) else state
        val newSize = if (size == -100) MathUtils.random(0, 2) else size

        if (data.currentMeteors[newSize] >= data.maxMeteors[newSize]) return

        val spawnPoint = generateRandomSpawnPoint()
        val meteor = Meteor(spawnPoint, newState, newSize, data.attackerAssets)
        increment(meteor.type, newSize)
        data.attackers.add(meteor)
    }

    private fun spawnPowerUpContainer(type: PowerUp.PowerUpType, state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0, 0) else state

        if (data.currentPowerUpContainers[newState] >= data.maxPowerUpContainers[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val powerUpContainer = PowerUpContainer(spawnPoint, newState, type, data.attackerAssets)
        increment(powerUpContainer.type, newState)
        data.attackers.add(powerUpContainer)
    }

    fun spawnSatellite(type: PowerUp.PowerUpType, state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0, 1) else state

        if (data.currentSatellites[newState] >= data.maxSatellites[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val satellite = Satellite(spawnPoint, newState, type, data.attackerAssets)
        increment(satellite.type, newState)
        data.attackers.add(satellite)
    }

    private fun increment(name: AttackerType?, index: Int) = when (name) {
        MISSILE -> data.currentMissiles[index]++
        NUCLEAR_BOMB -> data.currentNuclearBombs[index]++
        SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR -> data.currentMeteors[index]++
        POWERUP_CONTAINER -> data.currentPowerUpContainers[index]++
        SATELLITE -> data.currentSatellites[index] ++
        else -> throw IllegalArgumentException()
    }
}
