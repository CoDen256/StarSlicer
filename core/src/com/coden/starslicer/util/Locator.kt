package com.coden.starslicer.util

import com.coden.starslicer.Commands.Command
import com.coden.starslicer.entities.attackers.AttackerType

class Locator {
    companion object {
        lateinit var spawnServices: Map<String, Command>

        fun getSpawnCommand(id: String): Command {
            return spawnServices[id]!!
        }
        fun provide(key: String, value: Command){

        }
    }
}