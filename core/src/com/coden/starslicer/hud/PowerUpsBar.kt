package com.coden.starslicer.hud

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.hud.HUDElements.UIObject
import com.coden.starslicer.util.assets.AssetLocator
import com.coden.starslicer.util.sqRatio
import com.coden.starslicer.util.xRatio

class PowerUpsBar(val x: Float,val  y:Float, maxNumber: Int = 3): UIObject {
    // x center, y bottom

    val assets = AssetLocator.getPowerUpAssets()
    val powerUpIcons = assets.icons


    override var isDead = false
    private val size = assets.width * xRatio

    private val marginX = 0.5f //TODO: ratio to size in which it has to be margined DO NOT APPLY XRATIO(YRATIO)
    private val marginY = 0.2f

    private val totalWidth = ((maxNumber+1)*marginX + maxNumber)*size
    private val totalHeight = size*(1+2*marginY)

    init {
        var i = marginX
        for (icon in powerUpIcons) {
            icon.initialize(x - totalWidth/2 + i * size, y + marginY*size, size)
            i += 1 + marginX
        }
    }

    override fun update() {
    }


    override fun render(batch: SpriteBatch) {
        for (icon in powerUpIcons) {
            icon.draw(batch)
        }
    }

    override fun render(shapeRenderer: ShapeRenderer){
        for (icon in powerUpIcons){
            renderAmount(icon, shapeRenderer)
        }
    }

    fun increaseAmount(ability:PowerUp.PowerUpType){
        powerUpIcons.find{it.type == ability}!!.amount += 1
    }

    fun decreaseAmount(ability: PowerUp.PowerUpType){
        powerUpIcons.find{it.type == ability}!!.amount -= 1
    }

    private fun renderAmount(powerUpIcon: PowerUpIcon, shapeRenderer: ShapeRenderer) {
        shapeRenderer.circle(powerUpIcon.topright.x, powerUpIcon.topright.y, 13f* sqRatio)
    }

    fun debug(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x - totalWidth/2, y, totalWidth, totalHeight)
        for (icon in powerUpIcons) {
            shapeRenderer.rect(icon.pos.x, icon.pos.y, icon.width, icon.width)
        }
    }

}