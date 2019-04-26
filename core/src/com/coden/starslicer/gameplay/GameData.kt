package com.coden.starslicer.gameplay

data class GameData(var coins: Int, var points: Int) {
    fun gainCoins(amount: Int){
        coins += amount
    }

    fun gainPoints(amount: Int){
        points += amount
    }
}