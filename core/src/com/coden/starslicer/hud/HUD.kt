package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.coden.starslicer.BladePoint

import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Assets
import com.coden.util.swipe.Blade

class HUD(assets: Assets) {

    val powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.715f, Gdx.graphics.height*0.05f, assets.powerUpAssets) // TODO: Move HUD to the top,
                                                                                                              // since its uncomfortable to slice ther

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()



    fun update() {
        powerUpsBar.update()

    }

    fun updateInput() {

    }

    fun render() {
        batch.begin()

        powerUpsBar.render(batch)

        batch.end()


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        powerUpsBar.render(shapeRenderer)

        //debug(shapeRenderer)

        shapeRenderer.end()
    }

    fun debug(shapeRenderer: ShapeRenderer) {
        powerUpsBar.debug(shapeRenderer)
    }

}


