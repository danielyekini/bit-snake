package com.example.domain.model;

import java.util.Objects;

/**
 * A simple data class representing an (x, y) coordinate on the board.
 */
public class Position {

    public final int x;
    public final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compares this Position with another for equality based on x and y.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Position)) return false;
        if(this == o) return true;
        Position other = (Position) o;
        return this.x == other.x && this.y == other.y;
    }

    /**
     * A consistent hash code based on x and y.
     */
    @Override
    public int hashCode() {
         return Objects.hash(x, y);
    }

    /**
     * For debugging or logging.
     */
    @Override
    public String toString() {
         return "(" + x + ", " + y + ")";
    }
}
