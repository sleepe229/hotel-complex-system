package com.example.cringecoding.DTO;

public class FloorDTO {
    private Long idFloor;
    private Long idBuilding;
    private Integer numberOfRooms;

    public FloorDTO(Long idFloor, Long idBuilding, Integer numberOfRooms) {
        this.idFloor = idFloor;
        this.idBuilding = idBuilding;
        this.numberOfRooms = numberOfRooms;
    }

    // Getters and setters
    public Long getIdFloor() {
        return idFloor;
    }

    public void setIdFloor(Long idFloor) {
        this.idFloor = idFloor;
    }

    public Long getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(Long idBuilding) {
        this.idBuilding = idBuilding;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
