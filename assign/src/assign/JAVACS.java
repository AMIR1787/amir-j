/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assign;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author user
 */
public class JAVACS extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TableView table = new TableView();

        Button btn1 = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();
        Button btn4 = new Button();
        Button btn5 = new Button();
        Button btn6 = new Button();
        btn1.setText("INSERT");
        btn2.setText("UPDATE");
        btn4.setText("VIEW");
        btn5.setText("DISTINCT");
        btn6.setText("SELECT");

        TextField txt = new TextField();
        TextField txt1 = new TextField();
        TextField txt2 = new TextField();
        TextField txt3 = new TextField();
        TextField txt4 = new TextField();
        TextField txt5 = new TextField();

        Label lbl = new Label("SID :");
        Label lbl1 = new Label("STUDID :");
        Label lbl2 = new Label("FIRST NAME :");
        Label lbl3 = new Label("LAST NAME :");
        Label lbl4 = new Label("SECTION :");
        Label lbl5 = new Label("DEPARTMENT :");
        DbConnection db = new DbConnection();
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbConnection db = new DbConnection();
                String sql = "Insert into DEPMT_TBL (SID, STUDID, FIRSTNAME, LASTNAME,SECTION, DEPARTMENT ) Values (?,?,?,?,?,?)";
                String lbl = txt.getText();
                String lbl1 = txt1.getText();
                String lbl2 = txt2.getText();
                String lbl3 = txt3.getText();
                String lbl4 = txt4.getText();
                String lbl5 = txt5.getText();
                try {
                    Connection con = db.connMethod();

                    PreparedStatement pre;
                    try {
                        pre = con.prepareStatement(sql);
                        pre.setString(1, lbl);
                        pre.setString(2, lbl1);
                        pre.setString(3, lbl2);
                        pre.setString(4, lbl3);
                        pre.setString(5, lbl4);
                        pre.setString(6, lbl5);
                        int i = pre.executeUpdate();
                        if (i == 1) {

                            System.out.println("Data Inserted succsecfully");
                        }

                    } catch (SQLException ex) {
                        java.util.logging.Logger.getLogger(JAVACS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(JAVACS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }

        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                DbConnection db = new DbConnection();
                Connection con = null;
                try {
                    con = db.connMethod();
                    String value = txt2.getText();
                    String value1 = "AMAN";
                    String sql = "UPDATE DEPMT_TBL SET Firstname='" + value + "' WHERE Firstname='" + value1 + "'";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.executeUpdate();

                    a.setContentText("Updated successfuly");
                    a.showAndWait();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        btn4.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data;

            @Override
            public void handle(ActionEvent event) {

                DbConnection obj1;
                Connection c;

                ResultSet rs;
                data = FXCollections.observableArrayList();
                try {

                    obj1 = new DbConnection();
                    c = obj1.connMethod();
                    String SQL = "SELECT * from DEPMT_TBL";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                        table.getColumns().addAll(col);
                        System.out.println("Column [" + i + "] ");

                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }
                        System.out.println("Row[1]added " + row);
                        data.add(row);

                    }

                    table.setItems(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error ");
                }
            }
        });

        btn5.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data;

            @Override
            public void handle(ActionEvent event) {

                DbConnection db = new DbConnection();
                Connection c;
                ResultSet rs;
                data = FXCollections.observableArrayList();
                try {

                    c = db.connMethod();
                    String SQL = "SELECT distinct SECTION from DEPMT_TBL";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        col.setMinWidth(100);
                        table.getColumns().addAll(col);

                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }

                        data.add(row);

                    }

                    table.setItems(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error ");
                }

            }
        });
        btn6.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data;

            @Override
            public void handle(ActionEvent event) {

                DbConnection obj1;
                Connection c;
                ResultSet rs;
                data = FXCollections.observableArrayList();
                try {

                    obj1 = new DbConnection();
                    c = obj1.connMethod();
                    String SQL = "SELECT DEPARTMENT from DEPMT_TBL where FIRSTNAME = 'ELIAS' and SECTION='SecA' and DEPARTMENT='CS' ";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                        table.getColumns().addAll(col);

                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }

                        data.add(row);

                    }

                    table.setItems(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error ");
                }

            }
        });
        GridPane root = new GridPane();

        root.addRow(1, lbl, txt);
        root.addRow(2, lbl1, txt1);
        root.addRow(3, lbl2, txt2);
        root.addRow(4, lbl3, txt3);
        root.addRow(5, lbl4, txt4);
        root.addRow(6, lbl5, txt5);
        root.addRow(7, btn1, btn2);
        root.addRow(8, btn4);
        root.addRow(9, btn5, btn6);
        root.addColumn(3, table);
        root.setHgap(10);
        root.setVgap(10);

        Scene scene = new Scene(root, 800, 350);
        primaryStage.setTitle("Display");
        primaryStage.setScene(scene);
        primaryStage.show();
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        launch(args);
    }

}
