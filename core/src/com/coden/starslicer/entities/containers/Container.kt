package com.coden.starslicer.entities.containers

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.entities.attackers.Attacker

class Container : Attacker() {

    enum class ContainerType{
        SATELLITE, POWERUP_CONTAINER
    }
    override val lifeSpan: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override var health: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var damage: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override val movementSpeed: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val initialPos: Vector2
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override var pos: Vector2
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override val collisional: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val state: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override var hitBox: Rectangle
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var roundHitBox: Circle
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override val spriteTexture: TextureRegion?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val sprite: Sprite
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val maxHealth: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


}