package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.HotelComplex;
import com.example.cringecoding.Services.HotelComplexService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ComplexManagementController {

    @FXML
    private TableView<HotelComplex> complexTable;

    @FXML
    private TableColumn<HotelComplex, String> nameColumn;

    @FXML
    private TableColumn<HotelComplex, String> addressColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private Button handleAddComplex;

    @FXML
    private Button handleUpdateComplex;

    @FXML
    private Button handleDeleteComplex;

    private HotelComplexService hotelComplexService = new HotelComplexService();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("complexName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadComplexData();
    }

    @FXML
    private void handleSearchComplex(ActionEvent event) {
        String searchQuery = searchField.getText();
        List<HotelComplex> complexes = hotelComplexService.searchComplexesByName(searchQuery);
        updateComplexTable(complexes);
    }

    @FXML
    private void handleAddComplex(ActionEvent event) {
        String name = nameField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || address.isEmpty()) {
            showAlert("Error", "Fields must not be empty");
            return;
        }

        HotelComplex complex = new HotelComplex();
        complex.setComplexName(name);
        complex.setAddress(address);

        hotelComplexService.saveHotelComplex(complex);
        loadComplexData();
        clearFields();
    }

    @FXML
    private void handleUpdateComplex(ActionEvent event) {
        String name = nameField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || address.isEmpty()) {
            showAlert("Error", "Fields must not be empty");
            return;
        }

        hotelComplexService.updateHotelComplexAddress(name, address);
        loadComplexData();
        clearFields();
    }

    @FXML
    private void handleDeleteComplex(ActionEvent event) {
        String name = nameField.getText();
        String address = addressField.getText();

        if (name.isEmpty() && address.isEmpty()) {
            showAlert("Error", "At least one field must be filled");
            return;
        }

        hotelComplexService.deleteHotelComplex(name, address);
        loadComplexData();
        clearFields();
    }

    private void loadComplexData() {
        List<HotelComplex> complexes = hotelComplexService.getAllHotelComplexes();
        updateComplexTable(complexes);
    }

    private void updateComplexTable(List<HotelComplex> complexes) {
        ObservableList<HotelComplex> complexList = FXCollections.observableArrayList(complexes);
        complexTable.setItems(complexList);
    }

    private void clearFields() {
        nameField.clear();
        addressField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
