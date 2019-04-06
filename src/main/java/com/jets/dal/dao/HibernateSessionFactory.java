package com.jets.dal.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    private static HibernateSessionFactory hibernateSessionFactory;

    private SessionFactory sessionFactory;
    private Session session;

    private HibernateSessionFactory() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public synchronized static HibernateSessionFactory getInstance() {
        if(hibernateSessionFactory == null) {
            hibernateSessionFactory = new HibernateSessionFactory();
        }
        return hibernateSessionFactory;
    }

    public void beginTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public Session getSession() {
        if(session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public void closeSession() {
        if(session != null) {
            session.close();
            session = null;
        }
    }

    public void endTransaction() {
        if(session != null) {
            session.getTransaction().commit();
            session.close();
            session = null;
        }
    }
}
