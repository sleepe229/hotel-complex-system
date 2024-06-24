package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.Organization;
import com.example.cringecoding.DBUtils.HibernateUtil;
import com.example.cringecoding.Models.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrganizationService {

    public List<Organization> getAllOrganizations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Organization", Organization.class).list();
        }
    }

    public List<Reservation> getReservationsByOrganizationName(String organizationName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Reservation WHERE organization.name = :organizationName";
            return session.createQuery(hql, Reservation.class)
                    .setParameter("organizationName", organizationName)
                    .list();
        }
    }

    public void deleteReservation(Long idReservation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Reservation reservation = session.get(Reservation.class, idReservation);
            if (reservation != null) {
                session.delete(reservation);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
