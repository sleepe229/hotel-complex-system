package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.Floor;
import com.example.cringecoding.Models.HotelBuilding;
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

public class FloorManagementController {

    @FXML
    private TableView<Floor> floorTable;

    @FXML
    private TableColumn<Floor, Long> idColumn;

    @FXML
    private TableColumn<Floor, Long> buildingIdColumn;

    @FXML
    private TableColumn<Floor, Integer> numberOfRoomsColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextField idField;

    @FXML
    private TextField buildingIdField;

    @FXML
    private TextField numberOfRoomsField;

    @FXML
    private Button handleAddFloor;

    @FXML
    private Button handleUpdateFloor;

    @FXML
    private Button handleDeleteFloor;

    private FloorManagementService floorManagementService = new FloorManagementService();
    private BuildingManagementService buildingManagementService = new BuildingManagementService();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idFloor"));
        buildingIdColumn.setCellValueFactory(new PropertyValueFactory<>("buildingId"));
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        loadFloorData();
    }

    @FXML
    private void handleSearchFloor(ActionEvent event) {
        String searchQuery = searchField.getText();
        List<Floor> floors = floorManagementService.getAllFloors(); // Add search functionality if needed
        updateFloorTable(floors);
    }

    @FXML
    private void handleAddFloor(ActionEvent event) {
        Long buildingId = Long.parseLong(buildingIdField.getText());
        int numberOfRooms = Integer.parseInt(numberOfRoomsField.getText());

        HotelBuilding hotelBuilding = buildingManagementService.getBuildingById(buildingId);
        if (hotelBuilding == null) {
            showAlert("Error", "Building not found");
            return;
        }

        Floor floor = new Floor();
        floor.setHotelBuilding(hotelBuilding);
        floor.setNumberOfRooms(numberOfRooms);

        floorManagementService.saveFloor(floor);
        loadFloorData();
        clearFields();
    }

    @FXML
    private void handleUpdateFloor(ActionEvent event) {
        Long id = Long.parseLong(idField.getText());
        int numberOfRooms = Integer.parseInt(numberOfRoomsField.getText());

        Floor floor = floorManagementService.getFloorById(id);
        if (floor == null) {
            showAlert("Error", "Floor not found");
            return;
        }

        floor.setNumberOfRooms(numberOfRooms);

        floorManagementService.updateFloor(floor);
        loadFloorData();
        clearFields();
    }

    @FXML
    private void handleDeleteFloor(ActionEvent event) {
        Long id = Long.parseLong(idField.getText());

        floorManagementService.deleteFloor(id);
        loadFloorData();
        clearFields();
    }

    private void loadFloorData() {
        List<Floor> floors = floorManagementService.getAllFloors();
        updateFloorTable(floors);
    }

    private void updateFloorTable(List<Floor> floors) {
        ObservableList<Floor> floorList = FXCollections.observableArrayList(floors);
        floorTable.setItems(floorList);
    }

    private void clearFields() {
        idField.clear();
        buildingIdField.clear();
        numberOfRoomsField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
