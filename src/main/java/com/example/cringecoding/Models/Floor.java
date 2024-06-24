package com.example.cringecoding.Models;

import javax.persistence.*;
import java.util.Set;

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

    @Column(name = "number_of_floor")
    private int numberOfFloor;

    @OneToMany(mappedBy = "floor")
    private Set<ServiceEmployee> serviceEmployees;

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
    }

    public int getNumberOfFloor() {
        return numberOfFloor;
    }

    public void setNumberOfFloor(int numberOfFloor) {
        this.numberOfFloor = numberOfFloor;
    }

    public Set<ServiceEmployee> getServiceEmployees() {
        return serviceEmployees;
    }

    public void setServiceEmployees(Set<ServiceEmployee> serviceEmployees) {
        this.serviceEmployees = serviceEmployees;
    }
}
