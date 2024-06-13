package com.example.cringecoding.Models;

import javax.persistence.*;

@Entity
@Table(name = "Rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "floor_id", referencedColumnName = "id_floor")
    private Floor floor;

    @Column(name = "room_number")
    private int roomNumber;

    @Transient
    private Long floorId;

    @PostLoad
    private void postLoad() {
        this.floorId = floor != null ? floor.getIdFloor() : null;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
        this.floorId = floor != null ? floor.getIdFloor() : null;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }
}
