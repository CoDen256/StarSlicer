package com.coden.starslicer.util

import com.coden.starslicer.commands.spawnCommands.NullSpawnCommand
import com.coden.starslicer.commands.spawnCommands.SpawnCommand
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.gameplay.GameData
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

        val spaceCraft: SpaceCraft = SpaceCraft.create()

        private lateinit var gameDataService: GameData
        fun getGameData(): GameData{
            return gameDataService
        }

        fun provide(gameData: GameData){
            gameDataService = gameData
        }

    }
}