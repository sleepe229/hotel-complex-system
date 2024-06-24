package com.example.cringecoding.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long idReservation;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "organization_name")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "client_phone_number", nullable = false)
    private Client client;

    @Column(name = "payment_time")
    private Date paymentTime;

    @Column(name = "reservation_status")
    private String reservationStatus;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "number_of_nights")
    private Integer numberOfNights;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @Column(name = "floor_number")
    private Integer floorNumber;

    @Column(name = "number_of_booked_rooms")
    private Integer numberOfBookedRooms;

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getNumberOfBookedRooms() {
        return numberOfBookedRooms;
    }

    public void setNumberOfBookedRooms(Integer numberOfBookedRooms) {
        this.numberOfBookedRooms = numberOfBookedRooms;
    }

    @Column(name = "reservation_date")
    private Date reservationDate;

    @Column(name = "cur_room_status")
    private String curRoomStatus;

    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(Integer numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getCurRoomStatus() {
        return curRoomStatus;
    }

    public void setCurRoomStatus(String curRoomStatus) {
        this.curRoomStatus = curRoomStatus;
    }
}
