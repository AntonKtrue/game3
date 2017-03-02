package com.gamedev.game3.core;

import com.gamedev.game3.core.components.ArcanoidCarret;
import playn.core.Image;
import playn.core.Platform;
import playn.scene.ImageLayer;
import playn.scene.SceneGame;
import react.Slot;
import react.Value;



/**
 * Created by Anton on 02.03.2017.
 */
public class Arcanoid extends SceneGame {

   // private final Value turn = Value.create(null);

    public final Value<ArcanoidCarret> turn = Value.create(null);

    public Arcanoid(Platform plat) {
        super(plat, 33);
        Image bgImage = plat.assets().getImage("images/arcanoid-bg.jpg");
        ImageLayer bgLayer = new ImageLayer(bgImage);
        bgLayer.setSize(plat.graphics().viewSize);
        rootLayer.add(bgLayer);
        final ArcanoidView gview = new ArcanoidView(this);
        rootLayer.add(gview);

        turn.connect(new Slot<ArcanoidCarret>(){

            @Override
            public void onEmit(ArcanoidCarret arcanoidCarret) {
                gview.showCarret(arcanoidCarret);
               // turn.update(arcanoidCarret);
            }
        });

        reset();
    }

    private void reset() {

    }
}
