package com.gamedev.game3.core.showcase.sprites;

import playn.core.Image;

import playn.core.Platform;
import playn.core.Json;
import react.Functions;
import react.RFuture;
import react.RPromise;
import react.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 02.03.2017.
 */
public class SpriteLoader {
    public static Sprite getSprite(Platform plat, String imagePath, String jsonPath) {
        return getSprite(plat, jsonPath, new Image[]{plat.assets().getImage(imagePath)});
    }

    public static Sprite getSprite(Platform plat, String jsonPath) {
        return getSprite(plat, jsonPath, (Image[])null);
    }

    private static Sprite getSprite(final Platform plat, String jsonPath, final Image[] images) {
        final RPromise<Sprite> state = RPromise.create();
        final Sprite sprite = new Sprite(state);
        plat.assets().getText(jsonPath).onFailure(state.failer()).onSuccess(new Slot<String>() {
            @Override
            public void onEmit(String s) {
                try {
                    loadSprite(plat, images, sprite, plat.json().parse(s),state);
                } catch (Throwable err) {
                    err.printStackTrace(System.err);
                    state.fail(err);
                }
            }
        });
        return sprite;
    }

    private static void loadSprite(Platform plat, Image[] images, Sprite sprite,  Json.Object json, RPromise<Sprite> state) {
        if (images == null) {
            Json.Array urls = json.getArray("urls");
            assert urls != null : "No urls provided for sprite images";
            images = new Image[urls.length()];
            for (int ii = 0; ii < urls.length(); ii++) {
                images[ii] = plat.assets().getImage(urls.getString(ii));
            }
        }

        Json.Array spriteImages = json.getArray("sprites");
        for(int i = 0; i < spriteImages.length(); i++) {
            Json.Object jsonSpriteImage = spriteImages.getObject(i);
            String id = jsonSpriteImage.getString("id");
            int imageId = jsonSpriteImage.getInt("url");
            assert imageId < images.length : "URL must be an index into the URLs array";
            int x = jsonSpriteImage.getInt("x");
            int y = jsonSpriteImage.getInt("y");
            int width = jsonSpriteImage.getInt("w");
            int height = jsonSpriteImage.getInt("h");
            SpriteImage spriteImage = new SpriteImage(images[imageId],x,y,width,height);
            sprite.addSpriteImage(id, spriteImage);
        }

        List<RFuture<Image>> states = new ArrayList<>();
        for(Image image : images) {
            states.add(image.state);
            RFuture.collect(states).map(Functions.constant(sprite)).onComplete(state.completer());
        }
    }
}
