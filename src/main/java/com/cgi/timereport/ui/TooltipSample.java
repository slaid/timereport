package com.cgi.timereport.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TooltipSample extends Application {

    public static void main(String[] args) {
        launch();
    }

    private final static String[] rooms = new String[] {
            "Accomodation (BB)",
            "Half Board",
            "Late Check-out",
            "Extra Bed"
    };
    private final static Integer[] rates = new Integer[] {
            100, 20, 10, 30
    };

    private final CheckBox[] checkBoxes = new CheckBox[rooms.length];
    private final Label label = new Label("Total: $0");
    private Integer sum = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Tooltip Sample");
        primaryStage.setWidth(300);
        primaryStage.setHeight(150);

        label.setFont(new Font("Arial", 20));

        for(int i=0; i<rooms.length; i++) {
            final CheckBox checkBox = checkBoxes[i] = new CheckBox(rooms[i]);
            final Integer rate = rates[i];
            final Tooltip tooltip = new Tooltip("$" + rates[i].toString());
            tooltip.setFont(new Font("Arial", 16));
            checkBox.setTooltip(tooltip);
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (checkBox.isSelected())
                    sum += rate;
                else
                    sum -= rate;
                label.setText("Total: $" + sum.toString());
            });
        }

        VBox vbox = new VBox();
        vbox.getChildren().addAll(checkBoxes);
        vbox.setSpacing(5);
        HBox root = new HBox();
        root.getChildren().add(vbox);
        root.getChildren().add(label);
        root.setSpacing(40);
        root.setPadding(new Insets(20, 10, 10, 20));

        ((Group) scene.getRoot()).getChildren().add(root);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
