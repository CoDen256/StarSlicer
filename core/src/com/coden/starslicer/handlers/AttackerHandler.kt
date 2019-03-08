package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.BladePoint
import com.coden.starslicer.entities.*
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
                iterator.remove()
            }
        }


    }

    fun updateInput() {

        if (Gdx.input.justTouched()) {
            when{
                Gdx.input.x < centerX && Gdx.input.y < centerY -> spawnMissile()
                Gdx.input.x > centerX && Gdx.input.y < centerY -> spawnNuclearBomb()
                Gdx.input.x < centerX && Gdx.input.y > centerY -> spawnMeteor(1)
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

    fun updateSlicing(blades: ArrayList<BladePoint>){
        if (!(blades[0].active || blades[1].active)){
            return
        }
        for (attacker in attackers) {
            if (blades[0].isSlicing(attacker.hitBox) || blades[1].isSlicing(attacker.hitBox)) {
                attacker.kill()
            }
        }
    }

    fun updateCollision(spaceCraft: SpaceCraft, attacker: Attacker) {
        if (spaceCraft.hitBox.overlaps(attacker.hitBox)){
            attacker.kill()
        }
    }

    fun spawnMissile(state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,3) else state

        if (currentMissiles[newState] >= maxMissiles[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val missile = Missile(spawnPoint, newState)
        increment("missile", newState, 1)
        attackers.add(missile)
    }

    fun spawnNuclearBomb(state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,1) else state

        if (currentNuclearBombs[newState] >= maxNuclearBombs[newState]) return

        val spawnPoint = generateRandomSpawnPoint()
        val nuclearBomb = NuclearBomb(spawnPoint, newState)
        increment("nuclearbomb", newState, 1)
        attackers.add(nuclearBomb)
    }

    fun spawnMeteor(size: Int = -100,state: Int = -100) {
        val newState = if (state == -100) MathUtils.random(0,1)*MathUtils.random(0,1) else state
        val newSize = if (size == -100) MathUtils.random(0,3) else size

        if (currentMeteors[newSize] >= maxMeteors[newSize]) return

        val spawnPoint = generateRandomSpawnPoint()
        val meteor = Meteor(spawnPoint, newState, newSize)
        increment(meteor.name, newSize, 1)
        attackers.add(meteor)
    }


    fun increment(name: String, state: Int, value: Int) = when (name) {
        "missile" -> currentMissiles[state] += value
        "nuclearbomb" -> currentNuclearBombs[state] += value
        "smallMeteor", "mediumMeteor", "largeMeteor" -> currentMeteors[state] += value
        else -> Gdx.app.error("incrementation", "no such attacker")
    }


}