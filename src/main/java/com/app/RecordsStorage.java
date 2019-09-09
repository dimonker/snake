package com.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RecordsStorage {

    String folderPath = new File(RecordsStorage.class.getProtectionDomain().getCodeSource().getLocation().getPath())
            .getParentFile().getAbsolutePath();

    public ArrayList<String> getAllRecords() {
        ArrayList<String> records = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(folderPath, "results.txt"))) {
            stream.forEach(x -> {
                records.add(x);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public ObservableList<Record> getAllObservableListRecords() {
        ObservableList<Record> records = FXCollections.observableArrayList();

        try (Stream<String> stream = Files.lines(Paths.get(folderPath, "results.txt"))) {
            AtomicInteger pos = new AtomicInteger();
            stream.forEach(x -> {
                records.add(new Record(pos.incrementAndGet(), Integer.valueOf(x.split(",")[0]), x.split(",")[1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void saveAllRecords(ArrayList<String> records) {
        try {

            Files.write(Paths.get(folderPath, "results.txt"), records, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
