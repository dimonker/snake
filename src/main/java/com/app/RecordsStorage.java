package com.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RecordsStorage {

    public ArrayList<String> getAllRecords(){
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
        return records;
    }

    public ObservableList<Record> getAllObservableListRecords(){
        ObservableList<Record> records = FXCollections.observableArrayList();
        try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource("results.txt").toURI()))){
            AtomicInteger pos = new AtomicInteger();
            stream.forEach(x -> {
                records.add(new Record(pos.incrementAndGet(),Integer.valueOf(x.split(",")[0]),x.split(",")[1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void saveAllRecords(ArrayList<String> records){
        try {
            Files.write(Paths.get(getClass().getClassLoader().getResource("results.txt").toURI()), records, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
