package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.BladePoint
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUp.Companion.powerUps
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.util.Assets
import com.coden.starslicer.util.sqRatio
import com.coden.starslicer.util.xRatio

class PowerUpsBar(val x: Float,val  y:Float, val assets: Assets.PowerUpAssets, maxNumber: Int = 3) {

    val size = 75 * sqRatio

    val width = assets.width * sqRatio
    val height = assets.height * sqRatio


    val icons = mapOf(
                SHIELD to PowerUpIcon(x+(0.2f * width), y+0.2f*height, width, height, SHIELD),
                HPBOOST to PowerUpIcon(x+(1.4f * width), y+0.2f*height, width, height, HPBOOST),
                SHOCKWAVE to PowerUpIcon(x+(2.6f * width), y+0.2f*height, width, height, SHOCKWAVE)
        )


    val totalWidth = 0.2f*size*(6 * maxNumber + 1)
    val totalHeight = height*1.4f

    fun update() {
        for ( (type, icon) in icons) {
            icon.amount = powerUps.count{p -> p.type == type }
        }
    }

    fun updateInput() {
        if (Gdx.input.justTouched()) {
            for (powerup in icons.values) {
                if (powerup.hitBox.contains(Gdx.input.x * 1f, Gdx.graphics.height-Gdx.input.y * 1f)) {

                }
            }
        }

    }

    fun render(batch: SpriteBatch) {
        for (icon in icons.values) {
            icon.draw(batch, assets)
        }
    }

    fun render(shapeRenderer: ShapeRenderer){
        for (icon in icons.values){
            renderAmount(icon, shapeRenderer)
        }
    }

    fun renderAmount(powerUpIcon: PowerUpIcon, shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(powerUpIcon.topright.x, powerUpIcon.topright.y, 13f* sqRatio)
    }

    fun debug(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x, y, totalWidth, totalHeight)
        for (icon in icons.values) {
            shapeRenderer.rect(icon.x, icon.y, icon.width, icon.height)
        }
    }

}