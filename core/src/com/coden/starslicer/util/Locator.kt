package com.coden.starslicer.util

import com.coden.starslicer.Commands.spawnCommands.NullCommand
import com.coden.starslicer.Commands.spawnCommands.SpawnCommand

class Locator {
    companion object {
        val spawnServices = mutableMapOf<String, SpawnCommand>()

        fun getSpawnCommand(id: String): SpawnCommand {
            return spawnServices[id] ?: NullCommand()
        }
        fun provide(key: String, value: SpawnCommand){
            spawnServices[key] = value
        }
    }
}