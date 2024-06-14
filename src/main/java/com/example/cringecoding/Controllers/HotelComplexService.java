package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.HotelComplex;
import com.example.cringecoding.DBUtils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HotelComplexService {

    public void saveHotelComplex(HotelComplex hotelComplex) {
        executeTransaction(session -> session.save(hotelComplex));
    }

    public void updateHotelComplexAddress(String complexName, String newAddress) {
        executeTransaction(session -> {
            String hql = "UPDATE HotelComplex SET address = :newAddress WHERE complexName = :complexName";
            Query query = session.createQuery(hql);
            query.setParameter("newAddress", newAddress);
            query.setParameter("complexName", complexName);
            query.executeUpdate();
        });
    }

    public HotelComplex getHotelComplexByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(HotelComplex.class, name);
        }
    }

    public List<HotelComplex> getAllHotelComplexes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from HotelComplex", HotelComplex.class).list();
        }
    }

    public void deleteHotelComplex(String name, String address) {
        executeTransaction(session -> {
            String hql = "DELETE FROM HotelComplex WHERE complexName = :complexName OR address = :address";
            Query query = session.createQuery(hql);
            query.setParameter("complexName", name);
            query.setParameter("address", address);
            query.executeUpdate();
        });
    }

    public List<HotelComplex> searchComplexesByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM HotelComplex WHERE complexName LIKE :complexName";
            Query<HotelComplex> query = session.createQuery(hql, HotelComplex.class);
            query.setParameter("complexName", "%" + name + "%");
            return query.list();
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
