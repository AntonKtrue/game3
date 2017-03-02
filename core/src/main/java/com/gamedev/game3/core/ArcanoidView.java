package com.gamedev.game3.core;

import com.gamedev.game3.core.components.ArcanoidCarret;
import playn.core.Canvas;
import playn.core.Image;
import playn.core.Texture;
import playn.core.Tile;
import playn.scene.GroupLayer;
import playn.scene.ImageLayer;
import playn.scene.Layer;

/**
 * Created by Anton on 02.03.2017.
 */
public class ArcanoidView extends GroupLayer {
    private final float size = 80;

    private final Tile ptile;
    private final Arcanoid game;
    private final GroupLayer pgroup = new GroupLayer();

    public ArcanoidView(Arcanoid game) {
        this.game = game;

        Canvas canvas = game.plat.graphics().createCanvas(size,10f);
        canvas.setFillColor(0xFFFFFFFF).fillRect(1,1,size-1, 10f-1)
                .setStrokeColor(0xFF000000).setStrokeWidth(1).strokeRect(0,0,size,10f);

        Texture ptext = canvas.toTexture(Texture.Config.UNMANAGED);
        ptile = ptext.tile(0,0,size,10f);

        onDisposed(ptext.disposeSlot());

    }

    private ImageLayer addCarret(ArcanoidCarret carret) {
        ImageLayer pview = new ImageLayer(ptile);
        pview.setOrigin(Origin.CENTER);

        return pview;
    }

    public void showCarret(ArcanoidCarret arcanoidCarret) {
        ImageLayer pview = addCarret(arcanoidCarret);
        pview.setVisible(true);

    }
}
