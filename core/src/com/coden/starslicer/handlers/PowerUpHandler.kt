package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp

class PowerUpHandler(val spaceCraft: SpaceCraft) {

    val boosts = ArrayList<HPBoost>()


    fun updateAll() {
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

    fun renderAll(batch: SpriteBatch) {

    }

    fun updateInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            addHPBoost()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            useHPBoost()
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
}