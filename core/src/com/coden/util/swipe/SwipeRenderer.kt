package com.coden.util.swipe

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.coden.starslicer.util.assets.AssetLocator
import com.coden.starslicer.util.SwipeAssets
import com.coden.util.swipe.mesh.SwipeTriStrip

class SwipeRenderer(val initialDistance: Int, val minDistance: Int, val bladesNum: Int,
                    val maxLifeSpan: Float, val thickness: Float,
                    val maxInputPoint:Int,
                    val color: Color = Color.WHITE, val endcap: Float = 10f
                    ){

    lateinit var swipe: SwipeHandler
    lateinit var tris: SwipeTriStrip
    lateinit var tex: Texture

    fun changeTexture(num: Int){
        tex = AssetLocator.getSwipeAssets().getTexture(num)
    }

    fun create() {
        tris = SwipeTriStrip()

        //a swipe handler with max # of input points to be kept alive
        swipe = SwipeHandler(maxInputPoint, bladesNum)

        //minimum distance between two points
        swipe.minDistance = minDistance

        //minimum distance between first and second point
        swipe.initialDistance = initialDistance

        // lifespan of each particle
        swipe.maxLifeSpan = maxLifeSpan

        changeTexture(0)
        //we will use a texture for the smooth edge, and also for stroke effects
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

    }

    fun render(camera: OrthographicCamera) {

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        //generate the triangle strip from our path
        for (i in 0 until swipe.bladesNum) {
            tex.bind()

            //the endcap scale
            //tris.endcap = endcap;

            swipe.blades[i].timePassed += Gdx.graphics.deltaTime
            swipe.checkLife(i)

            //the thickness of the line
            tris.thickness = thickness

            tris.update(swipe.path(i))

            //the vertex color for tinting, i.e. for opacity
            tris.color = color

            //render the triangles to the screen
            tris.draw(camera)
        }

    }

    fun dispose() {
        tex.dispose()
    }







}