package com.coden.starslicer.hud

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class Button(name: String, pos: Vector2,stage: StageHandler) : Actor() {

    val buttonStyle = ImageButton.ImageButtonStyle()
    val skin = Skin()
    val textureAtlas = TextureAtlas(Gdx.files.internal("buttons/$name.atlas"))

    var imageButton: ImageButton

    init {
        skin.addRegions(textureAtlas)

        buttonStyle.imageUp = skin.getDrawable("${name}Up")
        buttonStyle.imageDown = skin.getDrawable("${name}Down")

        imageButton = ImageButton(buttonStyle)

        stage.add(imageButton)

        imageButton.setPosition(pos.x, pos.y)
    }


}