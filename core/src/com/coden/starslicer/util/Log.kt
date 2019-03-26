package com.coden.starslicer.util

import com.badlogic.gdx.utils.Logger


object Log {
    enum class LogType {INFO, DEBUG, HUD, ATTACKERS, SCREENS, ASSETS}
    private val infoLogger  = Logger("INFO", Logger.INFO)
    private val debugLogger  = Logger("DEBUG", Logger.INFO)
    private val hudLogger  = Logger("HUD", Logger.INFO)
    private val attackerLogger  = Logger("Attackers", Logger.INFO)
    private val screenLogger  = Logger("Screens", Logger.INFO)
    private val assetLogger  = Logger("Assets", Logger.INFO)

    fun info(string: String, type: LogType) = when(type){
        Log.LogType.INFO -> infoLogger.info(string)
        Log.LogType.DEBUG -> debugLogger.info(string)
        Log.LogType.HUD -> hudLogger.info(string)
        Log.LogType.ATTACKERS -> attackerLogger.info(string)
        Log.LogType.SCREENS -> screenLogger.info(string)
        Log.LogType.ASSETS -> assetLogger.info(string)
    }
}