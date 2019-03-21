package com.coden.starslicer.entities.entityInterfaces

interface Mortal {

    var isDead: Boolean

    fun kill(){
        isDead = true
    }
}