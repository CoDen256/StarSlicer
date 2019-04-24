package com.coden.starslicer.util.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.hud.PowerUpIcon
import com.coden.starslicer.util.Log

class PowerUpAssets(atlas: TextureAtlas){
    private val map = mapOf(
            PowerUp.PowerUpType.HPBOOST to atlas.findRegion("hpboost") as TextureRegion,
            PowerUp.PowerUpType.SHIELD to atlas.findRegion("shield") as TextureRegion,
            PowerUp.PowerUpType.SHOCKWAVE to atlas.findRegion("shockwave") as TextureRegion
    )

    val icons = arrayListOf(
            PowerUpIcon(PowerUp.PowerUpType.SHIELD, getTexture(PowerUp.PowerUpType.SHIELD)),
            PowerUpIcon(PowerUp.PowerUpType.HPBOOST, getTexture(PowerUp.PowerUpType.HPBOOST)),
            PowerUpIcon(PowerUp.PowerUpType.SHOCKWAVE, getTexture(PowerUp.PowerUpType.SHOCKWAVE))
    )

    init { Log.info("PowerUpAssets textureMap created", Log.LogType.ASSETS) }
    fun getTexture(type: PowerUp.PowerUpType): TextureRegion? = map[type]

    val width = map[PowerUp.PowerUpType.SHIELD]!!.regionWidth
    val height = map[PowerUp.PowerUpType.SHIELD]!!.regionHeight

}
class AttackerAssets(atlas: TextureAtlas){
    private val textureMap = mapOf(
            AttackerType.MISSILE to atlas.findRegion("missile") as TextureRegion,
            AttackerType.NUCLEAR_BOMB to atlas.findRegion("nuclearbomb") as TextureRegion,
            AttackerType.SMALL_METEOR to atlas.findRegion("smallMeteor") as TextureRegion,
            AttackerType.MEDIUM_METEOR to atlas.findRegion("mediumMeteor") as TextureRegion,
            AttackerType.LARGE_METEOR to atlas.findRegion("largeMeteor") as TextureRegion,
            AttackerType.SATELLITE to atlas.findRegion("satellite") as TextureRegion,
            AttackerType.POWERUP_CONTAINER to atlas.findRegion("container") as TextureRegion
    )
    init { Log.info("AttackerAssets  textureMap created", Log.LogType.ASSETS) }
    fun getTexture(type: AttackerType): TextureRegion? = textureMap[type]
}

class SwipeAssets(private val textureMap: List<Texture>){
    companion object { var max = 4 }

    init { Log.info("SwipeAssets textureMap created", Log.LogType.ASSETS) }

    fun getTexture(num: Int): Texture =  textureMap[textureMap.lastIndex - num]
}

class SpaceCraftAssets(private val texture: Texture) {

    init { Log.info("SpaceCraftAssets texture created", Log.LogType.ASSETS) }

    fun getTexture(): TextureRegion = TextureRegion(texture)

    fun dispose() {
        Log.info("SpaceCraftAssets disposing", Log.LogType.ASSETS)
        texture.dispose()
    }
}