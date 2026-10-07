package com.example.hellofx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerManager extends Application {

    // Simple POJO - must have public getters for PropertyValueFactory
    public static class Customer {
        private String name;
        private String province;

        public Customer(String name, String province) {
            this.name = name;
            this.province = province;
        }
        public String getName() { return name; }
        public String getProvince() { return province; }
    }

    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    private final ObservableList<String> zambianProvinces = FXCollections.observableArrayList(
            "Central",
            "Copperbelt",
            "Eastern",
            "Luapula",
            "Lusaka",
            "Muchinga",
            "Northern",
            "North-Western",
            "Southern",
            "Western"
    );

    @Override
    public void start(Stage primaryStage) {

        TextField nameField = new TextField();
        nameField.setPromptText("Customer Name");

        ComboBox<String> provinceCombo = new ComboBox<String>(zambianProvinces);
        provinceCombo.setPromptText("Select Province");
        provinceCombo.setPrefWidth(180);

        Button addButton = new Button("Add Customer");
        Button deleteButton = new Button("Delete Selected");

        TableView<Customer> tableView = new TableView<Customer>(customerList);
        tableView.setPrefHeight(350);

        TableColumn<Customer, String> nameCol = new TableColumn<Customer, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        nameCol.setPrefWidth(220);

        TableColumn<Customer, String> provinceCol = new TableColumn<Customer, String>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("province"));
        provinceCol.setPrefWidth(220);

        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(provinceCol);

        Label statusLabel = new Label("Ready - Enter name and province");
        statusLabel.setStyle("-fx-font-weight: bold;");

        // Add Customer with validation on ObservableList
        addButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            String province = provinceCombo.getValue();

            if (name.isEmpty() || province == null) {
                statusLabel.setText("Validation failed: Both fields must be filled.");
                statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                return;
            }

            customerList.add(new Customer(name, province));
            nameField.clear();
            provinceCombo.setValue(null);
            statusLabel.setText("Added: " + name + " (" + province + ")");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        });

        // Delete Selected with Yes/No Confirmation
        deleteButton.setOnAction(event -> {
            Customer selected = tableView.getSelectionModel().getSelectedItem();

            if (selected == null) {
                statusLabel.setText("Select a row in the table to delete.");
                statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete customer?");
            confirm.setContentText("Are you sure you want to delete '" + selected.getName() + "' ?");

            ButtonType yesType = new ButtonType("Yes");
            ButtonType noType = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirm.getButtonTypes().setAll(yesType, noType);

            confirm.showAndWait().ifPresent(response -> {
                if (response == yesType) {
                    customerList.remove(selected);
                    statusLabel.setText("Deleted: " + selected.getName());
                    statusLabel.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                } else {
                    statusLabel.setText("Delete cancelled.");
                    statusLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                }
            });
        });

        HBox topBox = new HBox(10, nameField, provinceCombo, addButton, deleteButton);
        topBox.setPadding(new Insets(10));

        VBox centerBox = new VBox(10, topBox, tableView, statusLabel);
        centerBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("TextField + Province Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}