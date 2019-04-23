package com.coden.starslicer.util

import com.badlogic.gdx.utils.Logger


object Log {
    enum class LogType {INFO, DEBUG, HUD, ATTACKERS, SCREENS, ASSETS, GAME, SPAWN, POWERUP}
    private val infoLogger  = Logger("INFO", Logger.NONE)
    private val debugLogger  = Logger("DEBUG", Logger.INFO)
    private val hudLogger  = Logger("HUD", Logger.NONE)
    private val attackerLogger  = Logger("Attackers", Logger.NONE)
    private val spawnLogger  = Logger("Spawning", Logger.NONE)
    private val screenLogger  = Logger("Screens", Logger.INFO)
    private val assetLogger  = Logger("Assets", Logger.INFO)
    private val gameLogger  = Logger("Game", Logger.NONE)
    private val powerUpLogger  = Logger("PowerUp", Logger.NONE)

    fun info(string: String, type: LogType) = when(type){
        Log.LogType.INFO -> infoLogger.info(string)
        Log.LogType.DEBUG -> debugLogger.info(string)
        Log.LogType.HUD -> hudLogger.info(string)
        Log.LogType.ATTACKERS -> attackerLogger.info(string)
        Log.LogType.SCREENS -> screenLogger.info(string)
        Log.LogType.ASSETS -> assetLogger.info(string)
        Log.LogType.GAME -> gameLogger.info(string)
        Log.LogType.SPAWN -> spawnLogger.info(string)
        Log.LogType.POWERUP -> powerUpLogger.info(string)
    }
}