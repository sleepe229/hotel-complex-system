package com.example.cringecoding.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Hotel_Buildings")
public class HotelBuilding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_building")
    private Long idBuilding;

    @ManyToOne
    @JoinColumn(name = "hotel_complex_name", referencedColumnName = "complex_name")
    private HotelComplex hotelComplex;

    @Transient
    private String hotelComplexName;

    @Column(name = "number_of_rooms")
    private int numberOfRooms;

    @Column(name = "rooms_per_floor")
    private int roomsPerFloor;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "has_food")
    private boolean hasFood;

    @Column(name = "has_service")
    private boolean hasService;

    @Column(name = "work_status")
    private boolean workStatus;

    @Column(name = "last_status_date_recharge")
    private Date lastStatusDateRecharge;

    @Column(name = "building_address")
    private String buildingAddress;

    @PostLoad
    private void postLoad() {
        this.hotelComplexName = hotelComplex != null ? hotelComplex.getComplexName() : null;
    }

    public Long getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(Long idBuilding) {
        this.idBuilding = idBuilding;
    }

    public HotelComplex getHotelComplex() {
        return hotelComplex;
    }

    public void setHotelComplex(HotelComplex hotelComplex) {
        this.hotelComplex = hotelComplex;
        this.hotelComplexName = hotelComplex != null ? hotelComplex.getComplexName() : null;
    }

    public String getHotelComplexName() {
        return hotelComplexName;
    }

    public void setHotelComplexName(String hotelComplexName) {
        this.hotelComplexName = hotelComplexName;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getRoomsPerFloor() {
        return roomsPerFloor;
    }

    public void setRoomsPerFloor(int roomsPerFloor) {
        this.roomsPerFloor = roomsPerFloor;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isHasFood() {
        return hasFood;
    }

    public void setHasFood(boolean hasFood) {
        this.hasFood = hasFood;
    }

    public boolean isHasService() {
        return hasService;
    }

    public void setHasService(boolean hasService) {
        this.hasService = hasService;
    }

    public boolean isWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(boolean workStatus) {
        this.workStatus = workStatus;
    }

    public Date getLastStatusDateRecharge() {
        return lastStatusDateRecharge;
    }

    public void setLastStatusDateRecharge(Date lastStatusDateRecharge) {
        this.lastStatusDateRecharge = lastStatusDateRecharge;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }
}
