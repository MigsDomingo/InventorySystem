package com.company;/**
 * Created by StarDiamond on 12/05/2017.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

//Name convention for UI: name first before Type.
// e.g thisButton, yourLabel, etc.
public class Main extends Application {

    private final String CSS_URL_MAIN = "css/main.css";
    private final String CSS_URL_SETTINGS = "css/settings.css";
    private final String ICON_URL_PMIC = "images/pmic_icon.png";
    private final String ICON_URL_BACK = "images/back.png";

    private TableView table = new TableView();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Group root = new Group();
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 1280, 720, Color.BLACK);

        // Load CSS
        try {
            mainScene.getStylesheets().add(CSS_URL_MAIN);
            mainScene.getStylesheets().add(CSS_URL_SETTINGS);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        /*
         * ==========================
         * PORTION START: WELCOME
         * ==========================
        */
        HBox welcomeHbox = new HBox();
        welcomeHbox.getStyleClass().add("hbox-welcome");

        Label welcomeLabel = new Label();
        welcomeLabel.setText("Panday Inventory System");
        welcomeLabel.getStyleClass().add("label-welcome");

        welcomeHbox.getChildren().add(welcomeLabel);
        /*
         * ===========================
         * PORTION START: MAIN BUTTONS
         * ===========================
        */
        HBox hboxButtons = new HBox();
        hboxButtons.getStyleClass().add("hbox-buttons");

        Button viewInvButton = new Button();
        Button incomingButton = new Button();
        Button outgoingButton = new Button();
        Button settingsButton = new Button();

        viewInvButton.getStyleClass().add("button-common");
        incomingButton.getStyleClass().add("button-common");
        outgoingButton.getStyleClass().add("button-common");
        settingsButton.getStyleClass().add("button-common");

        viewInvButton.setText("View Inventory");
        incomingButton.setText("Incoming");
        outgoingButton.setText("Outgoing");
        settingsButton.setText("Settings");

        viewInvButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                ViewInvScene viewInvScene = new ViewInvScene(primaryStage, mainScene);
                primaryStage.setScene(viewInvScene.init());
            }
        });
        incomingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                IncomingScene incomingScene = new IncomingScene(primaryStage, mainScene);
                primaryStage.setScene(incomingScene.init());
            }
        });
        outgoingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                OutgoingScene outgoingScene = new OutgoingScene(primaryStage, mainScene);
                primaryStage.setScene(outgoingScene.init());
            }
        });
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                SettingScene settingsScene = new SettingScene(primaryStage, mainScene);
                primaryStage.setScene(settingsScene.init());
            }
        });

        hboxButtons.getChildren().add(viewInvButton);
        hboxButtons.getChildren().add(incomingButton);
        hboxButtons.getChildren().add(outgoingButton);
        hboxButtons.getChildren().add(settingsButton);

        root.setTop(welcomeHbox);
        root.setCenter(hboxButtons);
        // Set stage's display settings
        primaryStage.setTitle("Panday Inventory System");
        primaryStage.getIcons().add(new Image("images/pmic_icon.png"));
        primaryStage.setScene(mainScene);
        primaryStage.show();


    }
}
