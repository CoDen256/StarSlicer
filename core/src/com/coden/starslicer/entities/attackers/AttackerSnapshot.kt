package com.coden.starslicer.entities.attackers

class AttackerSnapshot {

    var  lifeSpan: Float = 0f
    var  maxHealth : Float = 0f
    var  damage : Float = 0f
    var  movementSpeed : Float = 0f
    var collisional = false

    var lifeSpans = mutableMapOf<Int, Float>()

    var name = "UNDEFINED"

    var type: AttackerType
    get() = AttackerType.get(name)!!
    set(value) {}

    override fun toString(): String {
        return "$lifeSpan, $maxHealth, $damage, $movementSpeed $type $lifeSpans"
    }


}
