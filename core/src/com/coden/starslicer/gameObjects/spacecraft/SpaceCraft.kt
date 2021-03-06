package com.coden.starslicer.gameObjects.spacecraft

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.graphics.Animator
import com.coden.starslicer.gameObjects.DamageGiver
import com.coden.starslicer.gameObjects.DamageTaker
import com.coden.starslicer.util.*
import com.coden.starslicer.assets.AssetLocator
import com.coden.starslicer.assets.loaders.BladeLoader
import com.coden.starslicer.assets.loaders.SpaceCraftLoader
import com.coden.util.swipe.SwipeHandler

class SpaceCraft private constructor() : DamageTaker, DamageGiver {
    companion object {
        private var spaceCraft: SpaceCraft? = null
        fun create(): SpaceCraft{
            return spaceCraft ?: SpaceCraft()
        }
    }
    override var isDead = false

    private val snapshot = SpaceCraftLoader().load()

    val xProportion = snapshot.xProportion
    val yProportion = snapshot.yProportion

    override val damage = snapshot.damage // Body Damage

    // Health
    override val maxHealth = snapshot.maxHealth
    override var health = maxHealth

    // Positioning
    override var pos
        get() = Vector2(x, y)
        set(value) {}

    // ANIMATION // TODO: via Assets
    val animator = Animator("entities/animation/spacecraft/spacecraft_anim.png", 3, 7, 0.025f)
    //val spaceCraftTexture = AssetLocator.getSpaceCraftAssets().getTexture()

    override val width = xRatio * animator.frameWidth
    override val height = yRatio * animator.frameHeight

    val x = Gdx.graphics.width * xProportion
    val y = Gdx.graphics.height * yProportion

    val spaceCraftCenterX = x - width /2
    val spaceCraftCenterY = y - height /2

    // Sprite
    override val hitBox = Rectangle(spaceCraftCenterX, spaceCraftCenterY, width, height)
    override val hitSphere = Circle(x, y, minOf(height, width)/2)

    // Shield
    var isShielded = false
    var shieldRadius = 0f

    val shieldCircle: Circle
        get() = Circle(x, y, shieldRadius)

    //Blades
    //private var blades = arrayOf(BladePoint(0), BladePoint(1))
    private val bladeLoader = BladeLoader()
    private var blades = arrayOf(bladeLoader.load(0), bladeLoader.load(1))

    val firstBlade = blades[0]
    val secondBlade = blades[1]


    override fun toString()= "SPACECRAFT\nProportions:$xProportion, $yProportion\nDamage:$damage\nHealth:$maxHealth\n CurrentHealth:$health"


    fun render(batch: SpriteBatch) {
        //TODO: Animation Rendering has to be separate
        animator.render(batch, spaceCraftCenterX, spaceCraftCenterY, width, height)
    }

    fun update(swipe: SwipeHandler) {

        for (blade in blades) {
            blade.update(swipe)
            blade.updateActivation()
        }


    }



    fun dispose() {
        AssetLocator.getSpaceCraftAssets().dispose()
    }


}