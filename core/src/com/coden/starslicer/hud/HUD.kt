package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.coden.starslicer.BladePoint

import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Assets
import com.coden.util.swipe.Blade

class HUD(assets: Assets) {

    val powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.75f, Gdx.graphics.height*0.05f, assets.powerUpAssets)

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()



    fun update(powerUps: Map<PowerUp.PowerUpType, Int>) {
        powerUpsBar.update(powerUps)
    }

    fun updateInput(blades: ArrayList<BladePoint>) {
        if (blades[0].active || blades[1].active){
            return
        }

        powerUpsBar.updateInput()

    }

    fun render() {
        batch.begin()

        powerUpsBar.render(batch)

        batch.end()


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        powerUpsBar.render(shapeRenderer)

        debug(shapeRenderer)

        shapeRenderer.end()
    }

    fun debug(shapeRenderer: ShapeRenderer) {
        powerUpsBar.debug(shapeRenderer)
    }

}


