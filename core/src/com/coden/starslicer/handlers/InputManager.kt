package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.Commands.*
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.attackers.NuclearBomb
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.centerX
import com.coden.starslicer.util.centerY
import java.awt.Container

class InputManager(private val data: EntityData) {

    private val log = Logger("InputManager", Logger.DEBUG)

    fun updateSwiping(){
        with(SpaceCraft){
            if (!(firstBlade.active || secondBlade.active)){
                return
            }
            for (attacker in attackers) {

                // Only one blade would be registered
                val firstIsSlicing = firstBlade.isSlicing(attacker.hitBox)
                val secondIsSlicing = secondBlade.isSlicing(attacker.hitBox)

                if (firstIsSlicing){
                    attacker.takeDamage(firstBlade.damage)
                }

                if (secondIsSlicing){
                    attacker.takeDamage(secondBlade.damage)
                }

                if ((firstIsSlicing || secondIsSlicing) && attacker.isDead){
                    attacker.onDestroy()
                    if (attacker is com.coden.starslicer.entities.entityInterfaces.Container) {
                        addPowerUp(attacker.content)
                        Log.info("Granted ${attacker.content}", Log.LogType.ATTACKERS)
                    }
                }
            }
        }
    }

    fun updateClicking(){
        if (Gdx.input.justTouched()){
            for (icon in data.powerUpIcons) {
                if (icon.hitBox.contains(Gdx.input.x * 1f, Gdx.graphics.height - Gdx.input.y * 1f)) {
                    usePowerUp(icon.type)
                }
            }
        }
    }

    private fun usePowerUp(ability: PowerUp.PowerUpType) = when (ability) {
        SHIELD    -> if (!data.shields.isEmpty() && !SpaceCraft.isShielded) data.shields[0].applyEffect() else Unit
        HPBOOST   -> if (!data.boosts.isEmpty()) data.boosts[0].applyEffect() else Unit
        SHOCKWAVE -> {
            if (!data.shockWaves.isEmpty()) {
                for (shockWave in data.shockWaves) if (!shockWave.active) {
                    shockWave.applyEffect()
                    break
                }
            }
            else Unit
        }
        RANDOM -> throw IllegalArgumentException()
    }

    private fun addPowerUp(ability: PowerUp.PowerUpType) = when (ability) {
        SHIELD     -> data.shields.add(Shield())
        HPBOOST    -> data.boosts.add(HPBoost())
        SHOCKWAVE  -> data.shockWaves.add(ShockWave())
        RANDOM -> throw IllegalArgumentException()
    }


    fun debugSpawning() {

        //if (Gdx.input.justTouched()) {
        //    when {
        //        Gdx.input.x < centerX && Gdx.input.y < centerY -> spawnMissile.execute(data)
        //        Gdx.input.x > centerX && Gdx.input.y < centerY -> spawnNuclearBomb.execute(data)
        //        Gdx.input.x < centerX && Gdx.input.y > centerY -> spawnMeteor.execute(data)
        //        Gdx.input.x > centerX && Gdx.input.y > centerY -> {
        //            spawnSatellite.execute(data)
        //            spawnContainer.execute(data)
        //        }}
        //}

        //when {
        //    Gdx.input.isKeyJustPressed(Input.Keys.NUM_0) -> spawnMissile.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) -> spawnMissile.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) -> spawnMissile.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) -> spawnMissile.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.B) -> spawnNuclearBomb.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.M) -> spawnMeteor.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.COMMA) ->spawnMeteor.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.PERIOD) -> spawnMeteor.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.A) -> spawnSatellite.execute(data)
        //    Gdx.input.isKeyJustPressed(Input.Keys.C) -> spawnContainer.execute(data)
        //}
    }

}
