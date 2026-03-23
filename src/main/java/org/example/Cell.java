package org.example;

public class Cell {
    public int r;  // row
    public int c;  // column
    public int f;  // figure

    @Override
    public String toString() {
        return String.format("Cell(r=%d, c=%d, f=%d)", r, c, f);
    }
}
