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

class AttackerHandler {

    val maxMissiles = arrayListOf(20, 5, 20, 20) // 0 - missing, 1 - direct, 2 - orbiting , 3 - spiraling
    val maxNuclearBombs = arrayListOf(8, 0) //0 - missing, 1 - direct
    val maxMeteors = arrayListOf(17, 8, 2) // 0 - small, 1-medium, 2-large
    // maybe ratio amount can be applied to speed when difficulty is bigger

    var currentMissiles = arrayListOf(0, 0, 0, 0)
    var currentNuclearBombs = arrayListOf(0, 0)
    var currentMeteors = arrayListOf(0, 0, 0)

    val attackers = ArrayList<Attacker>()

    fun renderAll(batch: SpriteBatch) {
        for (attacker in attackers) {
            attacker.render(batch)
        }
    }

    fun updateAll(spaceCraft: SpaceCraft){
        val iterator = attackers.iterator()
        while (iterator.hasNext()) {
            val attacker = iterator.next()
            attacker.update()
            if (attacker.collisional) {
                updateCollision(spaceCraft, attacker)
            }

            if (attacker.isDead) {
                Gdx.app.log("attackers.update", "${attacker.name} is dead")
                increment(attacker.name, attacker.state, -1)
                entities.remove(attacker)
                iterator.remove()
            }
        }


    }

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

    fun updateCollision(spaceCraft: SpaceCraft, attacker: Attacker) {
        if (spaceCraft.isShielded) {
           if (spaceCraft.shieldCircle.contains(attacker.pos)) {
               attacker.kill()
           }
        } else if (spaceCraft.hitBox.overlaps(attacker.hitBox)){
            attacker.giveDamage(spaceCraft, attacker.damage)
            attacker.kill()
        }
    }

    fun spawnMissile(state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,3) else state

        if (currentMissiles[newState] >= maxMissiles[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val missile = Missile(spawnPoint, newState)
        increment(missile.name, newState, 1)
        attackers.add(missile)
    }

    fun spawnNuclearBomb(state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,1) else state

        if (currentNuclearBombs[newState] >= maxNuclearBombs[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val nuclearBomb = NuclearBomb(spawnPoint, newState)
        increment(nuclearBomb.name, newState, 1)
        attackers.add(nuclearBomb)
    }

    fun spawnMeteor(size: Int = -100,state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,1)*MathUtils.random(0,1) else state
        val newSize = if (size == -100) MathUtils.random(0,2) else size

        if (currentMeteors[newSize] >= maxMeteors[newSize]) return

        val spawnPoint = generateRandomSpawnPoint()
        val meteor = Meteor(spawnPoint, newState, newSize)
        increment(meteor.name, newSize, 1)
        attackers.add(meteor)
    }


    fun increment(name: Attacker.AttackerType, state: Int, value: Int) = when (name) {
        MISSILE -> currentMissiles[state] += value
        NUCLEAR_BOMB -> currentNuclearBombs[state] += value
        SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR -> currentMeteors[state] += value
    }


}