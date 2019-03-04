package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Missile
import kotlin.collections.ArrayList

fun spawnRandomMissle(state: Int, missiles: ArrayList<Missile>) {
    val angle = MathUtils.random(0, 360).toFloat()
    val radius = dist2(Gdx.graphics.width/2f, Gdx.graphics.height/2f).toFloat()+50
    val vector = Vector2(1f, 0f).setLength(radius).rotate(angle).add(Gdx.graphics.width/2f, Gdx.graphics.height/2f)

    Gdx.app.log("spawnRandomMissile", "Missle: ${vector.x} ${vector.y} - State: $state")
    missiles.add(Missile(vector, state))

}

