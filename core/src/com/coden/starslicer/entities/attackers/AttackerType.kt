package com.coden.starslicer.entities.attackers

enum class AttackerType {
    SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR, MISSILE, NUCLEAR_BOMB,
    SATELLITE, POWERUP_CONTAINER;

    object Converter{
        val types = AttackerType.values().map{it.name to it}.toMap()
        fun get(name: String) = types[name]!!
    }


}