package com.gamedev.game3.core.showcase.sprites;

import playn.scene.ImageLayer;
import pythagoras.f.Rectangle;
import react.RFuture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Anton on 02.03.2017.
 */
public class Sprite {
    private List<SpriteImage> spriteImages;
    private HashMap<String, Integer> spriteIdMap;
    private SpriteImage current;
    private int currentId = -1;
    private boolean imagesDone = false; //true when images have finished loading
    private boolean dataDone = false; //true when sprite data has finished loading

    /** This sprite's image layer */
    public final ImageLayer layer = new ImageLayer();

    /** a future that completes when this sprite is fully loaded */
    public final RFuture<Sprite> state;

    /**
     * Do not call directly
     */
    Sprite (RFuture<Sprite> state) {
        this.state = state;
        layer.region = new Rectangle();
        spriteImages = new ArrayList<>(0);
        spriteIdMap = new HashMap<>();

    }

    public int numSprites () {return  spriteImages.size(); }
    public float width() {return (current != null) ? current.getWidth() : 1; }
    public float height() { return (current != null) ? current.getHeight() : 1; }

    public void setSprite(int index) {
        assert index < spriteImages.size() : "Invalid sprite index";
        if (index != currentId) {
            current = spriteImages.get(index);
            currentId = index;
            if (current != null) {
                layer.setTile(current.getImage().texture());
                layer.region.setBounds(current.getX(), current.getY(), current.getWidth(), current.getHeight());
            }
        }
    }

    /** set current sprite via the sprite id */
    public void setSprite (String id) {
        Integer index = spriteIdMap.get(id);
        assert  index != null : "Invalid sprite id";
        setSprite(index);
    }

    void addSpriteImage(String key, SpriteImage spriteImage) {
        spriteIdMap.put(key, spriteImages.size());
        spriteImages.add(spriteImage);
    }

    List<SpriteImage> spriteImages() {
        return spriteImages;
    }

}
