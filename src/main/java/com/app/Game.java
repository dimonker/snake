package com.app;

import com.app.controllers.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Game {
    public final static int CELL_SIZE = 50;
    public final static int WIDTH = 16;
    public final static int HEIGHT = 16;

    private ArrayList<Point> walls = new ArrayList<>();
    private Long lastNanoTime = System.nanoTime();
    private AnimationTimer animationTimer;
    private Snake snake = new Snake();
    private Image appleImg = new Image(getClass().getClassLoader().getResource("apple.png").toExternalForm());
    private Point appleCoordinates = new Point(5, 5);
    private GameController gameController;
    private int currentScope;
    private int bestScope;

    public Game(GameController gameController){
        this.gameController = gameController;
        walls.add(new Point(0,0));
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

    public void initAnimationTimer(GraphicsContext gc) {
        animationTimer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                //1000000000
                //update every 100 ms
                if (now - lastNanoTime < 100000000){
                    return;
                }
                lastNanoTime = now;

                int size = Game.CELL_SIZE;
                Predicate<Integer> predicate1 = (x) -> x % 2 == 0;
                Predicate<Integer> predicate2 = (x) -> x % 2 == 1;

                Predicate<Integer> currPredicate = predicate1;
                for (int i = 0; i < WIDTH; i++){
                    for (int j = 0; j < HEIGHT; j++){
                        if (currPredicate.test(j))
                            gc.setFill(Color.web("#aad751"));
                        else
                            gc.setFill(Color.web("#a2d149"));

                        gc.fillRect(i * size, j * size, size, size);
                    }

                    if (i % 2 == 1)
                        currPredicate = predicate1;
                    else
                        currPredicate = predicate2;

                }

                Point p = snake.nextCoordinate();
                if(checkCollision(p)){
                    snake.setCrashed(true);
                    this.stop();
                }
                else
                    snake.update();

                renderApple(gc);
                renderWalls(gc);
                snake.render(gc);
            }
        };
    }

    private boolean checkCollision(Point p){
        ArrayList<Point> snakeCoordinates = snake.getBodyCoordinates();
        if (snakeCoordinates.contains(p))
            return true;

        if (walls.contains(p)){
            return true;
        }
        
        for (Point wall : walls){
            if (p.equals(wall))
                return true;
        }

        if (p.equals(appleCoordinates)){
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

    private void renderApple(GraphicsContext gc){

        gc.drawImage(appleImg, appleCoordinates.getX() * CELL_SIZE, appleCoordinates.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    private void renderWalls(GraphicsContext gc){
        gc.setFill(new Color(0.5, 0.5, 0.5, 1));
        for (Point wall : walls)
            gc.fillRect(wall.getX() * CELL_SIZE, wall.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    public void start(){
        animationTimer.start();
    }

    public void stop(){
        animationTimer.stop();
    }

    public void saveResult(){
        ArrayList<String> records = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource("results.txt").toURI()))){
            stream.forEach(x -> {
                    records.add(x);
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < records.size(); i++){
            if (currentScope >= Integer.valueOf(records.get(i).split(",")[0])){
                records.add(i, currentScope + "," + new Date());
                if (records.size() > 10)
                    records.remove(records.size() - 1);
                break;
            }

        }

        try {
            Files.write(Paths.get(getClass().getClassLoader().getResource("results.txt").toURI()), records, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
