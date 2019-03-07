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
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.coden.starslicer.BladePoint
import com.coden.starslicer.StarSlicerGame
import com.coden.starslicer.entities.Missile
import com.coden.starslicer.entities.NuclerBomb
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.handlers.MissileHandler
import com.coden.starslicer.util.centerX
import com.coden.starslicer.util.spawnRandomBomb
import com.coden.starslicer.util.spawnRandomMissle

class GameScreen(val game: StarSlicerGame) : Screen {

    lateinit var cam: OrthographicCamera
    lateinit var batch: SpriteBatch
    lateinit var shapeRenderer: ShapeRenderer

    lateinit var spaceCraft: SpaceCraft

    lateinit var missileHandler: MissileHandler

    val bombs = ArrayList<NuclerBomb>()

    val blades = arrayListOf(BladePoint(0),BladePoint(1))

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

        for (bomb in bombs) {
            bomb.render(batch)
        }


        batch.end()

        // SWIPE RENDERER
        game.swipeRenderer.render(cam)

        // SHAPE RENDERER FOR DEBUG
        debugShapes()
    }

    fun update() {

        spaceCraft.update()

        missileHandler.update(spaceCraft)
        missileHandler.updateSpawning()

        for (blade in blades){
            blade.update(game.swipeRenderer.swipe)
        }

        val iterator = bombs.iterator()
        while (iterator.hasNext()) {
            val bomb = iterator.next()
            bomb.update()
            if (bomb.isDead) {
                Gdx.app.log("update", "NuclearBomb is dead")
                iterator.remove()
            }
        }

        updateInput()

        updateSlicing()

    }

    fun updateInput() {

        if (Gdx.input.justTouched()) {
            if (Gdx.input.x < centerX) {
                missileHandler.spawnSomeMissile()
            } else {
                spawnRandomBomb(0, bombs)
            }

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            spawnRandomBomb(0, bombs)
        }

        if (Gdx.input.isTouched(0)) {
            blades[0].active = true

        }
        if (Gdx.input.isTouched(1)) {
            blades[1].active = true
        }

    }

    fun updateSlicing() {

        if (!(blades[0].active || blades[1].active)){
            return
        }
        for (bomb in bombs) {
            if (blades[0].isSlicing(bomb.hitBox) || blades[1].isSlicing(bomb.hitBox)){
                bomb.kill()
            }
        }

        for (missile in missileHandler.missiles) {
            if (blades[0].isSlicing(missile.hitBox) || blades[1].isSlicing(missile.hitBox)){
                missile.kill()
            }
        }

    }

    // DEBUG SECTION

    fun debugShapes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        renderRect(shapeRenderer, spaceCraft.hitBox)

        for (missile in missileHandler.missiles) {
            renderRect(shapeRenderer, missile.hitBox)
        }

        for (bomb in bombs) {
            renderRect(shapeRenderer, bomb.hitBox)
        }

        //shapeRenderer.circle(blade0.pos.x, blade0.pos.y, blade0.radius)
        for (hitBox in blades[0].hitBoxes){
            renderRect(shapeRenderer, hitBox)
        }
        for (hitBox in blades[1].hitBoxes){
            renderRect(shapeRenderer, hitBox)
        }



        shapeRenderer.end()
    }
    fun renderFPS() {
        font.draw(batch, Gdx.graphics.framesPerSecond.toString(), w-50, h-75)
    }

    fun renderVector(shapeRenderer: ShapeRenderer, pos: Vector2, vector: Vector2) {
        shapeRenderer.line(pos, pos.cpy().add(vector.cpy().setLength(50f)))
    }

    fun renderRect(shapeRenderer: ShapeRenderer,rect: Rectangle) {
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
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