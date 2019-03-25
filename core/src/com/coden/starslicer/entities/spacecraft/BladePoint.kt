package com.coden.starslicer.entities.spacecraft

import com.badlogic.gdx.math.Rectangle
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.util.sqRatio
import com.coden.util.swipe.SwipeHandler

class BladePoint: DamageGiver {

    // Seializable
    private val pointer : Int? = null
    private val detectionRatio : Float? = null
    private val cutRatio : Int? = null
    private val firstIgnored : Int? = null
    private val maxPoints : Int? = null
    private val initDamage : Float? = null
    private val detectionSize : Float? = null

    val size get() =  detectionSize!! * sqRatio
    private val damageFade get() = if (hitBoxes.size > Math.round(firstIgnored!!*sqRatio)) (hitBoxes.size)/(detectionRatio!!*maxPoints!!/cutRatio!!) else 0f
    override val damage get() = initDamage!! * damageFade

    var active = false
    var hitBoxes = ArrayList<Rectangle>()

    fun update(swipe: SwipeHandler) {
        if (active) {
            hitBoxes.clear()
            val simplified = swipe.path(pointer!!)
            for (i in 0 until Math.round(simplified.size*detectionRatio!!)) {
                if (i % cutRatio!! == 0){
                    hitBoxes.add(Rectangle(simplified[i].x-size/2, simplified[i].y-size/2, size, size))
                }

            }
        }
        active = false
    }

    fun isSlicing(rect: Rectangle): Boolean {
        for (hB in hitBoxes){
            if(hB.overlaps(rect) && active)
                return true
        }
        return false

    }
}