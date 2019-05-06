package com.coden.starslicer.gameplay.commands.spawnCommands

import com.coden.starslicer.gameplay.commands.Command
import com.coden.starslicer.gameplay.events.Observer
import com.coden.starslicer.gameplay.events.SubjectAdapter
import com.coden.starslicer.gameObjects.powerups.PowerUpType
import com.coden.starslicer.util.Locator
import kotlin.collections.ArrayList

abstract class SpawnCommand: SubjectAdapter(), Command{
    override val subscribers: ArrayList<Observer> = arrayListOf(Locator.getUI())

    companion object {
        fun convert(id: String): SpawnCommand{
            val type = id.substring(0,3)
            val state = id[3].toString().toInt()
            return when(type){
                "mis" -> SpawnMissile(state)
                "met" -> SpawnMeteor(state, id.last().toString().toInt())
                "nuc" -> SpawnNuclearBomb(state)
                "puc" -> SpawnContainer(state, PowerUpType.convert(id.last().toString().toInt()))
                "sat" -> SpawnSatellite(state, PowerUpType.convert(id.last().toString().toInt()))
                else -> NullSpawnCommand()
            }


        }
    }
}