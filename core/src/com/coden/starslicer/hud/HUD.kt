package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Assets

class HUD(assets: Assets) {

    private var powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.75f, Gdx.graphics.height*0.05f, assets.powerUpAssets)

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()



    fun update(powerUps: Map<PowerUp.PowerUpType, Int>) {
        powerUpsBar.update(powerUps)
    }

    fun render() {
        batch.begin()

        powerUpsBar.render(batch)

        batch.end()


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        powerUpsBar.render(shapeRenderer)

        shapeRenderer.end()
    }

}


