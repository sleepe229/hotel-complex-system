package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.Room;
import com.example.cringecoding.Models.Floor;
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

import java.util.List;

public class RoomManagementController {

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, Long> roomIdColumn;

    @FXML
    private TableColumn<Room, Long> floorIdColumn;

    @FXML
    private TableColumn<Room, Integer> roomNumberColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextField roomIdField;

    @FXML
    private TextField floorIdField;

    @FXML
    private TextField roomNumberField;

    @FXML
    private Button handleAddRoom;

    @FXML
    private Button handleUpdateRoom;

    @FXML
    private Button handleDeleteRoom;

    private RoomManagementService roomManagementService = new RoomManagementService();
    private FloorManagementService floorManagementService = new FloorManagementService();

    @FXML
    private void initialize() {
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        floorIdColumn.setCellValueFactory(new PropertyValueFactory<>("floorId"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        loadRoomData();
    }

    @FXML
    private void handleSearchRoom(ActionEvent event) {
        String searchQuery = searchField.getText();
        List<Room> rooms = roomManagementService.getAllRooms(); // Add search functionality if needed
        updateRoomTable(rooms);
    }

    @FXML
    private void handleAddRoom(ActionEvent event) {
        Long floorId = Long.parseLong(floorIdField.getText());
        int roomNumber = Integer.parseInt(roomNumberField.getText());

        Floor floor = floorManagementService.getFloorById(floorId);
        if (floor == null) {
            showAlert("Error", "Floor not found");
            return;
        }

        Room room = new Room();
        room.setFloor(floor);
        room.setRoomNumber(roomNumber);

        roomManagementService.saveRoom(room);
        loadRoomData();
        clearFields();
    }

    @FXML
    private void handleUpdateRoom(ActionEvent event) {
        Long roomId = Long.parseLong(roomIdField.getText());
        int roomNumber = Integer.parseInt(roomNumberField.getText());

        Room room = roomManagementService.getRoomById(roomId);
        if (room == null) {
            showAlert("Error", "Room not found");
            return;
        }

        room.setRoomNumber(roomNumber);

        roomManagementService.updateRoom(room);
        loadRoomData();
        clearFields();
    }

    @FXML
    private void handleDeleteRoom(ActionEvent event) {
        Long roomId = Long.parseLong(roomIdField.getText());

        roomManagementService.deleteRoom(roomId);
        loadRoomData();
        clearFields();
    }

    private void loadRoomData() {
        List<Room> rooms = roomManagementService.getAllRooms();
        updateRoomTable(rooms);
    }

    private void updateRoomTable(List<Room> rooms) {
        ObservableList<Room> roomList = FXCollections.observableArrayList(rooms);
        roomTable.setItems(roomList);
    }

    private void clearFields() {
        roomIdField.clear();
        floorIdField.clear();
        roomNumberField.clear();
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
