package com.coden.starslicer.hud

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.util.sqRatio
import com.coden.starslicer.util.xRatio
import com.coden.starslicer.util.yRatio

class PowerUpsBar(val x: Float,val  y:Float, val data: EntityData, maxNumber: Int = 3) {
    // x center, y bottom

    private val size = data.powerUpIconAssets.width * xRatio

    private val marginX = 0.5f // ratio to size in which it has to be margined DO NOT APPLY XRATIO(YRATIO)
    private val marginY = 0.2f

    private val totalWidth = ((maxNumber+1)*marginX + maxNumber)*size
    private val totalHeight = size*(1+2*marginY)

    init {
        var i = marginX
        for (icon in data.powerUpIcons) {
            icon.initialize(x - totalWidth/2 + i * size, y + marginY*size, size)
            i += 1 + marginX
        }
    }

    fun update() {
        for (icon in data.powerUpIcons) {
            icon.amount = when (icon.type) {
                SHIELD    -> data.shields.size
                SHOCKWAVE -> data.shockWaves.size
                HPBOOST   -> data.boosts.size
            }
        }
    }


    fun render(batch: SpriteBatch) {
        for (icon in data.powerUpIcons) {
            icon.draw(batch)
        }
    }

    fun render(shapeRenderer: ShapeRenderer){
        for (icon in data.powerUpIcons){
            renderAmount(icon, shapeRenderer)
        }
    }

    private fun renderAmount(powerUpIcon: PowerUpIcon, shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(powerUpIcon.topright.x, powerUpIcon.topright.y, 13f* sqRatio)
    }

    fun debug(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x - totalWidth/2, y, totalWidth, totalHeight)
        for (icon in data.powerUpIcons) {
            shapeRenderer.rect(icon.pos.x, icon.pos.y, icon.width, icon.width)
        }
    }

}