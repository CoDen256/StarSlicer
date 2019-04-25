package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.coden.starslicer.commands.spawnCommands.*
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.events.EventType
import com.coden.starslicer.events.SubjectAdapter
import com.coden.starslicer.util.assets.AssetLocator
import com.coden.starslicer.util.Locator
import com.coden.starslicer.util.Log

class InputManager: SubjectAdapter() {

    init {
        addObserver(Locator.getUI())
    }

    fun updateSwiping(){
        with(Locator.spaceCraft){
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
                    // TODO: observer
                    Locator.getGameData().points += attacker.reward

                    attacker.onDestroy()
                    if (attacker is com.coden.starslicer.entities.entityInterfaces.Container) {
                        addPowerUp(attacker.content)
                        Log.info("Granted ${attacker.content}", Log.LogType.ATTACKERS)
                    }
                    else{
                        Locator.getGameData().coins += MathUtils.random(10, 50)
                    }
                }
            }
        }
    }

    fun updateClicking(){
        if (Gdx.input.justTouched()){
            for (icon in AssetLocator.getPowerUpAssets().icons) {
                if (icon.hitBox.contains(Gdx.input.x * 1f, Gdx.graphics.height - Gdx.input.y * 1f)) {
                    usePowerUp(icon.type)
                }
            }
        }
    }

    private fun usePowerUp(ability: PowerUp.PowerUpType){
        notify(EventType.USED, ability)
        PowerUp.use(ability)
    }
    private fun addPowerUp(ability: PowerUp.PowerUpType){
        notify(EventType.ADDED, ability)
        PowerUp.create(ability)
    }

    fun debugSpawning() {
            when {
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_0) -> SpawnMissile(0).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) -> SpawnMissile(1).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) -> SpawnMissile(2).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) -> SpawnMissile(3).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.B) -> SpawnNuclearBomb(0).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.M) -> SpawnMeteor(size = 0).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.COMMA) -> SpawnMeteor(size = 1).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.PERIOD) -> SpawnMeteor(size = 2).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.A) -> SpawnSatellite(0, RANDOM).execute()
                Gdx.input.isKeyJustPressed(Input.Keys.C) -> SpawnContainer(0, RANDOM).execute()
            }
        }

}
