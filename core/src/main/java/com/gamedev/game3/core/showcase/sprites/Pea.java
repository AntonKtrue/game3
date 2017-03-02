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
    public static String IMAGE = "images/showcase/sprites/peasprites.png";
    public static String JSON = "images/showcase/sprites/peasprite.json";
    public static String JSON_WITH_IMAGE = "images/showcase/sprites/peasprite2.json";

    private Sprite sprite;
    private int spriteIndex = 0;
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
            if(Math.random() > 0.95) {
                spriteIndex = (spriteIndex + 1) % sprite.numSprites();
                sprite.setSprite(spriteIndex);
            }
            sprite.layer.setRotation(clock.tick/1000f);
        }
    }
}
