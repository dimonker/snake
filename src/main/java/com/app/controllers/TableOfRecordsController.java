package com.app.controllers;

import com.app.Record;
import com.app.RecordsStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TableOfRecordsController {
    @FXML
    Stage stage;

    @FXML
    TableView<Record> table;

    @FXML
    TableColumn<Record, Integer> scoreColumn;

    @FXML
    TableColumn<Record, String> dateColumn;

    @FXML
    TableColumn<Record, String> positionColumn;

    private ObservableList<Record> records = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        stage.setTitle("Table of records");
        records = new RecordsStorage().getAllObservableListRecords();

        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("Score"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("Position"));

        table.setItems(records);
        stage.show();
    }
}
