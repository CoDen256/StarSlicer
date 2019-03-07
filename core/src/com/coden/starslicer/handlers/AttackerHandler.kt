package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.BladePoint
import com.coden.starslicer.entities.Attacker
import com.coden.starslicer.entities.Missile
import com.coden.starslicer.entities.NuclearBomb
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.centerX
import com.coden.starslicer.util.generateRandomSpawnPoint
import com.coden.starslicer.util.spawnRandomMissle

class AttackerHandler {

    val maxMissiles = 17
    val maxNuclearBombs = 13

    var currentMissiles = 0
    var currentNuclearBombs = 0

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
                increment(attacker.name, -1)
                iterator.remove()
            }
        }


    }

    fun updateSpawning() {

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() < centerX) {
                spawnMissile()
            } else {
                spawnNuclearBomb()
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            spawnMissile(0)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            spawnMissile(1)
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            spawnMissile(2)
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            spawnMissile(3)
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            spawnNuclearBomb()
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
            Gdx.app.log("missileHandler.updateCollision", "overlaps")
            attacker.kill()
        }
    }

    fun spawnMissile(state: Int = -100) {
        if (currentMissiles >= maxMissiles) {
            return
        }
        val spawnPoint = generateRandomSpawnPoint()
        val missile = Missile(spawnPoint, if (state == -100) MathUtils.random(0,3) else state)
        increment("missile", 1)
        attackers.add(missile)
    }

    fun spawnNuclearBomb(state: Int = 0) {
        if (currentNuclearBombs >= maxNuclearBombs) {
            return
        }
        val spawnPoint = generateRandomSpawnPoint()
        val nuclearBomb = NuclearBomb(spawnPoint, state)
        increment("nuclearbomb", 1)
        attackers.add(nuclearBomb)
    }


    fun increment(name: String, value: Int) = when (name) {
        "missile" -> currentMissiles += value
        "nuclearbomb" -> currentNuclearBombs += value
        else -> Gdx.app.error("incrementation", "no such attacker")
    }


}