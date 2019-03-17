package com.coden.starslicer.util

import com.coden.starslicer.entities.attackers.AttackerSnapshot
import com.google.gson.Gson
import java.io.FileReader

object EntityLoader {

    val gson = Gson()

    fun  loadAttacker(name: String): AttackerSnapshot {
        return gson.fromJson(FileReader("entities/attackers/$name"), AttackerSnapshot::class.java)
    }

}