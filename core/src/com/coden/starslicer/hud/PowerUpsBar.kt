package com.coden.starslicer.hud

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Assets

class PowerUpsBar(pos: Vector2, powerUpAssets: Assets.PowerUpAssets, maxNumber: Int = 3) {

    var shieldIcon = PowerUpIcon(pos, powerUpAssets.getTexture(PowerUp.PowerUpType.SHIELD))
    var boostIcon = PowerUpIcon(pos.cpy().add(75f,0f), powerUpAssets.getTexture(PowerUp.PowerUpType.HPBOOST))
    var shockwaveIcon = PowerUpIcon(pos.cpy().add(150f,0f), powerUpAssets.getTexture(PowerUp.PowerUpType.SHOCKWAVE))

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


}