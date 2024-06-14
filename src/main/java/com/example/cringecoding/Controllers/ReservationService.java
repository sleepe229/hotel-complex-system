package com.example.cringecoding.Controllers;

import com.example.cringecoding.DBUtils.HibernateUtil;
import com.example.cringecoding.Models.Reservation;
import com.example.cringecoding.Models.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ReservationService {

    public void saveReservation(Reservation reservation) {
        executeTransaction(session -> session.save(reservation));
    }

    public List<Room> getAvailableRoomsByComplexAndType(String complexName, String roomType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Room r " +
                                    "WHERE r.floor.hotelBuilding.hotelComplex.complexName = :complexName " +
                                    "AND r.floor.hotelBuilding.roomType = :roomType " +
                                    "AND NOT EXISTS (SELECT 1 FROM Reservation res WHERE res.room = r AND res.curRoomStatus = 'booked')",
                            Room.class)
                    .setParameter("complexName", complexName)
                    .setParameter("roomType", roomType)
                    .list();
        }
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
