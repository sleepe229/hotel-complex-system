package com.example.cringecoding.Models;

import javax.persistence.*;

@Entity
@Table(name = "Floors")
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_floor")
    private Long idFloor;

    @ManyToOne
    @JoinColumn(name = "id_building", referencedColumnName = "id_building")
    private HotelBuilding hotelBuilding;

    @Column(name = "number_of_rooms")
    private int numberOfRooms;

    @Transient
    private Long buildingId;

    @PostLoad
    private void postLoad() {
        this.buildingId = hotelBuilding != null ? hotelBuilding.getIdBuilding() : null;
    }

    public Long getIdFloor() {
        return idFloor;
    }

    public void setIdFloor(Long idFloor) {
        this.idFloor = idFloor;
    }

    public HotelBuilding getHotelBuilding() {
        return hotelBuilding;
    }

    public void setHotelBuilding(HotelBuilding hotelBuilding) {
        this.hotelBuilding = hotelBuilding;
        this.buildingId = hotelBuilding != null ? hotelBuilding.getIdBuilding() : null;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
}
