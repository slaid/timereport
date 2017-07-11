package com.cgi.timereport.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class UI extends Application {

    private Button open;
    private Button execute;
    private Button showConsole;
    private FileChooser fileChooser;
    private Scene scene;
    private ListView<String> listView;
    private ObservableList<String> list;
    private StackPane root;


    /**
     * Initiates the Graphical components
     */
    private void initComponents() {
        open = new Button("Open");
        execute = new Button("Execute");
        showConsole = new Button("Show Console");
        execute.setDisable(true);
        fileChooser = new FileChooser();
        list = FXCollections.observableArrayList();
        listView = new ListView<>(list);
        root = new StackPane();
    }

    /**
     * Setups the components
     */
    private void setupComponents() {
        listView.setPrefWidth(100);
        listView.setPrefHeight(70);
        listView.setEditable(true);
        list.add("Mike");
        list.add("Marco");
        list.add("Inês");
        list.add("Marisa");
        list.add("Angela");
        list.add("Joana");
        listView.setItems(list);
        root.getChildren().add(listView);
        root.getChildren().add(open);
        root.getChildren().add(execute);
        root.getChildren().add(showConsole);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Monthly Report");
        initComponents();
        setupComponents();
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    /**
     * Launches the User Interface
     */
    public void start(String[] args) {
        launch(args);
    }

}
