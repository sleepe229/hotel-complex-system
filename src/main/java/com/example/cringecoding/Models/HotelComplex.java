package com.example.cringecoding.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Hotel_Complex")
public class HotelComplex {

    @Id
    @Column(name = "complex_name")
    private String complexName;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "hotelComplex", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HotelBuilding> buildings;


    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<HotelBuilding> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<HotelBuilding> buildings) {
        this.buildings = buildings;
    }
}
