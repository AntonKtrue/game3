package com.gamedev.game3.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 01.03.2017.
 */
public class Logic {
    public final int boardSize;

    public Logic (int boardSize) {
        this.boardSize = boardSize;
    }

    public boolean isLegalPlay (Map<Loader.Coord, Loader.Piece> board, Loader.Piece color, Loader.Coord coord) {
        if(!inBounds(coord.x, coord.y) || board.containsKey(coord)) return false;
        for (int ii = 0; ii < DX.length; ii++) {
            boolean sawOther = false;
            int x = coord.x, y = coord.y;
            for (int dd = 0; dd < boardSize; dd++) {
                x += DX[ii];
                y += DY[ii];
                if (!inBounds(x, y)) break;
                Loader.Piece piece = board.get(new Loader.Coord(x, y));
                if (piece == null) break;
                else if (piece != color) sawOther = true;
                else if (sawOther) return true;
                else break;
            }
        }

        return false;
    }

    public void applyPlay (Map<Loader.Coord, Loader.Piece> board, Loader.Piece color, Loader.Coord coord) {
        List<Loader.Coord> toFlip = new ArrayList<>();
        board.put(coord, color);
        for (int ii = 0; ii < DX.length; ii++) {
            int x = coord.x, y = coord.y;
            for (int dd = 0; dd < boardSize; dd++) {
                x += DX[ii];
                y += DY[ii];
                if (!inBounds(x, y)) break;
                Loader.Coord fc = new Loader.Coord(x, y);
                Loader.Piece piece = board.get(fc);
                if (piece == null) break;
                else if (piece != color) toFlip.add(fc);
                else {
                    for (Loader.Coord tf : toFlip) board.put(tf, color);
                    break;
                }
            }
            toFlip.clear();
        }
    }

    public List<Loader.Coord> legalPlays(Map<Loader.Coord, Loader.Piece> board, Loader.Piece color) {
        List<Loader.Coord> plays = new ArrayList<>();
        for (int yy = 0; yy < boardSize; yy++) {
            for(int xx = 0; xx < boardSize; xx++) {
                Loader.Coord coord = new Loader.Coord(xx, yy);
                if(board.containsKey(coord)) continue;
                if(isLegalPlay(board, color, coord)) plays.add(coord);
            }
        }
        return plays;
    }

    private final boolean inBounds (int x, int y) {
        return (x >= 0) && (x < boardSize) && (y >= 0) && (y < boardSize);
    }

    protected static final int[] DX = {-1, 0, 1, -1, 1, -1, 0, 1};
    protected static final int[] DY = {-1, -1, -1, 0, 0, 1, 1, 1};
}
