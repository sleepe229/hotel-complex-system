package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.Floor;
import com.example.cringecoding.DBUtils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FloorManagementService {

    public void saveFloor(Floor floor) {
        executeTransaction(session -> session.save(floor));
    }

    public void updateFloor(Floor floor) {
        executeTransaction(session -> session.update(floor));
    }

    public Floor getFloorById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Floor.class, id);
        }
    }

    public List<Floor> getAllFloors() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Floor", Floor.class).list();
        }
    }

    public void deleteFloor(Long id) {
        executeTransaction(session -> {
            Floor floor = session.get(Floor.class, id);
            if (floor != null) {
                session.delete(floor);
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
