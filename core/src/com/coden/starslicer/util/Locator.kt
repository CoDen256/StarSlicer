package com.coden.starslicer.util

import com.coden.starslicer.Commands.spawnCommands.NullSpawnCommand
import com.coden.starslicer.Commands.spawnCommands.SpawnCommand
import com.coden.starslicer.hud.HUD

class Locator {
    companion object {
        private val spawnServices = mutableMapOf<String, SpawnCommand>()

        fun getSpawnCommand(id: String): SpawnCommand {
            return spawnServices[id] ?: NullSpawnCommand()
        }
        fun provide(key: String, value: SpawnCommand){
            spawnServices[key] = value
        }


        private lateinit var uiService: HUD
        fun getUI(): HUD{
            return uiService
        }
        fun provide(hud: HUD){
            uiService = hud
        }
    }
}