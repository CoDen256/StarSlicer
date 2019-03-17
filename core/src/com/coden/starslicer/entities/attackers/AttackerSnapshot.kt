package com.coden.starslicer.entities.attackers

class AttackerSnapshot {

    var lifeSpan: Float? = null
    var maxHealth: Float? = null
    var maxMovementSpeed: Float? = null
    var damage: Float? = null
    var collisional: Boolean? = null


    var lifeSpanMap = mutableMapOf<Int, Float>()
    var maxHealthMap = mutableMapOf<Int, Float>()
    var maxMovementSpeedMap = mutableMapOf<Int, Float>()
    var damageMap = mutableMapOf<Int, Float>()
    var collisionalMap = mutableMapOf<Int, Boolean>()

    var name = "UNDEFINED"

    var type: AttackerType
    get() = AttackerType.get(name)!!
    set(value) {}

    override fun toString(): String {
        return "$lifeSpanMap, $maxHealthMap, $damageMap, $maxMovementSpeedMap $type $lifeSpanMap"
    }


}
