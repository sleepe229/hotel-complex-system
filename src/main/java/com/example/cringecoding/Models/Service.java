package com.example.cringecoding.Models;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @Column(name = "service")
    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
