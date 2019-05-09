package com.coden.starslicer.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.gameplay.InputManager
import com.coden.starslicer.hud.HUD
import com.coden.starslicer.StarSlicerGame
import com.coden.starslicer.gameObjects.attackers.Attacker.Companion.attackers
import com.coden.starslicer.gameObjects.powerups.PowerUp
import com.coden.starslicer.gameplay.DifficultyController
import com.coden.starslicer.gameplay.GameData
import com.coden.starslicer.util.*
import com.coden.starslicer.assets.AssetLocator
import com.coden.starslicer.gameObjects.attackers.Attacker
import com.coden.util.swipe.SwipeRenderer

class GameScreen(val game: StarSlicerGame) : Screen {

    private lateinit var cam: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var hud: HUD

    private lateinit var swipeRenderer: SwipeRenderer

    private lateinit var inputManager: InputManager
    private lateinit var difficultyController: DifficultyController

    private lateinit var gameData: GameData

    val font = BitmapFont()

    var timePassed = 0f
    val bg = Texture("ui/backgrounds/background.png")

    var debugging = false



    override fun show() {


        game.assetProvider.finish()

        AssetLocator.provide(game.assetProvider.attackerAssets)
        AssetLocator.provide(game.assetProvider.powerUpAssets)
        AssetLocator.provide(game.assetProvider.swipeAssets)
        AssetLocator.provide(game.assetProvider.spaceCraftAssets)
        AssetLocator.provide(game.assetProvider.configurationAssets)

        Log.info("The screen is created",Log.LogType.SCREENS)
        Log.info("Size: $w x $h", Log.LogType.SCREENS)
        Log.info("xRatio: $xRatio, yRatio: $yRatio, sqRatio:$sqRatio", Log.LogType.SCREENS)


        hud = HUD()
        gameData = GameData(0, 0)
        Locator.provide(hud)
        Locator.provide(gameData)

        inputManager = InputManager()

        difficultyController = DifficultyController()

        cam = OrthographicCamera()
        cam.setToOrtho(false, w, h)

        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()

        cam.update()
        batch.projectionMatrix = cam.combined


        swipeRenderer = SwipeRenderer(10, 10, 2,
                                    0.25f, 20f,
                                    10)

        //handle swipe input
        swipeRenderer.create()
        Gdx.input.inputProcessor = swipeRenderer.swipe

    }
    // RENDER SECTION

    override fun render(delta: Float) {
        // UPDATING EVENTS
        difficultyController.update()
        update()

        // MAIN BATCH

        renderMainEntities()

        // HUD
        hud.render()

        // SWIPE RENDERER
        swipeRenderer.render(cam)

        // SHAPE RENDERER FOR DEBUG
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) debugging = !debugging
        debugShapes(debugging)
    }

    fun renderMainEntities() {
        batch.begin()

        batch.draw(bg, 0f, 0f, w, h)

        renderFPS(batch)
        renderTimePassed(batch)
        difficultyController.render(batch, font)

        Locator.spaceCraft.render(batch)
        Attacker.renderAll(batch)


        batch.end()
    }

    // UPDATE SECTION
    fun update() {

        timePassed += Gdx.graphics.deltaTime

        Locator.spaceCraft.update(swipeRenderer.swipe)

        Attacker.updateAll()
        PowerUp.updateAll()




        updateInput()

        hud.update()

    }

    fun updateInput() {
        inputManager.debugSpawning()
        inputManager.updateSwiping()
        inputManager.updateClicking()

    }


    // DEBUG SECTION

    fun debugShapes(hitBoxRender: Boolean) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        when{
        Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0) -> swipeRenderer.changeTexture(0)
        Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1) -> swipeRenderer.changeTexture(1)
        Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2) -> swipeRenderer.changeTexture(2)
        Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3) -> swipeRenderer.changeTexture(3)

        }

        PowerUp.debugShapes(shapeRenderer)

        if (hitBoxRender) {
            renderRect(shapeRenderer, Locator.spaceCraft.hitBox)
            renderCircle(shapeRenderer, Locator.spaceCraft.hitSphere)


            for (attacker in attackers) {
                renderRect(shapeRenderer, attacker.hitBox)
                renderCircle(shapeRenderer, attacker.hitSphere)
            }

            for (hitBox in Locator.spaceCraft.firstBlade.hitBoxes) {
                renderRect(shapeRenderer, hitBox)
            }
            for (hitBox in Locator.spaceCraft.secondBlade.hitBoxes) {
                renderRect(shapeRenderer, hitBox)
            }

        }
        shapeRenderer.end()

    }
    fun renderFPS(batch: SpriteBatch) {
        font.draw(batch, Gdx.graphics.framesPerSecond.toString(), w-50, h-75)
    }

    fun renderTimePassed(batch: SpriteBatch){
        font.draw(batch, timePassed.toString(), w-75, h-100)
    }


    fun renderVector(shapeRenderer: ShapeRenderer, pos: Vector2, vector: Vector2) {
        shapeRenderer.line(pos, pos.cpy().add(vector.cpy()))
    }

    fun renderRect(shapeRenderer: ShapeRenderer,rect: Rectangle) {
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
    }

    fun renderCircle(shapeRenderer: ShapeRenderer, circle: Circle){
        shapeRenderer.circle(circle.x, circle.y, circle.radius)
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

        Log.info("The screen is disposed", Log.LogType.SCREENS)
        batch.dispose()
        shapeRenderer.dispose()
        Locator.spaceCraft.dispose()
        swipeRenderer.dispose()

    }

}