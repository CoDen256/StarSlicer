package com.mygdx.util.swipe

import com.mygdx.util.swipe.simplify.ResolverRadialChaikin

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

class SwipeHandler(maxInputPoints: Int, val bladesNum: Int) : InputAdapter() {

    val blades = Array<Blade>()

    var maxLifeSpan = 0.25f

    /** The minimum distance between the first and second point in a drawn line.  */
    var initialDistance = 10

    /** The minimum distance between two points in a drawn line (starting at the second point).  */
    var minDistance = 20

    private val simplifier = ResolverRadialChaikin()

    init {
        for (i in 0 until bladesNum) {
            val newBlade = Blade(i)
            newBlade.create(maxInputPoints)
            blades.add(newBlade)
            resolve(i)
        }

    }

    fun path(num: Int): Array<Vector2> {
        resolve(num)
        return blades[num].simplified
    }

    /**
     * If the points are dirty, the line is simplified.
     */
    fun resolve(num: Int) = blades[num].resolve(simplifier)

    fun checkLife(num: Int) = blades[num].checkLife(maxLifeSpan)


    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer >= bladesNum) {
            return false
        }
        blades[pointer].onTouchDown(screenX, screenY)
        resolve(pointer)
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer >= bladesNum) {
            return false
        }
        //on release, the line is simplified
        blades[pointer].onTouchUp(screenX, screenY)
        resolve(pointer)
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean =
            when {
                (pointer >= bladesNum) -> false
                else -> blades[pointer].onTouchDragged(screenX, screenY, minDistance, initialDistance)
            }



}
