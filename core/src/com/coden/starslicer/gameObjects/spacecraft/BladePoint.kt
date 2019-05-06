package com.coden.starslicer.gameObjects.spacecraft

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.coden.starslicer.gameObjects.DamageGiver
import com.coden.starslicer.util.sqRatio
import com.coden.util.swipe.SwipeHandler

class BladePoint: DamageGiver {

    // Serializable
    var pointer : Int = -1
    var detectionRatio : Float = -1f
    var cutRatio : Int = -1
    var firstIgnored : Int = -1
    var maxPoints : Int = -1
    var initDamage : Float = -1f
    var detectionSize : Float = -1f

    val size get() =  detectionSize * sqRatio
    private val damageFade get() = if (hitBoxes.size > Math.round(firstIgnored*sqRatio)) (hitBoxes.size)/(detectionRatio*maxPoints/cutRatio) else 0f
    override val damage get() = initDamage * damageFade

    private var active = false

    val isActive get() = active
    var hitBoxes = ArrayList<Rectangle>()

    override fun toString(): String {
        return "Blade $pointer\n" +
                "DetectionRation :$detectionRatio\n" +
                "CutRatio: $cutRatio\n" +
                "FirstIgnored : $firstIgnored\n" +
                "MaxPoints: $maxPoints\n" +
                "IniDamage: $initDamage\n" +
                "DetectionSize: $detectionSize"
    }

    fun updateActivation(){
        if (Gdx.input.isTouched(pointer)){
            active = true
        }
    }

    fun update(swipe: SwipeHandler) {
        if (active) {
            hitBoxes.clear()
            val simplified = swipe.path(pointer)
            for (i in 0 until Math.round(simplified.size*detectionRatio)) {
                if (i % cutRatio == 0){
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