package com.gamedev.game3.core;

import com.gamedev.game3.core.components.ArcanoidCarret;
import playn.core.Clock;
import playn.core.Image;
import playn.core.Platform;
import playn.core.Pointer;
import playn.scene.GroupLayer;
import playn.scene.ImageLayer;
import playn.scene.Layer;
import playn.scene.SceneGame;
import react.Slot;


/**
 * Created by Anton on 02.03.2017.
 */
public class HelloGame extends SceneGame {

    public class Pea {
        private final float angVel = (plat.tick() % 10 - 5)/1000f;
        private final ImageLayer layer;
        {
            Image image = plat.assets().getImage("images/carret.png");
            layer = new ImageLayer(image);
            layer.setOrigin(ImageLayer.Origin.CENTER);
        }

        public Pea(final GroupLayer peaLayer, float x, float y) {
            peaLayer.addAt(layer, x, y);
            //connect to the paint signal to animate our rotation
            paint.connect(new Slot<Clock>() {
                @Override
                public void onEmit(Clock clock) {
                   //layer.setRotation(clock.tick * angVel);
                }
            });
        }


    }



    public final Pointer pointer;

    public HelloGame(Platform plat) {
        super(plat, 25);

        //combine mouse and touch into pointer events
        pointer = new Pointer(plat);
        Image bgImage = plat.assets().getImage("images/arcanoid-bg.jpg");
        ImageLayer bgLayer = new ImageLayer(bgImage);
        bgLayer.setSize(plat.graphics().viewSize);
        rootLayer.add(bgLayer);

        //create a group field to hold the peas
        final GroupLayer peaLayer = new GroupLayer();
        rootLayer.add(peaLayer);
       // new Pea(peaLayer, peaLayer.width()/2, peaLayer.height() - 40);
        Image carretImage = plat.assets().getImage("images/carret.png");
        ArcanoidCarret carret = new ArcanoidCarret(carretImage);
        peaLayer.addAt(carret,
                plat.graphics().viewSize.width()/2-12,
                plat.graphics().viewSize.height()-40);


        //then the pointer is tapped add a new pea
        pointer.events.connect(new Slot<Pointer.Event>() {

            @Override
            public void onEmit(Pointer.Event event) {
                if (event.kind.isStart)
                    new Pea(peaLayer, event.x(), event.y());
            }
        });


    }
}
