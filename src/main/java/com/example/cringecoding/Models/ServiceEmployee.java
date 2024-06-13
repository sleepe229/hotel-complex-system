package com.example.cringecoding.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "service_employee")
public class ServiceEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Long idEmployee;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "kind_of_service")
    private String kindOfService;

    @ManyToOne
    @JoinColumn(name = "id_floor", nullable = false)
    private Floor floor;

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getKindOfService() {
        return kindOfService;
    }

    public void setKindOfService(String kindOfService) {
        this.kindOfService = kindOfService;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
