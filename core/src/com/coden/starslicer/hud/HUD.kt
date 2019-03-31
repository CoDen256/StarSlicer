package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.entities.spacecraft.SpaceCraft


class HUD(data: EntityData) {

    private val powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.5f, Gdx.graphics.height*0.88f, data)

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()
    private var debugRenderer: ShapeRenderer = ShapeRenderer()

    private val log = Logger("HUD", Logger.INFO)


    private val spaceCraftBar = HealthBar(SpaceCraft)



    fun update() {
        powerUpsBar.update()
        spaceCraftBar.update()

    }

    fun render() {
        batch.begin()

        powerUpsBar.render(batch)
        spaceCraftBar.render(batch)

        batch.end()


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(1f, 1f, 1f, 1f)

        powerUpsBar.render(shapeRenderer)
        spaceCraftBar.render(shapeRenderer)

        for (attacker in attackers){
            if (attacker.healthBar != null){
                if (attacker !is Missile){
                    attacker.healthBar!!.render(shapeRenderer)
                }

            }

        }

        shapeRenderer.end()

        debug()

    }


    fun debug() {
        debugRenderer.begin(ShapeRenderer.ShapeType.Line)

        powerUpsBar.debug(debugRenderer)

        debugRenderer.end()
    }

}


