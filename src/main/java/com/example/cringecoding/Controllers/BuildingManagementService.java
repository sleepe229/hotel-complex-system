package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.HotelBuilding;
import com.example.cringecoding.Models.HotelComplex;
import com.example.cringecoding.DBUtils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BuildingManagementService {

    public void saveBuilding(HotelBuilding building) {
        executeTransaction(session -> session.save(building));
    }

    public void updateBuildingAddress(Long id, String newAddress) {
        executeTransaction(session -> {
            String hql = "UPDATE HotelBuilding SET buildingAddress = :newAddress WHERE idBuilding = :id";
            Query query = session.createQuery(hql);
            query.setParameter("newAddress", newAddress);
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    public HotelBuilding getBuildingById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(HotelBuilding.class, id);
        }
    }

    public List<HotelBuilding> getAllBuildings() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from HotelBuilding", HotelBuilding.class).list();
        }
    }

    public void deleteBuilding(Long id) {
        executeTransaction(session -> {
            HotelBuilding building = session.get(HotelBuilding.class, id);
            if (building != null) {
                session.delete(building);
            }
        });
    }

    public List<HotelBuilding> searchBuildingsByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM HotelBuilding WHERE buildingAddress LIKE :name";
            Query<HotelBuilding> query = session.createQuery(hql, HotelBuilding.class);
            query.setParameter("name", "%" + name + "%");
            return query.list();
        }
    }

    public HotelComplex getHotelComplexByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(HotelComplex.class, name);
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
