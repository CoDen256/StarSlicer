package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave

class PowerUpHandler(val spaceCraft: SpaceCraft) {

    val boosts = ArrayList<HPBoost>()
    val shields = ArrayList<Shield>()
    val shockWaves = ArrayList<ShockWave>()


    fun updateAll() {
        updateHPBoost()
        updateShield()
        updateShockWaves()
    }

    fun renderAll(batch: SpriteBatch) {

    }

    fun updateInput() {
        when  {
            Gdx.input.isKeyJustPressed(Input.Keys.H) -> addHPBoost()
            Gdx.input.isKeyJustPressed(Input.Keys.J) -> useHPBoost()

            Gdx.input.isKeyJustPressed(Input.Keys.S) -> addShield()
            Gdx.input.isKeyJustPressed(Input.Keys.D) -> useShield()

            Gdx.input.isKeyJustPressed(Input.Keys.W) -> addShockWave()
            Gdx.input.isKeyJustPressed(Input.Keys.E) -> useShockWave()
        }

    }

    fun addHPBoost(){
        boosts.add(HPBoost())
    }

    fun useHPBoost() {
        if (!boosts.isEmpty()){
            boosts[0].applyEffect(spaceCraft)
            Gdx.app.log("hpBoost", "applied")
        }
    }

    fun updateHPBoost() {
        val iterator = boosts.iterator()
        while (iterator.hasNext()) {
            val boost = iterator.next()
            boost.update()
            if (boost.isDead) {
                Gdx.app.log("hpBoost", "is dead")
                iterator.remove()
            }
        }
    }

    fun addShield() {
        shields.add(Shield(spaceCraft))
    }

    fun useShield() {
        if (!shields.isEmpty() && !spaceCraft.isShielded) {
            shields[0].applyEffect()
            Gdx.app.log("shield", "applied")
        }
    }

    fun updateShield() {
        val iterator = shields.iterator()
        while (iterator.hasNext()) {
            val shield = iterator.next()
            if (shield.active){
                shield.update()

                if (shield.isDead) {
                    Gdx.app.log("shield", "is dead")
                    iterator.remove()
                }
            }
        }
    }

    fun addShockWave() {
        shockWaves.add(ShockWave())
    }

    fun useShockWave() {
        if (!shockWaves.isEmpty()) {
            for (shockWave in shockWaves) {
                if (!shockWave.active) {
                    shockWave.applyEffect()
                    return
                }
            }
        }
    }

    fun updateShockWaves() {
        val iterator = shockWaves.iterator()
        while (iterator.hasNext()) {
            val shockWave = iterator.next()
            if (shockWave.active){
                shockWave.update()

                if (shockWave.isDead) {
                    Gdx.app.log("shockWave", "is dead")
                    iterator.remove()
                }
            }
        }
    }

}