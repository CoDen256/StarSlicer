package com.coden.starslicer.Commands.spawnCommands

import com.coden.starslicer.Commands.Command
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.events.Observer
import com.coden.starslicer.events.SubjectAdapter
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
                "puc" -> SpawnContainer(state, PowerUp.convert(id.last().toString().toInt()))
                "sat" -> SpawnSatellite(state, PowerUp.convert(id.last().toString().toInt()))
                else -> NullSpawnCommand()
            }


        }
    }
}