package com.example.cringecoding.Controllers;

import com.example.cringecoding.Services.OtherUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelloController {

    public Button btn_admin;
    public Button btn_organization;
    public Button btn_client;
    public Button btn_employee;

    @FXML
    protected void handleAdminLogin(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/admin.fxml", "Admin");
    }

    @FXML
    protected void handleOrganizationLogin(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/organization.fxml", "Organization");
    }

    @FXML
    protected void handleClientLogin(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/client.fxml", "Client");
    }

    @FXML
    protected void handleEmployeeLogin(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/employee.fxml", "Employee");
    }
}
