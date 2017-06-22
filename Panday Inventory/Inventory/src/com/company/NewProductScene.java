package com.company;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by Migs on 6/22/2017.
 */
public class NewProductScene {

    Stage productStage;
    Label brandLabel, modelLabel, descriptionLabel, priceLabel;
    TextField brandTextField, modelTextField, descriptionTextField, priceTextField;
    Button confirmButton, cancelButton;
    public NewProductScene() {
        productStage = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene thisScene = new Scene(borderPane, 400, 400);
        VBox mainVBox = new VBox();
        mainVBox.setPadding(new Insets(10));
        mainVBox.setSpacing(10);

        brandLabel = new Label("Brand");
        brandTextField = new TextField();
        brandTextField.setPromptText("Brand name here");
        modelLabel = new Label("Label");
        modelTextField = new TextField();
        modelTextField.setPromptText("Model name here");
        descriptionLabel = new Label ("Item Description");
        descriptionTextField = new TextField();
        descriptionTextField.setPromptText("Item description here");
        priceLabel = new Label("Price");
        priceTextField = new TextField();
        priceTextField.setPromptText("Price here");
        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        confirmButton = new Button("Confirm");
        confirmButton.setPadding(new Insets(20));

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage confirmStage = new Stage();
                BorderPane confirmPane = new BorderPane();
                Scene confirmScene = new Scene(confirmPane,200, 200);
                confirmPane.setTop(new Label("Confirm create a new product?"));
                HBox mainHBox = new HBox();
                mainHBox.setSpacing(10);
                //mainHBox.setPadding(new Insets(10));
                Button okButton= new Button("Ok");
                okButton.setPadding(new Insets(10));
                okButton.setOnAction(event -> {
                    DatabaseHelper.connect();
                    DatabaseHelper.insert("INSERT INTO product VALUES(\'");
                    confirmStage.close();
                });
                Button cancelButton = new Button("Cancel");
                cancelButton.setPadding(new Insets(10));
                cancelButton.setOnAction(event -> {
                    confirmStage.close();
                });
                mainHBox.getChildren().add(okButton);
                mainHBox.getChildren().add(cancelButton);
                confirmPane.setCenter(mainHBox);

                confirmStage.setScene(confirmScene);
                confirmStage.show();

            }
        });
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            productStage.close();
        });
        cancelButton.setPadding(new Insets(20));
        buttonsHBox.getChildren().add(confirmButton);
        buttonsHBox.getChildren().add(cancelButton);

        mainVBox.getChildren().add(brandLabel);
        mainVBox.getChildren().add(brandTextField);
        mainVBox.getChildren().add(modelLabel);
        mainVBox.getChildren().add(modelTextField);
        mainVBox.getChildren().add(descriptionLabel);
        mainVBox.getChildren().add(descriptionTextField);
        mainVBox.getChildren().add(priceLabel);
        mainVBox.getChildren().add(priceTextField);
        mainVBox.getChildren().add(buttonsHBox);

        borderPane.setCenter(mainVBox);
        productStage.setScene(thisScene);
        productStage.show();
    }
}
