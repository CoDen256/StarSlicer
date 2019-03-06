package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.Missile
import com.coden.starslicer.entities.NuclerBomb
import kotlin.collections.ArrayList

fun spawnRandomMissle(state: Int, missiles: ArrayList<Missile>) {
    val angle = MathUtils.random(0, 360).toFloat()
    val radius = dist2(centerX, centerY)+50
    val vector = Vector2(1f, 0f).setLength(radius).rotate(angle).add(center)

    missiles.add(Missile(vector, state))

}

fun spawnRandomBomb(state: Int, bombs: ArrayList<NuclerBomb>) {
    val angle = MathUtils.random(0, 360).toFloat()
    val radius = dist2(centerX, centerY)+50
    val vector = Vector2(1f, 0f).setLength(radius).rotate(angle).add(center)

    bombs.add(NuclerBomb(vector, state))
}

