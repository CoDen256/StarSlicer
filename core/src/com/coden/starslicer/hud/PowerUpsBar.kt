package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Assets
import com.coden.starslicer.util.sqRatio
import com.coden.starslicer.util.xRatio

class PowerUpsBar(val x: Float,val  y:Float,val powerUpAssets: Assets.PowerUpAssets, maxNumber: Int = 3) {

    val size = 75 * sqRatio
    init {
        Gdx.app.log("powerupsbar", "$size")
    }

    var shieldIcon = PowerUpIcon(x+(0.2f * size), y+0.2f*size, powerUpAssets.getTexture(PowerUp.PowerUpType.SHIELD))
    var boostIcon = PowerUpIcon(x+(1.4f * size), y+0.2f*size, powerUpAssets.getTexture(PowerUp.PowerUpType.HPBOOST))
    var shockwaveIcon = PowerUpIcon(x+(2.6f * size), y+0.2f*size, powerUpAssets.getTexture(PowerUp.PowerUpType.SHOCKWAVE))

    val width = 0.2f*shieldIcon.width*(6 * maxNumber + 1)

    fun update(powerups: Map<PowerUp.PowerUpType, Int>) {
        shieldIcon.amount = powerups[PowerUp.PowerUpType.SHIELD]
        boostIcon.amount = powerups[PowerUp.PowerUpType.SHIELD]
        shockwaveIcon.amount = powerups[PowerUp.PowerUpType.SHIELD]
    }

    fun render(batch: SpriteBatch) {
        shieldIcon.draw(batch)
        boostIcon.draw(batch)
        shockwaveIcon.draw(batch)
    }

    fun render(shapeRenderer: ShapeRenderer){
        shapeRenderer.rect(x, y, width, shieldIcon.height*1.4f)
        renderAmount(shieldIcon, shapeRenderer)
        renderAmount(boostIcon, shapeRenderer)
        renderAmount(shockwaveIcon, shapeRenderer)
    }

    fun renderAmount(powerUpIcon: PowerUpIcon, shapeRenderer: ShapeRenderer) {

    }

}