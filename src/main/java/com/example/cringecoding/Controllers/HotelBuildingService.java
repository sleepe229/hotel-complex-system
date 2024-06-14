package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.HotelBuilding;
import com.example.cringecoding.DBUtils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HotelBuildingService {

    public List<HotelBuilding> getBuildingsByComplex(String complexName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM HotelBuilding WHERE hotel_complex_name = :complexName";
            return session.createQuery(hql, HotelBuilding.class)
                    .setParameter("complexName", complexName)
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
