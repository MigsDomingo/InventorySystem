package com.company;

import java.sql.*;
import java.util.ArrayList;

import static javafx.scene.input.KeyCode.Z;

/**
 * Created by StarDiamond on 17/05/2017.
 */
public class DatabaseHelper {

    public static final int MYSQL_DUPLICATE_PK = 1062;

    public static final String databaseName = "pmic_inventory";
    public static final String username = "root";
    public static final String password = "root";

    public static final String driver = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/" + databaseName;

    public static Connection conn;
    public static ResultSet rs;

    public static void connect() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Given column and table
    public static ResultSet select(String column, String table) {

        try {
            PreparedStatement preStatement = conn.prepareStatement("SELECT " + column + " FROM " + table);
            rs = preStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet executeQuery(String query) {
        if (conn == null) return null;
        try {
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Overload: give power to make own query
    public static ResultSet select(String query) {

        try {
            Statement statement = conn.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void insert(PreparedStatement query) {
        try {
            //PreparedStatement preStatement = conn.prepareStatement(query);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBySerialNum(String tableName, int serialNum) {
        String statement = "DELETE FROM " + tableName + " WHERE product_serial_no = " + serialNum;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void createInsertQuery(String tableName, String[] columns, ArrayList<Object> values) {
        int noOfCols = columns.length;
        String qMarks = "";
        String columnsStr = "";

        // comma portion
        for ( int i = 0; i < noOfCols; i++) {
            qMarks += "?,";
        }
        qMarks = qMarks.substring(0, qMarks.length()-1); //remove last comma. Too lazy to make this efficient

        // columns portion
        for ( int i = 0; i < noOfCols; i++) {
            columnsStr += columns[i] + ",";
        }
        columnsStr = columnsStr.substring(0, columnsStr.length()-1); //remove last comma. Too lazy to make this efficient

        String query = "INSERT INTO " + tableName + "(" + columnsStr + ") VALUES(" + qMarks + ")";
        System.out.println("Query looks like: " + query);

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            // values portion
            for (int i = 1; i <= noOfCols; i++) {
                if ( values.get(i-1) instanceof String ) {
                    preparedStatement.setString(i, (String) values.get(i-1));
                }
                else if (values.get(i-1) instanceof Double ) {
                    //Double temp = (Double) values.get(i-1);
                    //                    Double temp = Double.parseDouble((String) values.get(i));
                    preparedStatement.setDouble(i, (Double) values.get(i-1));
                }
            }

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            //if (e.getErrorCode() == )
            if ( e.getErrorCode() == MYSQL_DUPLICATE_PK ) {
                //Do something
            }
            e.printStackTrace();
            System.out.print(e.getErrorCode());
        }

        //return null;
    }
}
