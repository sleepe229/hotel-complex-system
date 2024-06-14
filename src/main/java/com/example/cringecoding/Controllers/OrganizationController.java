package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.Reservation;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class OrganizationController {

    @FXML
    private TextField organizationNameField;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Long> reservationIdColumn;

    @FXML
    private TableColumn<Reservation, String> clientPhoneNumberColumn;

    @FXML
    private TableColumn<Reservation, Integer> roomNumberColumn;

    @FXML
    private TableColumn<Reservation, String> arrivalDateColumn;

    @FXML
    private TableColumn<Reservation, Integer> numberOfNightsColumn;

    @FXML
    private TableColumn<Reservation, String> reservationStatusColumn;

    private OrganizationService organizationService = new OrganizationService();

    @FXML
    private void initialize() {
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        clientPhoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getPhoneNumber()));
        roomNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoom().getRoomNumber()).asObject());
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        numberOfNightsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfNights"));
        reservationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("reservationStatus"));
    }

    @FXML
    private void handleSearchReservations(ActionEvent event) {
        String organizationName = organizationNameField.getText();

        if (organizationName.isEmpty()) {
            showAlert("Error", "Organization name must not be empty.");
            return;
        }

        List<Reservation> reservations = organizationService.getReservationsByOrganizationName(organizationName);
        updateReservationTable(reservations);
    }

    private void updateReservationTable(List<Reservation> reservations) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList(reservations);
        reservationTable.setItems(reservationList);
    }

    @FXML
    private void handleDeleteReservation(ActionEvent event) {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation == null) {
            showAlert("Error", "No reservation selected.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to delete the selected reservation?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            organizationService.deleteReservation(selectedReservation.getIdReservation());
            reservationTable.getItems().remove(selectedReservation);
            showAlert("Success", "Reservation deleted successfully.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
