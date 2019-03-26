package com.app;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    private int x;
    private int y;
    private boolean crashed = false;
    private ArrayList<Point> bodyCoordinates = new ArrayList<>();

    private Direction curDirection = Direction.RIGHT;
    private Direction nextDirection = Direction.RIGHT;

    public Snake(){
        x = 3;
        y = 2;
        bodyCoordinates.add(new Point(3,2));
        bodyCoordinates.add(new Point(2, 2));
        bodyCoordinates.add(new Point(0,0));
    }

    public void setNextDirection(Direction direction){
        if (curDirection == Direction.RIGHT && direction != Direction.LEFT)
            nextDirection = direction;
        if (curDirection == Direction.LEFT && direction != Direction.RIGHT)
            nextDirection = direction;
        if (curDirection == Direction.DOWN && direction != Direction.UP)
            nextDirection = direction;
        if (curDirection == Direction.UP && direction != Direction.DOWN)
            nextDirection = direction;
    }

    public Point nextCoordinate(){
        int nextX = x, nextY = y;
        if (nextDirection == Direction.RIGHT){
            nextX = x + 1;
            if (nextX > Game.WIDTH - 1)
                nextX = 0;
        }
        if (nextDirection == Direction.LEFT){
            nextX = x - 1;
            if (nextX < 0)
                nextX = Game.WIDTH - 1;
        }
        if (nextDirection == Direction.UP){
            nextY = y - 1;
            if (nextY < 0)
                nextY = Game.HEIGHT - 1;
        }
        if (nextDirection == Direction.DOWN){
            nextY = y + 1;
            if (nextY > Game.HEIGHT - 1)
                nextY = 0;
        }
        return new Point(nextX, nextY);
    }

    public void update(){
        Point p = nextCoordinate();
        x = p.getX();
        y = p.getY();
        for (int i = bodyCoordinates.size() - 1; i > 0; i--){
            bodyCoordinates.set(i, bodyCoordinates.get(i - 1));
        }
        bodyCoordinates.set(0, new Point(x, y));
        curDirection = nextDirection;
    }

    public void render(GraphicsContext gc){
        if (crashed)
            gc.setFill(new Color(0,0,0,1));
        else
            gc.setFill(Color.web("#4572e7"));

        gc.fillOval(x * Game.CELL_SIZE, y * Game.CELL_SIZE, Game.CELL_SIZE, Game.CELL_SIZE);

        gc.setFill(Color.web("#4572e7"));
        for (int i = 1; i < bodyCoordinates.size(); i++){
            Point p = bodyCoordinates.get(i);
            gc.fillOval(p.getX() * Game.CELL_SIZE, p.getY() * Game.CELL_SIZE, Game.CELL_SIZE, Game.CELL_SIZE);
        }
    }

    public ArrayList<Point> getBodyCoordinates() {
        return bodyCoordinates;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public void eatApple(){
        bodyCoordinates.add(new Point(0, 0));
    }
}
