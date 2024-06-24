package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.Client;
import com.example.cringecoding.DBUtils.HibernateUtil;
import com.example.cringecoding.Models.Report;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClientService {

    public void saveClient(Client client) {
        executeTransaction(session -> session.save(client));
    }

    public void updateClient(Client client) {
        executeTransaction(session -> session.update(client));
    }

    public Client getClientByPhoneNumber(String phoneNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Client.class, phoneNumber);
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

    public void saveReport(Report report) {
        executeTransaction(session -> session.save(report));
    }

    @FunctionalInterface
    private interface TransactionConsumer {
        void accept(Session session) throws Exception;
    }
}
