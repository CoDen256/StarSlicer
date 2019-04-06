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

    private fun usePowerUp(ability: PowerUp.PowerUpType) = PowerUp.use(ability)
    private fun addPowerUp(ability: PowerUp.PowerUpType) = PowerUp.create(ability)


    fun debugSpawning() {
        when {
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_0) -> SpawnMissile(0).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) -> SpawnMissile(1).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) -> SpawnMissile(2).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) -> SpawnMissile(3).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.B) -> SpawnNuclearBomb(0).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.M) -> SpawnMeteor(size=0).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.COMMA) ->SpawnMeteor(size=1).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.PERIOD) -> SpawnMeteor(size=2).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.A) -> SpawnSatellite(0, RANDOM).execute(data)
            Gdx.input.isKeyJustPressed(Input.Keys.C) -> SpawnContainer(0, RANDOM).execute(data)
        }
    }

}
