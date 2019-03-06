package com.coden.starslicer.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.coden.starslicer.StarSlicerGame
import com.coden.starslicer.entities.Missile
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.handlers.MissileHandler
import com.coden.starslicer.util.spawnRandomMissle

class GameScreen(val game: StarSlicerGame) : Screen {

    lateinit var cam: OrthographicCamera
    lateinit var batch: SpriteBatch
    lateinit var shapeRenderer: ShapeRenderer

    lateinit var spaceCraft: SpaceCraft

    lateinit var missileHandler: MissileHandler



    val font = BitmapFont()

    val bg = Texture("bg.png")

    val w = Gdx.graphics.width + 0f
    val h = Gdx.graphics.height + 0f



    override fun show() {

        spaceCraft = SpaceCraft()

        missileHandler = MissileHandler()

        Gdx.app.log("GameScreen", "The screen is created")
        Gdx.app.log("GameScreen", "Size: $w x $h")

        cam = OrthographicCamera()

        cam.setToOrtho(false, w, h)

        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()

        cam.update()
        batch.projectionMatrix = cam.combined


    }

    override fun render(delta: Float) {
        // UPDATING EVENTS
        update()

        // MAIN BATCH
        batch.begin()

        batch.draw(bg, 0f, 0f, w, h)

        renderFPS()

        spaceCraft.render(batch)
        missileHandler.render(batch)


        batch.end()

        // SWIPE RENDERER
        game.swipeRenderer.render(cam)

        // SHAPE RENDERER FOR DEBUG
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)


        shapeRenderer.end()
    }

    fun update() {

        spaceCraft.update()

        missileHandler.update()
        missileHandler.updateSpawning()

        updateInput()




    }

    fun updateInput() {

        if (Gdx.input.justTouched()) {
            missileHandler.spawnSomeMissile()
        }

    }

    // DEBUG SECTION
    fun renderFPS() {
        font.draw(batch, Gdx.graphics.framesPerSecond.toString(), w-50, h-75)
    }

    fun renderVector(shapeRenderer: ShapeRenderer, pos: Vector2, vector: Vector2) {
        shapeRenderer.line(pos, pos.cpy().add(vector.cpy().setLength(50f)))
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

        Gdx.app.log("GameScreen", "The screen is disposed")

    }

}