package com.app;

import com.app.controllers.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class Game {
    public final static int CELL_SIZE = 50;
    public final static int WIDTH = 16;
    public final static int HEIGHT = 16;

    private ArrayList<Point> walls = new ArrayList<>();
    private Long lastNanoTime = System.nanoTime();
    private GraphicsContext gc;
    private AnimationTimer animationTimer;
    private Snake snake = new Snake();
    private Image appleImg = new Image(getClass().getClassLoader().getResource("apple.png").toExternalForm());
    private Point appleCoordinates;
    private GameController gameController;
    private int currentScope;
    private boolean over = false;
    private int level;

    public Game(GameController gameController, GraphicsContext gc, int level){
        this.gameController = gameController;
        this.gc = gc;
        this.level = level;
        walls.add(new Point(0,0));
        walls.add(new Point(WIDTH - 1, 0));
        walls.add(new Point(0, HEIGHT / 2));
        walls.add(new Point(0, HEIGHT - 1));
        walls.add(new Point(WIDTH - 1, HEIGHT / 2));
        walls.add(new Point(WIDTH - 1, HEIGHT - 1));
        spawnApple();
        spawnWalls();
    }


    public void keyPressed(String key){
        switch (key){
            case "UP":
                snake.setNextDirection(Direction.UP);
                break;
            case "DOWN":
                snake.setNextDirection(Direction.DOWN);
                break;
            case "LEFT":
                snake.setNextDirection(Direction.LEFT);
                break;
            case "RIGHT":
                snake.setNextDirection(Direction.RIGHT);
                break;
        }
    }

    private void initAnimationTimer() {
        animationTimer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                //update every 100 ms
                if (now - lastNanoTime < 100000000){
                    return;
                }
                lastNanoTime = now;

                Point p = snake.nextCoordinate();
                if(checkCollision(p)){
                    snake.setCrashed(true);
                    over = true;
                    saveResult();
                    this.stop();
                }
                else
                    snake.update();

                renderMap(gc);
                renderApple(gc);
                renderWalls(gc);
                snake.render(gc);
            }
        };
    }

    private boolean checkCollision(Point nextHeadCoordinate){
        ArrayList<Point> snakeCoordinates = snake.getBodyCoordinates();
        if (snakeCoordinates.contains(nextHeadCoordinate))
            return true;

        if (walls.contains(nextHeadCoordinate)){
            return true;
        }
        
        for (Point wall : walls){
            if (nextHeadCoordinate.equals(wall))
                return true;
        }

        if (nextHeadCoordinate.equals(appleCoordinates)){
            snake.eatApple();
            gameController.setCurrentScope(++currentScope);
            spawnApple();
        }

        return false;
    }

    private int randomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void spawnApple(){
        Point p = new Point(randomInt(0, WIDTH - 1), randomInt(0, HEIGHT - 1));
        if (walls.contains(p) || snake.getBodyCoordinates().contains(p))
            spawnApple();
        else
            appleCoordinates = p;
    }

    private void spawnWalls(){
        double coeff = 0;
        if (level == 0)
            coeff = 0.5;
        else if (level == 1)
            coeff = 1;
        else if (level == 2)
            coeff = 2;
        int needWalls = (int) (coeff * Math.sqrt(HEIGHT * WIDTH)) - 6;
        while (needWalls > 0){
            Point p = new Point(randomInt(0, WIDTH - 1), randomInt(0, HEIGHT - 1));
            if (!walls.contains(p) && !appleCoordinates.equals(p) && !snake.getBodyCoordinates().contains(p)){
                walls.add(p);
                needWalls--;
            }
        }
    }

    private void renderMap(GraphicsContext gc){
        Predicate<Integer> predicate1 = (x) -> x % 2 == 0;
        Predicate<Integer> predicate2 = (x) -> x % 2 == 1;

        Predicate<Integer> currPredicate = predicate1;
        for (int i = 0; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT; j++){
                if (currPredicate.test(j))
                    gc.setFill(Color.web("#aad751"));
                else
                    gc.setFill(Color.web("#a2d149"));

                gc.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }

            if (i % 2 == 1)
                currPredicate = predicate1;
            else
                currPredicate = predicate2;

        }
    }

    private void renderApple(GraphicsContext gc){
        gc.drawImage(appleImg, appleCoordinates.getX() * CELL_SIZE, appleCoordinates.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    private void renderWalls(GraphicsContext gc){
        gc.setFill(new Color(0.5, 0.5, 0.5, 1));
        for (Point wall : walls)
            gc.fillRect(wall.getX() * CELL_SIZE, wall.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    public void start(){
        initAnimationTimer();
        animationTimer.start();
    }

    public void stop(){
        animationTimer.stop();
    }

    public void saveResult(){
        ArrayList<String> records = new RecordsStorage().getAllRecords();

        for (int i = 0; i < records.size(); i++)
            if (currentScope >= Integer.valueOf(records.get(i).split(",")[0])){
                records.add(i, currentScope + "," + new Date());
                if (records.size() > 10)
                    records.remove(records.size() - 1);
                break;
            }


        new RecordsStorage().saveAllRecords(records);
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
