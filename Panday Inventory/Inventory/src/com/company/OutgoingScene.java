package com.company;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by StarDiamond on 16/05/2017.
 */
public class OutgoingScene {

    Scene outgoingScene;
    private final String ICON_URL_BACK = "images/back.png";


    public OutgoingScene(Stage primaryStage, Scene mainScene) {
        BorderPane rootSettings = new BorderPane();
        outgoingScene = new Scene(rootSettings, 1280, 720, Color.BLACK);

        HBox backBtnHbox = new HBox();
        backBtnHbox.setPadding(new Insets(30));

        ImageView backImage = new ImageView(ICON_URL_BACK);
        backImage.setFitHeight(100);
        backImage.setFitWidth(100);
        backImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                primaryStage.setScene(mainScene);
                //consumes ensure only this guy uses this listener
                event.consume();
            }
        });
        backBtnHbox.getChildren().add(backImage);
        rootSettings.setTop(backBtnHbox);

    }

    public Scene init() {
        return outgoingScene;
    }

}
