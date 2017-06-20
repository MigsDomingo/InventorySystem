package com.company;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by StarDiamond on 16/05/2017.
 */
public class ViewInvScene {

    Scene settingsScene;
    private TableView<Record> table = new TableView<Record>();
    //tableView;
    //private ObservableList<Record> data;
    private ObservableList<Record> records = FXCollections.observableArrayList(); //RETURN list of records here
    //table.setItems(records);


    private final String ICON_URL_BACK = "images/back.png";


    public ViewInvScene(Stage primaryStage, Scene mainScene) {
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
        //rootSettings.setTop(backBtnHbox);

        //TableView
        VBox tableVbox = new VBox();
        TableColumn<Record, String> dateOrderedCol = new TableColumn<Record, String>("Date Ordered");
        TableColumn<Record, String> dateReceivedCol = new TableColumn<Record, String>("Date Received");
        TableColumn<Record, String> dateReleasedCol = new TableColumn<Record, String>("Date Released");
        TableColumn<Record, String> serialNumberCol = new TableColumn<Record, String>("Serial Number");
        TableColumn<Record, String> brandCol = new TableColumn<Record, String>("Brand");
        TableColumn<Record, String> modelCol = new TableColumn<Record, String>("Model");
        TableColumn<Record, String> descriptionCol = new TableColumn<Record, String>("Description");
        TableColumn<Record, String> priceCol = new TableColumn<Record, String>("Price");

        // Nasty yet subtle difference between setCellFactory vs setCellValueFactory
        //https://stackoverflow.com/questions/32398007/javafx-scene-control-tablecolumn-cannot-be-cast-to-javafx-scene-control-tablecol
        dateOrderedCol.setCellValueFactory(new PropertyValueFactory<Record, String>("dateOrdered"));
        dateReceivedCol.setCellValueFactory(new PropertyValueFactory<Record, String>("dateReceived"));
        dateReleasedCol.setCellValueFactory(new PropertyValueFactory<Record, String>("dateReleased"));
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<Record, String>("serialNumber"));
        brandCol.setCellValueFactory(new PropertyValueFactory<Record, String>("brand"));
        modelCol.setCellValueFactory(new PropertyValueFactory<Record, String>("model"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Record, String>("description"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Record, String>("price"));

        ArrayList<TableColumn<Record, String>> columns = new ArrayList<TableColumn<Record, String>>();
        columns.add(dateOrderedCol);
        columns.add(dateReceivedCol);
        columns.add(dateReleasedCol);
        columns.add(serialNumberCol);
        columns.add(brandCol);
        columns.add(modelCol);
        columns.add(descriptionCol);
        columns.add(priceCol);

        /*
        Callback<TableColumn<Record,String>, TableCell<Record,String>> stringCellFactory = p -> {
            MyStringTableCell cell = new MyStringTableCell();
            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
            return cell;
        };*/
        //dateReceivedCol.setCellFactory();
        //dateOrderedCol.setCellFactory(stringCellFactory);

        /*
        for ( TableColumn<Record,String> thisCol : columns) {
            thisCol.setCellFactory(stringCellFactory);
            System.out.println("Added");
        }
*/

        ArrayList<TableColumn<Record, String>> tempList = new ArrayList<TableColumn<Record, String>>();
        tempList.add(dateOrderedCol);
        tempList.add(dateReceivedCol);
        tempList.add(dateReleasedCol);
        tempList.add(serialNumberCol);
        tempList.add(brandCol);
        tempList.add(modelCol);
        tempList.add(descriptionCol);
        tempList.add(priceCol);

        for ( TableColumn<Record, String> obj : tempList ) {
            obj.setPrefWidth(150);
        }
        table.getColumns().setAll(tempList);

        table.setItems(records);

        //Table Columns (Data)
        DatabaseHelper.connect();
        ResultSet rs = DatabaseHelper.executeQuery("select inventory.product_serial_no, date_ordered, date_received, date_released, brand, model, description, price from inventory left join product on inventory.product_serial_no = product.product_serial_no;");

        try {

            while (rs.next()) {
                try {
                    Record thisRecord = new Record();
                    thisRecord.setSerialNumber(rs.getString("product_serial_no"));
                    thisRecord.setDateOrdered(rs.getDate("date_ordered"));
                    thisRecord.setDateReceived(rs.getDate("date_received"));
                    thisRecord.setDateReleased(rs.getDate("date_released"));
                    thisRecord.setBrand(rs.getString("brand"));
                    thisRecord.setModel(rs.getString("model"));
                    thisRecord.setDescription(rs.getString("description"));
                    thisRecord.setPrice(rs.getDouble("price"));


                    records.add(thisRecord);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //https://stackoverflow.com/questions/20802208/delete-a-row-from-a-javafx-table-using-context-menu
        table.setRowFactory(new Callback<TableView<Record>, TableRow<Record>>() {
            @Override
            public TableRow<Record> call(TableView<Record> tableView) {
                final TableRow<Record> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        table.getItems().remove(row.getItem());
                    }
                });
                contextMenu.getItems().add(removeMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );
                return row ;
            }
        });
        /*
        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){

            //We are using non property style for making dynamic table

            final int j = i;

            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));

            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            tableview.getColumns().addAll(col);

            System.out.println("Column ["+i+"] ");

        }*/

        //backBtnHbox.getChildren().add(table);
        table.setPrefHeight(620);
        tableVbox.getChildren().add(table);

        rootSettings.setTop(backBtnHbox);
        rootSettings.setCenter(tableVbox);
    }

    public Scene init() {
        return settingsScene;
    }

    class MyStringTableCell extends TableCell<Record, String> {
/*
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? null : getString());
            setGraphic(null);
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
*/
    }

    /*
    class MyDateTableCell extends TableCell<Record, String> {

        //Date uses java.util
        @Override
        public void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);

        }
    }*/

    /*
    class MyEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent t) {
            if (t.isSecondaryButtonDown()) {
                System.out.println("DELET DIS");
            }
        }
    }*/
}
