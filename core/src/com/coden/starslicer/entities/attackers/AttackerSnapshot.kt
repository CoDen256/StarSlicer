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

    var reward: Int  = 10

    private var lifeSpanMap = mapOf<Int, Float>()
    private var maxHealthMap = mapOf<Int, Float>()
    private var maxMovementSpeedMap = mapOf<Int, Float>()
    private var damageMap = mapOf<Int, Float>()
    private var collisionalMap = mapOf<Int, Boolean>()

    var name = "UNDEFINED"
    val type: AttackerType get() = AttackerType.valueOf(name)


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
