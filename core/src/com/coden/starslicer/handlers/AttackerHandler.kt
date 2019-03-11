package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.BladePoint
import com.coden.starslicer.entities.*
import com.coden.starslicer.entities.Entity.Companion.entities
import com.coden.starslicer.entities.attackers.*
import com.coden.starslicer.entities.attackers.Attacker.AttackerType.*
import com.coden.starslicer.util.centerX
import com.coden.starslicer.util.centerY
import com.coden.starslicer.util.generateRandomSpawnPoint

class AttackerHandler(private val data: EntityData) {

    fun renderAll(batch: SpriteBatch) {
        for (attacker in data.attackers) {
            attacker.render(batch)
        }
    }

    fun updateAll(){
        val iterator = data.attackers.iterator()
        while (iterator.hasNext()) {
            val attacker = iterator.next()
            attacker.update()
            updateCollision(attacker)

            if (attacker.isDead) {
                Gdx.app.log("attackers.update", "${attacker.name} is dead")
                decrement(attacker.name, attacker.state)
                entities.remove(attacker) // Removing from all entities
                iterator.remove()
            }
        }
    }

    fun updateCollision(attacker: Attacker) {
        if (data.spaceCraft.isShielded) {
            if (data.spaceCraft.shieldCircle.overlaps(attacker.roundHitBox)) {
                attacker.kill()
            }
        } else if (data.spaceCraft.hitBox.overlaps(attacker.hitBox) && attacker.collisional){
            attacker.giveDamage(data.spaceCraft, attacker.damage)
            attacker.kill()
        }
    }

    private fun decrement(name: Attacker.AttackerType, index: Int) = when (name) {
        MISSILE -> data.currentMissiles[index] --
        NUCLEAR_BOMB -> data.currentNuclearBombs[index] --
        SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR -> data.currentMeteors[index] --
    }


    // TODO: Move spawning to ProgressClass
    fun debugSpawning() {

        if (Gdx.input.justTouched()) {
            when{
                Gdx.input.x < centerX && Gdx.input.y < centerY -> spawnMissile()
                Gdx.input.x > centerX && Gdx.input.y < centerY -> spawnNuclearBomb()
                Gdx.input.x < centerX && Gdx.input.y > centerY -> spawnMeteor()
            }
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
        }
    }

    private fun spawnMissile(state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,3) else state

        if (data.currentMissiles[newState] >= data.maxMissiles[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val missile = Missile(spawnPoint, newState)
        increment(missile.name, newState)
        data.attackers.add(missile)
    }

    private fun spawnNuclearBomb(state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,1) else state

        if (data.currentNuclearBombs[newState] >= data.maxNuclearBombs[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val nuclearBomb = NuclearBomb(spawnPoint, newState)
        increment(nuclearBomb.name, newState)
        data.attackers.add(nuclearBomb)
    }

    private fun spawnMeteor(size: Int = -100,state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,1)*MathUtils.random(0,1) else state
        val newSize = if (size == -100) MathUtils.random(0,2) else size

        if (data.currentMeteors[newSize] >= data.maxMeteors[newSize]) return

        val spawnPoint = generateRandomSpawnPoint()
        val meteor = Meteor(spawnPoint, newState, newSize)
        increment(meteor.name, newSize)
        data.attackers.add(meteor)
    }

    private fun increment(name: Attacker.AttackerType, index: Int) = when (name) {
        MISSILE -> data.currentMissiles[index] ++
        NUCLEAR_BOMB -> data.currentNuclearBombs[index] ++
        SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR -> data.currentMeteors[index] ++
    }




}