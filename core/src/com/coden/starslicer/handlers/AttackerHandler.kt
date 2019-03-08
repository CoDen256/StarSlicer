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

    val maxMissiles = arrayListOf<Int>() // 0 - missing, 1 - direct, 2 - orbiting , 3 - spiraling
    val maxNuclearBombs = arrayListOf<Int>() //0 - missing, 1 - direct
    val maxMeteors = arrayListOf<Int>() //
    // maybe ratio amount can be applied to speed when difficulty is bigger

    var currentMissiles = 0
    var currentNuclearBombs = 0
    var currentMeteors = 0

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
            Gdx.input.isKeyJustPressed(Input.Keys.B) -> spawnNuclearBomb()
            Gdx.input.isKeyJustPressed(Input.Keys.M) -> spawnMeteor(1)
            Gdx.input.isKeyJustPressed(Input.Keys.COMMA) -> spawnMeteor(2)
            Gdx.input.isKeyJustPressed(Input.Keys.PERIOD) -> spawnMeteor(3)
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
        //if (currentMissiles >= maxMissiles) {
        //    return
        //}
        val spawnPoint = generateRandomSpawnPoint()
        val missile = Missile(spawnPoint, if (state == -100) MathUtils.random(0,3) else state)
        increment("missile", 1)
        attackers.add(missile)
    }

    fun spawnNuclearBomb(state: Int = 0) {
        //if (currentNuclearBombs >= maxNuclearBombs) {
        //    return
        //}
        val spawnPoint = generateRandomSpawnPoint()
        val nuclearBomb = NuclearBomb(spawnPoint, state)
        increment("nuclearbomb", 1)
        attackers.add(nuclearBomb)
    }

    fun spawnMeteor(size: Int,state: Int = -100) {
        //if (currentMeteors >= maxMeteors) {
        //    return
        //}
        val spawnPoint = generateRandomSpawnPoint()
        val meteor = Meteor(spawnPoint, if (state == -100) MathUtils.random(0,1)*MathUtils.random(0,1)*MathUtils.random(0,1) else state, size)
        increment(meteor.name, 1)
        attackers.add(meteor)
    }


    fun increment(name: String, value: Int) = when (name) {
        "missile" -> currentMissiles += value
        "nuclearbomb" -> currentNuclearBombs += value
        "smallMeteor", "mediumMeteor", "largeMeteor" -> currentMeteors += value
        else -> Gdx.app.error("incrementation", "no such attacker")
    }


}