package com.cgi.timereport.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class MenuSample extends Application {

    private final PageData[] pages = new PageData[] {
            new PageData("Apple",
                    "The apple is the pomaceous fruit of the apple tree, species Malus "
                            + "domestica in the rose family (Rosaceae). It is one of the most "
                            + "widely cultivated tree fruits, and the most widely known of "
                            + "the many members of genus Malus that are used by humans. "
                            + "The tree originated in Western Asia, where its wild ancestor, "
                            + "the Alma, is still found today.",
                    "Malus domestica"),
            new PageData("Hawthorn",
                    "The hawthorn is a large genus of shrubs and trees in the rose "
                            + "family, Rosaceae, native to temperate regions of the Northern "
                            + "Hemisphere in Europe, Asia and North America. "
                            + "The name hawthorn was "
                    + "originally applied to the species native to northern Europe, "
                    + "especially the Common Hawthorn C. monogyna, and the unmodified "
                    + "name is often so used in Britain and Ireland.",
                    "Crataegus monogyna"),
            new PageData("Ivy",
                    "The ivy is a flowering plant in the grape family (Vitaceae) native to"
                            + " eastern Asia in Japan, Korea, and northern and eastern China. "
                            + "It is a deciduous woody vine growing to 30 m tall or more given "
                            + "suitable support,  attaching itself by means of numerous small "
                            + "branched tendrils tipped with sticky disks.",
                    "Parthenocissus tricuspidata"),
            new PageData("Quince",
                    "The quince is the sole member of the genus Cydonia and is native to "
                            + "warm-temperate southwest Asia in the Caucasus region. The "
                            + "immature fruit is green with dense grey-white pubescence, most "
                            + "of which rubs off before maturity in late autumn when the fruit "
                            + "changes color to yellow with hard, strongly perfumed flesh.",
                    "Cydonia oblonga")
    };

    final String[] viewOptions = new String[] {
            "Title",
            "Binomial name",
            "Picture",
            "Description"
    };

    private final Entry<String, Effect>[] effects = new Entry[] {
            new SimpleEntry<String, Effect>("Sepia Tone", new SepiaTone()),
            new SimpleEntry<String, Effect>("Glow", new Glow()),
            new SimpleEntry<String, Effect>("Shadow", new DropShadow())
    };

    private final ImageView pic = new ImageView();
    private final Label name = new Label();
    private final Label binName = new Label();
    private final Label description = new Label();
    private int currentIndex = -1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Menu Sample");

        Scene scene = new Scene(new VBox(), 400, 350);
        scene.setFill(Color.OLDLACE);

        name.setFont(new Font("Verdana Bold", 22));
        binName.setFont(new Font("Arial Italic", 10));
        pic.setFitHeight(150);
        pic.setPreserveRatio(true);
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);

        shuffle();

        MenuBar menuBar = new MenuBar();

        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.getChildren().addAll(name, binName, pic, description);

        // - - - - - Menu File - - - - -
        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Shuffle", new ImageView(getClass().getClassLoader().getResource("menusample/shuffle.png").toString()));

        add.setOnAction(event -> {
            shuffle();
            vbox.setVisible(true);
        });

        MenuItem clear = new MenuItem("Clear");
        clear.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        clear.setOnAction(event -> vbox.setVisible(true));

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> System.exit(0));

        menuFile.getItems().addAll(add, clear, new SeparatorMenuItem(), exit);

        // - - - - - Menu Edit - - - - - -
        Menu menuEdit = new Menu("Edit");

        // Picture Effect Menu
        Menu menuEffect = new Menu("Picture Effect");
        final ToggleGroup toggleGroup = new ToggleGroup();
        for (Entry<String, Effect> effectEntry: effects) {
            RadioMenuItem itemEffect = new RadioMenuItem(effectEntry.getKey());
            itemEffect.setUserData(effectEntry.getValue());
            itemEffect.setToggleGroup(toggleGroup);
            menuEffect.getItems().add(itemEffect);
        }

        // No Effect Menu
        final MenuItem noEffects = new MenuItem("No Effects");
        noEffects.setOnAction(event -> {
            pic.setEffect(null);
            toggleGroup.getSelectedToggle().setSelected(false);
        });

        // Processing menu item selection
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                Effect effect = (Effect) toggleGroup.getSelectedToggle().getUserData();
                pic.setEffect(effect);
            }
        });

        menuEdit.getItems().addAll(menuEffect, noEffects);




        // - - - - - Menu View - - - - - -
        Menu menuView = new Menu("View");

        // - - - Creating four check menu items within the start method - - -
        CheckMenuItem titleView = createMenuItem("Title", name);
        CheckMenuItem binNameView = createMenuItem("Binomial Name", binName);
        CheckMenuItem picView = createMenuItem("Picture", pic);
        CheckMenuItem descriptionView =createMenuItem("Description", description);
        menuView.getItems().addAll(titleView, binNameView, picView, descriptionView);



        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private CheckMenuItem createMenuItem(String title, final Node node) {
        CheckMenuItem checkMenuItem = new CheckMenuItem(title);
        checkMenuItem.setSelected(true);
        checkMenuItem.selectedProperty().addListener((observable, old_val, new_val) -> node.setVisible(new_val));
        return checkMenuItem;
    }


    private void shuffle() {
        int i = currentIndex;
        while (i == currentIndex) {
            i = (int) (Math.random() * pages.length);
        }
        pic.setImage(pages[i].image);
        name.setText(pages[i].name);
        binName.setText("(" + pages[i].binNames + ")");
        description.setText(pages[i].description);
        currentIndex = i;
    }

    private class PageData {
        String name;
        String description;
        String binNames;
        Image image;
        PageData(String name, String description, String binNames) {
            this.name = name;
            this.description = description;
            this.binNames = binNames;
            image = new Image(getClass().getClassLoader().getResource("menusample/" + name + ".jpg").toString());
        }
    }



    public static void main(String[] args) {
        launch();
    }


}
