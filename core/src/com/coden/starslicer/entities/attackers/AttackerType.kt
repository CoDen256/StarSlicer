package com.coden.starslicer.entities.attackers

enum class AttackerType {
    SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR, MISSILE, NUCLEAR_BOMB,
    SATELLITE, POWERUP_CONTAINER;

    companion object {
        val types = listOf(SMALL_METEOR, MEDIUM_METEOR, LARGE_METEOR, MISSILE, NUCLEAR_BOMB, SATELLITE, POWERUP_CONTAINER)
                                             .map{it.name to it}.toMap()
        fun get(name: String) = types[name]
    }


}