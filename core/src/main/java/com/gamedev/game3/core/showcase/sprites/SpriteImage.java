package com.gamedev.game3.core.showcase.sprites;

import playn.core.Image;

/**
 * Created by Anton on 02.03.2017.
 */
public class SpriteImage {
    private final Image image;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public SpriteImage(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
