package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.util.spaceCraftCenter
import com.coden.starslicer.util.sqRatio
import com.coden.starslicer.util.xRatio
import com.coden.starslicer.util.yRatio

class NuclearBomb(override val initialPos: Vector2,
                 override val state: Int): Attacker(AttackerType.NUCLEAR_BOMB){
    // Life
    override val lifeSpan = 5f
    override val maxHealth = 20f
    override var health = maxHealth
    override var damage = 200f

    // Speed constants
    override val movementSpeed = 5 * sqRatio

    // Movement
    override var pos: Vector2 = initialPos
    private var velocity: Vector2

    // Sprite
    private val w = spriteTexture?.width!!/1.5f
    private val h = spriteTexture?.height!!/1.5f

    override val collisional: Boolean = false

    override var hitBox :Rectangle
        get() = Rectangle(pos.x - h * yRatio/2, pos.y - h * yRatio/2, h * yRatio, h * yRatio)
        set(value) {}

    override var roundHitBox: Circle
        get() = Circle(pos.x, pos.y, minOf(w, h)/2)
        set(value) {}

    init {
        velocity = when (state) {
            0 -> targetVector.rotate(MathUtils.random(8, 45)*MathUtils.randomSign().toFloat()).setLength(movementSpeed)
            1 -> initialPos.cpy().sub(spaceCraftCenter).scl(-1f).setLength(movementSpeed)
            else -> Vector2()
        }

        Gdx.app.log("nuclearBomb.init", "Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state")

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(velocity.angle()+90)
    }

    override fun update() {
        updateLife()
        pos = pos.add(velocity)
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)
    }

}