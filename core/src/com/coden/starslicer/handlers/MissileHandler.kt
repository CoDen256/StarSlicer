package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.entities.Missile
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.spawnRandomMissle

class MissileHandler {
    val missiles = ArrayList<Missile>()

    fun render(batch: SpriteBatch) {
        for (missile in missiles) {
            missile.render(batch)
        }
    }

    fun update(spaceCraft: SpaceCraft){
        val iterator = missiles.iterator()
        while (iterator.hasNext()) {

            val missile = iterator.next()
            missile.update()

            updateCollision(spaceCraft, missile)
            if (missile.isDead) {
                Gdx.app.log("update", "Missile is dead")
                iterator.remove()
            }
        }
    }

    fun updateCollision(spaceCraft: SpaceCraft,missile: Missile) {
        if (spaceCraft.hitBox.overlaps(missile.hitBox)){
            Gdx.app.log("missileHandler.updateCollision", "overlaps")
            missile.kill()
        }
    }

    fun updateSpawning() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            spawnRandomMissle(0, missiles)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            spawnRandomMissle(1, missiles)
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            spawnRandomMissle(2, missiles)
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            spawnRandomMissle(3, missiles)
        }
    }

    fun spawnSomeMissile() {
        spawnRandomMissle(MathUtils.random(0, 3), missiles)
    }
}