package com.example.cringecoding.Controllers;

import com.example.cringecoding.DTO.FloorDTO;
import com.example.cringecoding.DBUtils.HibernateUtil;
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

    public Button btnRefreshFloors;
    @FXML
    private TableView<FloorDTO> floorsTable;

    @FXML
    private TableColumn<FloorDTO, Long> floorIdColumn;

    @FXML
    private TableColumn<FloorDTO, Long> buildingIdColumn;

    @FXML
    private TableColumn<FloorDTO, Integer> numberOfRooms;

    @FXML
    private TextField employeeIdField;

    private ObservableList<FloorDTO> floorsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        floorIdColumn.setCellValueFactory(new PropertyValueFactory<>("idFloor"));
        buildingIdColumn.setCellValueFactory(new PropertyValueFactory<>("idBuilding"));
        numberOfRooms.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        floorsTable.setItems(floorsList);
    }

    @FXML
    protected void handleRefreshFloors(ActionEvent event) {
        String employeeIdText = employeeIdField.getText();
        if (employeeIdText != null && !employeeIdText.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(employeeIdText);
                loadFloors(employeeId);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid numeric Employee ID.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Input", "Please enter an Employee ID.");
        }
    }

    private void loadFloors(int CURRENT_EMPLOYEE_ID) {
        floorsList.clear();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT f.idFloor, f.hotelBuilding.idBuilding, f.numberOfRooms FROM Floor f JOIN f.serviceEmployees se WHERE se.idEmployee = :employeeId", Object[].class);
            query.setParameter("employeeId", (long) CURRENT_EMPLOYEE_ID);
            List<Object[]> results = query.getResultList();

            for (Object[] result : results) {
                Long idFloor = (Long) result[0];
                Long idBuilding = (Long) result[1];
                Integer numberOfRooms = (Integer) result[2];
                floorsList.add(new FloorDTO(idFloor, idBuilding, numberOfRooms));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load floors.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
