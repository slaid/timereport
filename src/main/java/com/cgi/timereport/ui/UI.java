package com.cgi.timereport.ui;

import com.cgi.timereport.exporter.RejectedExporter;
import com.cgi.timereport.util.FXUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.PrintStream;

public class UI extends Application {

    private Button open;
    private Button execute;
    private Button showConsole;
    private Scene scene;
    private ListView<File> listView;
    private ObservableList<File> list;
    private StackPane root;
    private Console console;
    private TextArea textArea;
    private PrintStream printStream;
    private VBox vboxLeft;
    private VBox vboxRight;
    private BorderPane borderPane;
    private FileChooser fileChooser;
    private Desktop desktop;
    private String tmpStringFile;
    private RejectedExporter rejectedExporter;

    /**
     * Initiates the Graphical components
     */
    private void initComponents() {
        open = new Button("Open");
        execute = new Button("Execute");
        showConsole = new Button("Console");
        execute.setDisable(true);
        fileChooser = new FileChooser();
        list = FXCollections.observableArrayList();
        listView = new ListView<>(list);
        root = new StackPane();
        borderPane = new BorderPane();
        vboxLeft = new VBox();
        vboxRight = new VBox();
        desktop = Desktop.getDesktop();
    }


    /**
     * Setups the components
     */
    private void setupComponents() {
        listView.setPrefWidth(200);
        listView.setPrefHeight(100);
        listView.setEditable(true);
        // listView.setItems(list);
        textArea = FXUtil.build(new TextArea(), textArea1 -> {
            textArea1.prefHeight(800);
            textArea1.prefWidth(600);
            textArea1.setWrapText(true);
            textArea1.setEditable(false);
        });
        vboxLeft.setAlignment(Pos.CENTER);
        vboxLeft.setPadding(new Insets(15,12, 15, 12));
        vboxRight.setAlignment(Pos.CENTER);
        vboxRight.setPadding(new Insets(15, 12, 15, 12));
        vboxRight.setSpacing(7);
        vboxLeft.getChildren().add(listView);
        vboxRight.getChildren().addAll(open, execute, showConsole);
        borderPane.setPrefSize(100, 100);
        borderPane.setLeft(vboxLeft);
        borderPane.setRight(vboxRight);
        console = new Console(textArea);
        printStream = new PrintStream(console, true);
        // borderPane.setStyle("-fx-background-image: url(hello_kitty.jpg);");

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Service Monthly Report");
        initComponents();
        setupComponents();

        open.setOnAction(event -> {
            configureFileChooser(fileChooser);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                // openFile(file);
                System.out.println("Ficheiro adicionado à lista: " + file.getName());
                list.add(file);
                System.out.println(file.getAbsolutePath());
                rejectedExporter = new RejectedExporter(file.getAbsolutePath());
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (execute.isDisable())
                execute.setDisable(false);
            // System.out.println("Observable Value is: " + observable.toString());
            // System.out.println("Old Value is: " + oldValue);
            // System.out.println("New Value is: " + newValue);
        });

        // When Console button pressed it shows all logs in a new Window
        showConsole.setOnAction(event -> {
            if(!showConsole.isDisable()) {
                Stage stage = new Stage();
                stage.setTitle("Console Log");
                HBox hbox = new HBox();
                hbox.getChildren().addAll(textArea);
                Scene scene1 = new Scene(hbox);
                stage.setScene(scene1);
                stage.setMinWidth(500);
                stage.setMinHeight(250);
                stage.setOnCloseRequest(event1 -> {
                    showConsole.setDisable(false);
                });
                stage.show();
                showConsole.setDisable(true);
            }
        });

        execute.setOnAction(event -> {
            rejectedExporter.execute();
        });

        System.setOut(printStream);
        primaryStage.setScene(new Scene(borderPane, 300, 150));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Select Excel File");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );
    }


    /**
     * Launches the User Interface
     */
    public void start(String[] args) {
        launch(args);
    }

}
