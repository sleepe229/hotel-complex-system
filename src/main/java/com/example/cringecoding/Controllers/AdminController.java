package com.example.cringecoding.Controllers;

import com.example.cringecoding.Services.OtherUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminController {

    @FXML
    private Button btn_complex_management;

    @FXML
    private Button btn_hotel_management;

    @FXML
    private Button btn_room_management;

    @FXML
    private Button btn_floor_management;

    @FXML
    private void handleComplexManagement(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/complex_management.fxml", "Complex Management");
    }

    @FXML
    private void handleHotelManagement(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/building_management.fxml", "Hotel Management");
    }

    @FXML
    private void handleRoomManagement(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/room_management.fxml", "Room Management");
    }

    @FXML
    private void handleFloorManagement(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/floor_management.fxml", "Floor Management");
    }
}
