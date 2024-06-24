package com.example.cringecoding.Controllers;

import com.example.cringecoding.DTO.FloorDTO;
import com.example.cringecoding.DBUtils.HibernateUtil;
import com.example.cringecoding.Services.OtherUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeController {

    @FXML
    public Button btnRefreshFloors;

    @FXML
    private TableView<FloorDTO> floorsTable;

    @FXML
    private TableColumn<FloorDTO, Long> floorIdColumn;

    @FXML
    private TableColumn<FloorDTO, Integer> numberOfFloorColumn;

    @FXML
    private TableColumn<FloorDTO, String> buildingAddressColumn;

    @FXML
    private TextField employeeIdField;

    private ObservableList<FloorDTO> floorsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        floorIdColumn.setCellValueFactory(new PropertyValueFactory<>("idFloor"));
        numberOfFloorColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfFloor"));
        buildingAddressColumn.setCellValueFactory(new PropertyValueFactory<>("buildingAddress"));
        floorsTable.setItems(floorsList);
    }

    @FXML
    protected void handleRefreshFloors(ActionEvent event) {
        String employeeIdText = employeeIdField.getText();
        if (employeeIdText != null && !employeeIdText.isEmpty()) {
            try {
                String employeeId = employeeIdText;
                loadFloors(employeeId);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid numeric Employee ID.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Input", "Please enter an Employee ID.");
        }
    }

    private void loadFloors(String employeeFullName) {
        floorsList.clear();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT f.idFloor, f.numberOfFloor, f.hotelBuilding.buildingAddress " +
                            "FROM Floor f JOIN f.serviceEmployees se " +
                            "WHERE se.fullName LIKE :employeeFullName", Object[].class);
            query.setParameter("employeeFullName", "%" + employeeFullName + "%");
            List<Object[]> results = query.getResultList();

            for (Object[] result : results) {
                Long idFloor = (Long) result[0];
                Integer numberOfFloor = (Integer) result[1];
                String buildingAddress = (String) result[2];
                floorsList.add(new FloorDTO(idFloor, numberOfFloor, buildingAddress));
            }
            floorsTable.setItems(floorsList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load floors.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        OtherUtils.changeScene(event, "/com/example/cringecoding/hello-view.fxml", "Hello View");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
