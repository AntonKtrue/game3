package com.gamedev.game3.core.showcase.sprites;

import playn.core.Clock;
import playn.core.Platform;
import playn.scene.GroupLayer;
import playn.scene.Layer;
import react.Slot;

/**
 * Created by Anton on 02.03.2017.
 */
public class Pea {
    public static String IMAGE ="images/showcase/sprites/billylee.png";// "images/showcase/sprites/peasprites.png";
    public static String JSON = "images/showcase/sprites/bellylee.json";// "images/showcase/sprites/peasprite.json";
    public static String JSON_WITH_IMAGE = "images/showcase/sprites/peasprite2.json";

    private Sprite sprite;
    private int spriteIndex = 0;
    private int scan = 0;
    private int scandelay = 10;
    private boolean hasLoaded = false;

    public Pea(Platform plat, final GroupLayer layer, float x, float y) {
        sprite = SpriteLoader.getSprite(plat, IMAGE, JSON);
        sprite.layer.setOrigin(Layer.Origin.CENTER);
        sprite.layer.setTranslation(x, y);

        sprite.state.onSuccess(new Slot<Sprite>(){

            @Override
            public void onEmit(Sprite sprite) {
                sprite.setSprite(spriteIndex);
                layer.add(sprite.layer);
                hasLoaded = true;
            }
        });
    }

    public void update(Clock clock) {
        if(hasLoaded) {
           // if(Math.random() > 0.95) {
               // spriteIndex = (spriteIndex + 1) % sprite.numSprites();
            scan++;
            if(scan > scandelay) {
                spriteIndex++;
                if(spriteIndex >= sprite.numSprites()) spriteIndex = 0;
                sprite.setSprite(spriteIndex);
                scan = 0;
            }

           // }
           // sprite.layer.setRotation(clock.tick/1000f);
        }
    }
}
