package com.example.cringecoding.DTO;

public class FloorDTO {
    private Long idFloor;
    private Integer numberOfFloor;
    private String buildingAddress;

    public FloorDTO(Long idFloor, Integer numberOfFloor, String buildingAddress) {
        this.idFloor = idFloor;
        this.numberOfFloor = numberOfFloor;
        this.buildingAddress = buildingAddress;
    }

    public Long getIdFloor() {
        return idFloor;
    }

    public void setIdFloor(Long idFloor) {
        this.idFloor = idFloor;
    }

    public Integer getNumberOfFloor() {
        return numberOfFloor;
    }

    public void setNumberOfFloor(Integer numberOfFloor) {
        this.numberOfFloor = numberOfFloor;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }
}
