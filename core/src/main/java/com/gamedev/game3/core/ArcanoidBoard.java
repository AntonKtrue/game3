package com.gamedev.game3.core;

import playn.core.Image;
import playn.core.Surface;
import playn.scene.ImageLayer;
import playn.scene.Layer;

/**
 * Created by Anton on 02.03.2017.
 */
public class ArcanoidBoard extends ImageLayer {

    private final Arcanoid game;

    public ArcanoidBoard(Arcanoid game) {
        this.game = game;
        Image bgImage = game.plat.assets().getImage("images/arcanoid-bg.jpg");
        ImageLayer bgLayer = new ImageLayer(bgImage);
        bgLayer.setSize(game.plat.graphics().viewSize);
        game.rootLayer.add(bgLayer);

    }

    @Override
    protected void paintImpl(Surface surf) {

    }
}
