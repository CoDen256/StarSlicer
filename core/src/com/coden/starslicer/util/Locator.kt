package com.coden.starslicer.util

import com.coden.starslicer.Commands.spawnCommands.NullCommand
import com.coden.starslicer.Commands.spawnCommands.SpawnCommand
import com.coden.starslicer.hud.HUD

class Locator {
    companion object {
        private val spawnServices = mutableMapOf<String, SpawnCommand>()

        fun getSpawnCommand(id: String): SpawnCommand {
            return spawnServices[id] ?: NullCommand()
        }
        fun provide(key: String, value: SpawnCommand){
            spawnServices[key] = value
        }


        private lateinit var uiManager: HUD
        fun getUI(): HUD{
            return uiManager
        }
        fun provide(hud: HUD){
            uiManager = hud
        }
    }
}