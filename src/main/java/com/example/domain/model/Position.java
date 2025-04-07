package com.example.domain.model;

import java.util.Objects;

/**
 * A simple data class representing an (x, y) coordinate on the board.
 */
public class Position {

    public Position() {}

    /**
     * Compares this Position with another for equality based on x and y.
     */
    @Override
    public boolean equals(Object o) {}

    /**
     * A consistent hash code based on x and y.
     */
    @Override
    public int hashCode() {
        // return Objects.hash(x, y);
    }

    /**
     * For debugging or logging.
     */
    @Override
    public String toString() {
        // return "(" + x + ", " + y + ")";
    }
}
