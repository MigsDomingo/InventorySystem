package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import javax.xml.soap.Text;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by StarDiamond on 16/05/2017.
 */
public class IncomingScene {

    Scene incomingScene;

    ComboBox modelComboBox;
    TextField descriptionTextField;
    TextField priceTextField;
    private final String ICON_URL_BACK = "images/back.png";


    public IncomingScene(Stage primaryStage, Scene mainScene) {
        BorderPane rootSettings = new BorderPane();
        incomingScene = new Scene(rootSettings, 1280, 720, Color.BLACK);

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

        HBox mainHBox = new HBox();
        VBox mainVBox = new VBox();
        mainHBox.getChildren().add(mainVBox);
        mainVBox.setPadding(new Insets(10));
        //Label
        Label serialNumLabel = new Label("Serial Number");
        HBox serialNumHBox = new HBox();
        //serialNumHBox.setPadding(new Insets(10));
        TextField firstSerialNumBtn = new TextField();
        Label serialNumdashLabel = new Label(" - ");
        TextField lastSerialNumTextField = new TextField();
        lastSerialNumTextField.setPromptText("Optional: (e.g. 100 - 105)");
        serialNumHBox.getChildren().add(firstSerialNumBtn);
        serialNumHBox.getChildren().add(serialNumdashLabel);
        serialNumHBox.getChildren().add(lastSerialNumTextField);

        Label brandLabel = new Label("Brand");

        ComboBox brandComboBox = new ComboBox<String>(FXCollections.observableList(getBrands()));
        brandComboBox.setOnAction((event -> {
            String selectedBrand = (String) brandComboBox.getSelectionModel().getSelectedItem();
            if(!selectedBrand.equals("(Create a new product)"))
                onSelectBrand(selectedBrand);
            else {
                NewProductScene newProductScene = new NewProductScene();
                //make new window

            }
        }));

        Label modelLabel = new Label("Model");
        modelComboBox = new ComboBox<String>();
        modelComboBox.setDisable(true);
        modelComboBox.setOnAction((event -> {
            String selectedModel = (String) modelComboBox.getSelectionModel().getSelectedItem();
            if (!selectedModel.equals("(Create a new model)"))
                onSelectModel(selectedModel);
            else {
                NewProductScene newProductScene = new NewProductScene();
                //make new window (create product)
            }
        }));

        Label descriptionLabel = new Label("Description");
        descriptionTextField = new TextField();

        Label priceLabel = new Label("Price per unit");
        priceTextField = new TextField();

        Label dateOrderedLabel = new Label("Date ordered");
        TextField dateOrderedTextField = new TextField();
        dateOrderedTextField.setPromptText("This field can be left blank");
        Label dateReceivedLabel = new Label("Date Received");
        TextField dateReceivedTextField = new TextField();
        dateReceivedTextField.setPromptText("This field can be left blank");


        mainVBox.getChildren().add(serialNumLabel);
        mainVBox.getChildren().add(serialNumHBox);
        mainVBox.getChildren().add(brandLabel);
        mainVBox.getChildren().add(brandComboBox);
        mainVBox.getChildren().add(modelLabel);
        mainVBox.getChildren().add(modelComboBox);
        //mainVBox.getChildren().add(modelTextField);
        mainVBox.getChildren().add(descriptionLabel);
        mainVBox.getChildren().add(descriptionTextField);
        mainVBox.getChildren().add(priceLabel);
        mainVBox.getChildren().add(priceTextField);
        mainVBox.getChildren().add(dateOrderedLabel);
        mainVBox.getChildren().add(dateOrderedTextField);
        mainVBox.getChildren().add(dateReceivedLabel);
        mainVBox.getChildren().add(dateReceivedTextField);
        rootSettings.setTop(backBtnHbox);
        rootSettings.setCenter(mainHBox);
    }

    public Scene init() {
        return incomingScene;
    }

    private List<String> getBrands() {
        List<String> tempBrandList = new ArrayList<String>();
        tempBrandList.add("(Create a new product)");
        DatabaseHelper.connect();
        ResultSet rs = DatabaseHelper.select("SELECT DISTINCT brand FROM product ORDER BY brand ASC");
        //ResultSet rs = DatabaseHelper.select("brand", "product");
        try {
            while (rs.next()) {
                tempBrandList.add(rs.getString("brand"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempBrandList;
    }

    private List<String> getModels(String brandName) {
        List<String> tempModelsList = new ArrayList<String>();
        tempModelsList.add("(Create a new model)");
        DatabaseHelper.connect();
        String query = "SELECT DISTINCT model FROM product WHERE brand = \'" + brandName + "\' ORDER BY brand ASC";
        ResultSet rs = DatabaseHelper.select(query);
        try {
            while (rs.next()) {
                tempModelsList.add(rs.getString("model"));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return tempModelsList;
    }

    //this method handles actions performed after selecting a brand.
    private void onSelectBrand(String brandName) {
        System.out.println("Brand " + brandName + " was selected");

        List<String> tempList = getModels(brandName);
        ObservableList<String> tempObsList = FXCollections.observableArrayList(tempList);
        modelComboBox.setItems((ObservableList) tempObsList);
        modelComboBox.setDisable(false);
    }

    private void onSelectModel(String modelName) {
        System.out.println("Model " + modelName + " was selected");
        DatabaseHelper.connect();
        ResultSet rs = DatabaseHelper.select("SELECT DISTINCT description, price FROM product WHERE model = \'" + modelName + "\'");
        try {
            rs.next();
            String descriptionString = rs.getString("description");
            descriptionTextField.setText(descriptionString);
            String priceString = rs.getString("price");
            priceTextField.setText((priceString));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
