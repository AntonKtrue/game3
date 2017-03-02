package com.gamedev.game3.core.components;



/**
 * Created by Anton on 02.03.2017.
 */
public enum Piece {
    BLACK, WHITE;
    public Piece next () { return values()[(ordinal()+1) % values().length]; }
}
