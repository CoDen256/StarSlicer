package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Assets

class HUD(assets: Assets) {

    private var powerUpsBar = PowerUpsBar(Vector2(Gdx.graphics.width*0.75f, Gdx.graphics.height*0.2f), assets.powerUpAssets)

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()


    init {

    }


    fun update(powerups: Map<PowerUp.PowerUpType, Int>) {
        powerUpsBar.update(powerups)
    }

    fun render() {
        batch.begin()

        powerUpsBar.render(batch)

        batch.end()


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        shapeRenderer.end()
    }

    fun dispose() {

    }
}


