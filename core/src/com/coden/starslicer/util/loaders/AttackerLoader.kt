package com.coden.starslicer.util.loaders

import com.badlogic.gdx.utils.JsonValue
import com.coden.starslicer.entities.attackers.AttackerSnapshot
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.util.assets.AssetLocator

class AttackerLoader : Loader<AttackerSnapshot>{
    override val configMap = AssetLocator.getConfigs().attackerConfigMap

    fun load(attacker: AttackerType): AttackerSnapshot {
        return loadToSnapshot(configMap[attacker]!!)
    }

     fun loadToSnapshot(config: JsonValue): AttackerSnapshot {
        val snapshot = AttackerSnapshot(config.getString("name"))

        snapshot.minAngleSpeed = config.getFloat("minAngleSpeed", 0f)
        snapshot.maxAngleSpeed = config.getFloat("maxAngleSpeed", 0f)
        snapshot.spiralRadius = config.getFloat("spiralRadius", 0f)
        snapshot.spiralInitialCount = config.getFloat("spiralInitialCount", 0f)
        snapshot.shieldAbsorbPortion = config.getFloat("shieldAbsorbPortion", 0f)

        snapshot.reward = config.getIntArray("reward")
        snapshot.collisional = config.getBooleanArray("collisional")
        snapshot.maxHealth = config.getFloatArray("maxHealth")
        snapshot.maxMovementSpeed = config.getFloatArray("maxMovementSpeed")
        snapshot.damage = config.getFloatArray("damage")
        snapshot.lifeSpan  = config.getFloatArray("lifeSpan")

        return snapshot
    }

}