package com.gamedev.game3.core;

import playn.core.Surface;
import playn.scene.Layer;
import pythagoras.f.IDimension;

/**
 * Created by Anton on 01.03.2017.
 */
public class BoardView extends Layer {
    private static final float LINE_WIDTH = 2;
    private final Reversi game;
    public final float cellSize;

    public BoardView(Reversi game, IDimension viewSize) {
        this.game = game;
        float maxBoardSize = Math.min(viewSize.width(), viewSize.height()) - 20;
        this.cellSize = (float)Math.floor(maxBoardSize / game.boardSize);
    }

    @Override
    public float width() {
        return cellSize * game.boardSize + LINE_WIDTH;
    }

    @Override
    public float height() {
        return width();
    }

    @Override
    protected void paintImpl(Surface surf) {
        surf.setFillColor(0xFF000000);
        float top = 0, bot = height(), left = 0, right = width();

        for (int yy = 0; yy <= game.boardSize; yy++) {
            float ypos = yy * cellSize + 1;
            surf.drawLine(left, ypos, right, ypos, LINE_WIDTH);
        }
        for (int xx = 0; xx <= game.boardSize; xx++) {
            float xpos = xx * cellSize + 1;
            surf.drawLine(xpos, top, xpos, bot, LINE_WIDTH);
        }

    }

    public float cell(int cc) {
        return cc*cellSize + cellSize/2 + 1;
    }


}
