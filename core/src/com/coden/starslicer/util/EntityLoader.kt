package com.coden.starslicer.util

import com.coden.starslicer.entities.attackers.AttackerSnapshot
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.util.Assets.Companion.attackerConfigMap
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.FileReader

object EntityLoader {

    val gson = Gson()


    fun  loadAttacker(type: AttackerType): AttackerSnapshot {
        return gson.fromJson(attackerConfigMap[type], AttackerSnapshot::class.java)
    }

}