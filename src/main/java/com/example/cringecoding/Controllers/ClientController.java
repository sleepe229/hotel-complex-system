package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.*;
import com.example.cringecoding.DBUtils.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class ClientController {

    @FXML
    private TableView<HotelComplex> complexTable;

    @FXML
    private TableColumn<HotelComplex, String> complexNameColumn;

    @FXML
    private TableColumn<HotelComplex, String> complexAddressColumn;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ComboBox<String> roomTypeComboBox;

    private ClientService clientService = new ClientService();
    private HotelComplexService hotelComplexService = new HotelComplexService();
    private ReservationService reservationService = new ReservationService();
    private OrganizationService organizationService = new OrganizationService();

    @FXML
    private void initialize() {
        complexNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComplexName()));
        complexAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        loadComplexData();
        roomTypeComboBox.setItems(FXCollections.observableArrayList("Econom", "Standart", "Luxury"));
    }

    private void loadComplexData() {
        List<HotelComplex> complexes = hotelComplexService.getAllHotelComplexes();
        ObservableList<HotelComplex> complexList = FXCollections.observableArrayList(complexes);
        complexTable.setItems(complexList);
    }

    @FXML
    private void handleBookRoom(ActionEvent event) {
        HotelComplex selectedComplex = complexTable.getSelectionModel().getSelectedItem();
        String roomType = roomTypeComboBox.getValue();
        String phoneNumber = phoneNumberField.getText();

        if (selectedComplex == null || roomType == null || phoneNumber.isEmpty()) {
            showAlert("Error", "All fields must be filled and a complex must be selected.");
            return;
        }

        Client client = clientService.getClientByPhoneNumber(phoneNumber);
        if (client == null) {
            client = new Client();
            client.setPhoneNumber(phoneNumber);
            clientService.saveClient(client);
        }

        List<Room> availableRooms = reservationService.getAvailableRoomsByComplexAndType(selectedComplex.getComplexName(), roomType);
        if (availableRooms.isEmpty()) {
            showAlert("No Rooms", "No available rooms of the selected type.");
            return;
        }

        Room room = availableRooms.get(0);
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setClient(client);
        reservation.setReservationStatus("booked");
        reservation.setReservationDate(new Date());
        reservation.setArrivalDate(new Date());
        reservation.setNumberOfNights(1);
        reservation.setNumberOfPeople(1);
        reservation.setCurRoomStatus("booked");

        List<Organization> organizations = organizationService.getAllOrganizations();
        Random rand = new Random();
        Organization organization = organizations.get(rand.nextInt(organizations.size()));
        reservation.setOrganization(organization);

        reservationService.saveReservation(reservation);

        showAlert("Success", String.format("Вы успешно забронировали номер в комплексе %s, по адресу %s, на %d этаже. Ваш номер %d. На одни сутки начиная с %s. Обратитесь с паспортом к портье.",
                selectedComplex.getComplexName(),
                room.getFloor().getHotelBuilding().getBuildingAddress(),
                room.getFloor().getNumberOfRooms(),
                room.getRoomNumber(),
                new Date()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
