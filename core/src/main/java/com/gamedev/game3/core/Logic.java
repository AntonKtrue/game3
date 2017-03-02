package com.gamedev.game3.core;

import com.gamedev.game3.core.components.Coord;
import com.gamedev.game3.core.components.Piece;

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

    public boolean isLegalPlay (Map<Coord, Piece> board, Piece color, Coord coord) {
        if(!inBounds(coord.x, coord.y) || board.containsKey(coord)) return false;
        for (int ii = 0; ii < DX.length; ii++) {
            boolean sawOther = false;
            int x = coord.x, y = coord.y;
            for (int dd = 0; dd < boardSize; dd++) {
                x += DX[ii];
                y += DY[ii];
                if (!inBounds(x, y)) break;
                Piece piece = board.get(new Coord(x, y));
                if (piece == null) break;
                else if (piece != color) sawOther = true;
                else if (sawOther) return true;
                else break;
            }
        }

        return false;
    }

    public void applyPlay (Map<Coord, Piece> board, Piece color, Coord coord) {
        List<Coord> toFlip = new ArrayList<>();
        board.put(coord, color);
        for (int ii = 0; ii < DX.length; ii++) {
            int x = coord.x, y = coord.y;
            for (int dd = 0; dd < boardSize; dd++) {
                x += DX[ii];
                y += DY[ii];
                if (!inBounds(x, y)) break;
                Coord fc = new Coord(x, y);
                Piece piece = board.get(fc);
                if (piece == null) break;
                else if (piece != color) toFlip.add(fc);
                else {
                    for (Coord tf : toFlip) board.put(tf, color);
                    break;
                }
            }
            toFlip.clear();
        }
    }

    public List<Coord> legalPlays(Map<Coord, Piece> board, Piece color) {
        List<Coord> plays = new ArrayList<>();
        for (int yy = 0; yy < boardSize; yy++) {
            for(int xx = 0; xx < boardSize; xx++) {
                Coord coord = new Coord(xx, yy);
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
