package com.app;

public class Game {
    public final static int CELL_SIZE = 50;
    public final static int WIDTH = 20;
    public final static int HEIGHT = 16;
    private int[][] map;

    public int[][] getMap() {
        return map;
    }

    public void setMap(int x, int y) {
        this.map = new int[x][y];
    }
}
