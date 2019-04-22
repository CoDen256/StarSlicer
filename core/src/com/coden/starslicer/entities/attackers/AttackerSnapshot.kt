package com.coden.starslicer.entities.attackers

import java.util.*
import kotlin.math.min

data class AttackerSnapshot(val name: String) {

    // Meteors + Containers
    var minAngleSpeed: Float = 0f
    var maxAngleSpeed: Float = 0f

    // Missile properties
    var spiralRadius: Float = 0f
    var spiralInitialCount: Float = 0f

    // NuclearBomb
    var shieldAbsorbPortion = 0f


    lateinit var reward: Array<Int>
    lateinit var lifeSpan : Array<Float>
    lateinit var maxHealth : Array<Float>
    lateinit var maxMovementSpeed : Array<Float>
    lateinit var damage : Array<Float>
    lateinit var collisional : Array<Boolean>

    val type: AttackerType get() = AttackerType.valueOf(name)

    override fun toString(): String {
        return "Name,type : $name, $type\n" +
                "AngleSpeed: $minAngleSpeed, $maxAngleSpeed\n" +
                "Spiral: $spiralRadius, $spiralInitialCount\n" +
                "ShieldAbsorb: $shieldAbsorbPortion\n" +
                "Reward: ${Arrays.toString(reward)}\n" +
                "LifeSpan: ${Arrays.toString(lifeSpan)}\n" +
                "MaxHealth: ${Arrays.toString(maxHealth)}\n" +
                "MaxMovementSpeed: ${Arrays.toString(maxMovementSpeed)}\n" +
                "Damage: ${Arrays.toString(damage)}\n" +
                "Collisional: ${Arrays.toString(collisional)}"
    }


    fun getMaxHealth(state: Int): Float {
        return if (maxHealth.size > 1) maxHealth[state] else maxHealth[0]
    }

    fun getLifeSpan(state: Int): Float {
        return if (lifeSpan.size > 1) lifeSpan[state] else lifeSpan[0]
    }

    fun getMaxMovementSpeed(state: Int): Float {
        return if (maxMovementSpeed.size > 1) maxMovementSpeed[state] else maxMovementSpeed[0]
    }

    fun getDamage(state: Int): Float {
        return if (damage.size > 1) damage[state] else damage[0]
    }

    fun getCollisional(state: Int): Boolean {
        return if (collisional.size > 1) collisional[state] else collisional[0]
    }

    fun getReward(state: Int): Int{
        return if (reward.size > 1) reward[state] else reward[0]
    }



}