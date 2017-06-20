package com.company;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by StarDiamond on 16/05/2017.
 */
public class SettingScene {

    Scene settingsScene;
    private final String ICON_URL_BACK = "images/back.png";


    public SettingScene(Stage primaryStage, Scene mainScene) {
        BorderPane rootSettings = new BorderPane();
        settingsScene = new Scene(rootSettings, 1280, 720, Color.BLACK);

        HBox backBtnHbox = new HBox();

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

        HBox addModelHbox = new HBox();
        VBox addModelVbox = new VBox();
        addModelHbox.getChildren().add(addModelVbox);

        //add UI elements
        Label brandLabel = new Label("Brand");
        TextField brandTextField = new TextField();
        Label modelLabel = new Label("Model");
        TextField modelTextField = new TextField();
        Label descriptionLabel = new Label("Description");
        TextField descriptionTextField = new TextField();
        Label serialNoLabel = new Label("Serial Number");
        TextField serialNoTextField = new TextField();
        Label priceLabel = new Label("Price");
        TextField priceTextField = new TextField();
        priceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ( !newValue.matches("\\d*\\.\\d*") )  {
                    priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                    //priceTextField.setText("");
                }
            }
        });
        Button newModelButton = new Button("Add new Model");
        Label errorLabel = new Label("");

        final String[] brandText = new String[1];
        final String[] modelText = new String[1];
        final String[] descriptionText = new String[1];
        final String[] serialNoText = new String[1];
        final String[] priceText = new String[1];
        newModelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {


                brandText[0] = brandTextField.getText();
                modelText[0] = modelTextField.getText();
                descriptionText[0] = descriptionTextField.getText();
                serialNoText[0] = serialNoTextField.getText();
                priceText[0] = priceTextField.getText();

                boolean errOccurred = false;
                String err = "";
                if ( brandText[0].equals("") ) {
                    errOccurred = true;
                    err += "Brand cannot be blank!\n";
                }
                if ( modelText[0].equals("") ) {
                    errOccurred = true;
                    err += "Model cannot be blank!\n";
                }
                if ( descriptionText[0].equals("") ) {
                    errOccurred = true;
                    err += "Description cannot be empty!\n";
                }
                if( serialNoText[0].equals("")) {
                    errOccurred = true;
                    err += "Serial Number cannot be empty!\n";
                }
                if( priceText[0].equals("") ) {
                    errOccurred = true;
                    err += "Price cannot be empty!\n";
                }

                if ( !errOccurred ) {
                    try {
                        insertProduct(serialNoText[0], brandText[0], modelText[0], descriptionText[0], Double.parseDouble(priceText[0]));
                        errorLabel.setText("Insert successful");
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
                else {
                    errorLabel.setText(err);
                    errorLabel.setVisible(true);
                }

            }
        });
        addModelVbox.getChildren().add(serialNoLabel);
        addModelVbox.getChildren().add(serialNoTextField);
        addModelVbox.getChildren().add(brandLabel);
        addModelVbox.getChildren().add(brandTextField);
        addModelVbox.getChildren().add(modelLabel);
        addModelVbox.getChildren().add(modelTextField);
        addModelVbox.getChildren().add(descriptionLabel);
        addModelVbox.getChildren().add(descriptionTextField);
        addModelVbox.getChildren().add(priceLabel);
        addModelVbox.getChildren().add(priceTextField);
        addModelVbox.getChildren().add(newModelButton);
        addModelVbox.getChildren().add(errorLabel);
        rootSettings.setCenter(addModelHbox);
    }

    public Scene init() {
        return settingsScene;
    }

    //insert to database
    private void insertProduct(String serialNo, String brand, String model, String desc, double price) {
        String tableName = "PRODUCT ";
        String[] columns = new String[5];
        columns[0] = "product_serial_no";
        columns[1] = "brand";
        columns[2] = "model";
        columns[3] = "description";
        columns[4] = "price";
        ArrayList<Object> values = new ArrayList<Object>();
        values.add(serialNo);
        values.add(brand);
        values.add(model);
        values.add(desc);
        values.add(price);
        /*
        String[] values = new String[5];
        values[0] = serialNo;
        values[1] = brand;
        values[2] = model;
        values[3] = desc;
        values[4] = Double.toString(price);
        */
        DatabaseHelper.connect();
        DatabaseHelper.createInsertQuery(tableName, columns, values);
        //DatabaseHelper.insert(query);
        DatabaseHelper.disconnect();
    }
}
