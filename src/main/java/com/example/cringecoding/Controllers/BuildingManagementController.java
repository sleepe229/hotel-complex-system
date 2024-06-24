package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.HotelBuilding;
import com.example.cringecoding.Models.HotelComplex;
import com.example.cringecoding.Services.OtherUtils;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BuildingManagementController {

    @FXML
    private TableView<HotelBuilding> buildingTable;

    @FXML
    private TableColumn<HotelBuilding, Long> idColumn;

    @FXML
    private TableColumn<HotelBuilding, String> complexNameColumn;

    @FXML
    private TableColumn<HotelBuilding, Integer> numberOfRoomsColumn;

    @FXML
    private TableColumn<HotelBuilding, Integer> roomsPerFloorColumn;

    @FXML
    private TableColumn<HotelBuilding, String> roomTypeColumn;

    @FXML
    private TableColumn<HotelBuilding, Boolean> hasFoodColumn;

    @FXML
    private TableColumn<HotelBuilding, Boolean> hasServiceColumn;

    @FXML
    private TableColumn<HotelBuilding, Boolean> workStatusColumn;

    @FXML
    private TableColumn<HotelBuilding, Date> lastStatusDateRechargeColumn;

    @FXML
    private TableColumn<HotelBuilding, String> buildingAddressColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextField idField;

    @FXML
    private TextField complexNameField;

    @FXML
    private TextField numberOfRoomsField;

    @FXML
    private TextField roomsPerFloorField;

    @FXML
    private TextField roomTypeField;

    @FXML
    private TextField hasFoodField;

    @FXML
    private TextField hasServiceField;

    @FXML
    private TextField workStatusField;

    @FXML
    private TextField lastStatusDateRechargeField;

    @FXML
    private TextField buildingAddressField;

    @FXML
    private Button handleAddBuilding;

    @FXML
    private Button handleUpdateBuilding;

    @FXML
    private Button handleDeleteBuilding;

    private BuildingManagementService buildingManagementService = new BuildingManagementService();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idBuilding"));
        complexNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelComplexName"));
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        roomsPerFloorColumn.setCellValueFactory(new PropertyValueFactory<>("roomsPerFloor"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        hasFoodColumn.setCellValueFactory(new PropertyValueFactory<>("hasFood"));
        hasServiceColumn.setCellValueFactory(new PropertyValueFactory<>("hasService"));
        workStatusColumn.setCellValueFactory(new PropertyValueFactory<>("workStatus"));
        lastStatusDateRechargeColumn.setCellValueFactory(new PropertyValueFactory<>("lastStatusDateRecharge"));
        buildingAddressColumn.setCellValueFactory(new PropertyValueFactory<>("buildingAddress"));
        loadBuildingData();
    }

    @FXML
    private void handleSearchBuilding(ActionEvent event) {
        String searchQuery = searchField.getText();
        List<HotelBuilding> buildings = buildingManagementService.searchBuildingsByName(searchQuery);
        updateBuildingTable(buildings);
    }

    @FXML
    private void handleAddBuilding(ActionEvent event) {
        String complexName = complexNameField.getText();
        String address = buildingAddressField.getText();
        int numberOfRooms = Integer.parseInt(numberOfRoomsField.getText());
        int roomsPerFloor = Integer.parseInt(roomsPerFloorField.getText());
        String roomType = roomTypeField.getText();
        boolean hasFood = Boolean.parseBoolean(hasFoodField.getText());
        boolean hasService = Boolean.parseBoolean(hasServiceField.getText());
        boolean workStatus = Boolean.parseBoolean(workStatusField.getText());
        Date lastStatusDateRecharge = null;

        try {
            lastStatusDateRecharge = new SimpleDateFormat("dd-MM-yyyy").parse(lastStatusDateRechargeField.getText());
        } catch (Exception e) {
            showAlert("Error", "Invalid date format. Please use dd-MM-yyyy");
            return;
        }

        if (complexName.isEmpty() || address.isEmpty()) {
            showAlert("Error", "Fields must not be empty");
            return;
        }

        HotelComplex hotelComplex = buildingManagementService.getHotelComplexByName(complexName);
        if (hotelComplex == null) {
            showAlert("Error", "Hotel Complex not found");
            return;
        }

        HotelBuilding building = new HotelBuilding();
        building.setHotelComplex(hotelComplex);
        building.setBuildingAddress(address);
        building.setNumberOfRooms(numberOfRooms);
        building.setRoomsPerFloor(roomsPerFloor);
        building.setRoomType(roomType);
        building.setHasFood(hasFood);
        building.setHasService(hasService);
        building.setWorkStatus(workStatus);
        building.setLastStatusDateRecharge(lastStatusDateRecharge);

        buildingManagementService.saveBuilding(building);
        loadBuildingData();
        clearFields();
    }


    @FXML
    private void handleUpdateBuilding(ActionEvent event) {
        Long id = Long.parseLong(idField.getText());
        String address = buildingAddressField.getText();

        if (address.isEmpty()) {
            showAlert("Error", "Address field must not be empty");
            return;
        }

        buildingManagementService.updateBuildingAddress(id, address);
        loadBuildingData();
        clearFields();
    }

    @FXML
    private void handleDeleteBuilding(ActionEvent event) {
        Long id = Long.parseLong(idField.getText());

        if (id == null) {
            showAlert("Error", "ID field must not be empty");
            return;
        }

        buildingManagementService.deleteBuilding(id);
        loadBuildingData();
        clearFields();
    }

    private void loadBuildingData() {
        List<HotelBuilding> buildings = buildingManagementService.getAllBuildings();
        updateBuildingTable(buildings);
    }

    private void updateBuildingTable(List<HotelBuilding> buildings) {
        ObservableList<HotelBuilding> buildingList = FXCollections.observableArrayList(buildings);
        buildingTable.setItems(buildingList);
    }

    private void clearFields() {
        idField.clear();
        complexNameField.clear();
        buildingAddressField.clear();
        numberOfRoomsField.clear();
        roomsPerFloorField.clear();
        roomTypeField.clear();
        hasFoodField.clear();
        hasServiceField.clear();
        workStatusField.clear();
        lastStatusDateRechargeField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/admin.fxml", "?");
    }
}
