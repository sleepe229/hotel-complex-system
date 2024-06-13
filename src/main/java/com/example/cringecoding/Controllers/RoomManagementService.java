package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.Room;
import com.example.cringecoding.DBUtils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RoomManagementService {

    public void saveRoom(Room room) {
        executeTransaction(session -> session.save(room));
    }

    public void updateRoom(Room room) {
        executeTransaction(session -> session.update(room));
    }

    public Room getRoomById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Room.class, id);
        }
    }

    public List<Room> getAllRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Room", Room.class).list();
        }
    }

    public void deleteRoom(Long id) {
        executeTransaction(session -> {
            Room room = session.get(Room.class, id);
            if (room != null) {
                session.delete(room);
            }
        });
    }

    private void executeTransaction(TransactionConsumer consumer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface TransactionConsumer {
        void accept(Session session) throws Exception;
    }
}
