package com.app;



public class Snake {
    private int x;
    private int y;

    private Direction curDirection = Direction.RIGHT;
    private Direction nextDirection = Direction.RIGHT;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCurDirection(Direction direction){
        curDirection = direction;
    }

    public Direction getCurDirection(){
        return curDirection;
    }

    public Direction getNextDirection(){
        return nextDirection;
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

    public void update(){
        if (nextDirection == Direction.RIGHT){
            x = x + 1;
            if (x > Game.WIDTH - 1)
                x = 0;
        }
        if (nextDirection == Direction.LEFT){
            x = x - 1;
            if (x < 0)
                x = Game.WIDTH - 1;
        }
        if (nextDirection == Direction.UP){
            y = y - 1;
            if (y < 0)
                y = Game.HEIGHT - 1;
        }
        if (nextDirection == Direction.DOWN){
            y = y + 1;
            if (y > Game.HEIGHT - 1)
                y = 0;
        }
        curDirection = nextDirection;
    }
}
