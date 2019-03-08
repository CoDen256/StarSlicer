package com.coden.starslicer.entities

interface Entity {
    var health: Float
    val damage: Float

    fun takeDamage(value: Float)
    fun giveDamage(entities: ArrayList<Entity>, value: Float)
    fun giveDamage(entity: Entity, value: Float)
}