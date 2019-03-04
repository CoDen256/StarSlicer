package com.mygdx.util.swipe

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.mygdx.util.swipe.simplify.ResolverRadialChaikin

class Blade(val pointer: Int){

    lateinit var  inputPoints: FixedList<Vector2>
    lateinit var simplified: Array<Vector2>

    var timePassed = 0f
    var pointLifespan = HashMap<Vector2, Float>()

    var lastPoint = Vector2()
    var isDrawing = false

    fun create(maxInputPoints: Int) {
        inputPoints = FixedList(maxInputPoints, Vector2::class.java)
        simplified = Array(true, maxInputPoints, Vector2::class.java)
    }

    fun resolve(simplifier: ResolverRadialChaikin) {
        simplifier.resolve(inputPoints, simplified)
    }

    fun onTouchDown(screenX: Int, screenY: Int) {
        isDrawing = true

        //clear points
        inputPoints.clear()

        //starting point
        lastPoint = Vector2(screenX.toFloat(), (Gdx.graphics.height - screenY).toFloat())
        inputPoints.insert(lastPoint)

        pointLifespan[lastPoint] = timePassed
    }

    fun onTouchUp(screenX: Int, screenY: Int) {
        inputPoints.clear()
        timePassed = 0f

        isDrawing = false
    }

    fun onTouchDragged(screenX: Int, screenY: Int, minDistance: Int, initialDistance:Int):Boolean {

        isDrawing = true

        val v = Vector2(screenX.toFloat(), (Gdx.graphics.height - screenY).toFloat())

        //calc length
        val dx = v.x - lastPoint.x
        val dy = v.y - lastPoint.y
        val len = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        //TODO: use minDistanceSq

        //if we are under required distance
        if (len < minDistance && (inputPoints.size > 1 || len < initialDistance))
            return false

        //add new point
        inputPoints.insert(v)

        lastPoint = v

        pointLifespan[lastPoint] = timePassed
        return true

    }

    fun checkLife(maxLifeSpan: Float) {
        for (i in 0 until inputPoints.size) {
            if (inputPoints[i] != null && i > 2) {
                val life = pointLifespan[inputPoints[i]]
                if (timePassed - life!!.toFloat() >= maxLifeSpan) {
                    inputPoints[i] = null
                }
            }
        }
    }


}
