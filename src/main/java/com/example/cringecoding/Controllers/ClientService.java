package com.example.cringecoding.Controllers;

import com.example.cringecoding.Models.Client;
import com.example.cringecoding.DBUtils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

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

    public List<Client> getAllClients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Client", Client.class).list();
        }
    }

    public void deleteClient(String phoneNumber) {
        executeTransaction(session -> {
            Client client = session.get(Client.class, phoneNumber);
            if (client != null) {
                session.delete(client);
            }
        });
    }

    public List<Client> searchClientsByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Client WHERE name LIKE :name";
            return session.createQuery(hql, Client.class)
                    .setParameter("name", "%" + name + "%")
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
