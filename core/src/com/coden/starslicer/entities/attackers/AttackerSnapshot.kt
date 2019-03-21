package com.coden.starslicer.entities.attackers

class AttackerSnapshot {

    // Attacker
    private var lifeSpan: Float? = null
    private var maxHealth: Float? = null
    private var maxMovementSpeed: Float? = null
    private var damage: Float? = null
    private var collisional: Boolean? = null

    // Meteors + Containers
    var minAngleSpeed: Float = 0f
    var maxAngleSpeed: Float = 0f

    // Missile properties
    var spiralRadius: Float = 0f
    var spiralInitialCount = 0f

    // NuclearBomb
    var shieldAbsorbPortion = 0f



    private var lifeSpanMap = mutableMapOf<Int, Float>()
    private var maxHealthMap = mutableMapOf<Int, Float>()
    private var maxMovementSpeedMap = mutableMapOf<Int, Float>()
    private var damageMap = mutableMapOf<Int, Float>()
    private var collisionalMap = mutableMapOf<Int, Boolean>()

    var name = "UNDEFINED"
    val type: AttackerType get() = AttackerType.Converter.get(name)


    fun getMaxHealth(state: Int): Float {
        return if (maxHealth == null) maxHealthMap[state]!! else maxHealth!!
    }

    fun getLifeSpan(state: Int): Float {
        return if (lifeSpan == null) lifeSpanMap[state]!! else lifeSpan!!
    }

    fun getMaxMovementSpeed(state: Int): Float {
        return if (maxMovementSpeed == null) maxMovementSpeedMap[state]!! else maxMovementSpeed!!
    }

    fun getDamage(state: Int): Float {
        return if (damage == null) damageMap[state]!! else damage!!
    }

    fun getCollisional(state: Int): Boolean {
        return if (collisional == null) collisionalMap[state]!! else collisional!!
    }

}
