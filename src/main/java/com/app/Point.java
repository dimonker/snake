package com.app;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj.getClass() ==  Point.class)
            return x == ((Point) obj).x && y == ((Point) obj).y;
        else
            return false;
    }
}
