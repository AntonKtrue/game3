package com.gamedev.game3.core.components;

/**
 * Created by Anton on 02.03.2017.
 */
public class Coord {
    public final int x, y;

    public Coord (int x, int y) {
        assert x >= 0 && y >= 0;
        this.x = x;
        this.y = y;
    }

    public boolean equals (Coord other) {
        return other.x == x && other.y == y;
    }
    @Override public boolean equals (Object other) {
        return (other instanceof Coord) && equals((Coord)other);
    }
    @Override public int hashCode () { return x ^ y; }
    @Override public String toString () { return "+" + x + "+" + y; }
}
