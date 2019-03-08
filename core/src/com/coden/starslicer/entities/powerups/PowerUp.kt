package com.coden.starslicer.entities.powerups

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Entity
import com.coden.starslicer.util.textureMap

enum class PowerUp{
    SHIELD {
        override fun update() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    HPBOOST {
        override fun update() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    SHOCKWAVE {
        override fun update() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    };

    var isDead = false
    var active = false

    abstract fun update()

    open fun kill() {
        isDead = true
    }

}