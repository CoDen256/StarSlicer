package com.coden.starslicer.util

import com.badlogic.gdx.utils.Logger


object Log {
    private val infoLogger  = Logger("INFO", Logger.INFO)
    private val debugLogger  = Logger("DEBUG", Logger.DEBUG)

    fun info(string: String) {
        infoLogger.info(string)
    }

    fun debug(string: String) {
        debugLogger.info(string)
    }
}