package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Cung cap EntityManager cho toan bo tang DAO.
 * EntityManagerFactory chi duoc khoi tao MOT LAN duy nhat (static) vi day
 * la doi tuong "nang", ton nhieu chi phi neu tao lai nhieu lan.
 */
public class JPAConfig {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("jpa-hibernate-mysql");

    private JPAConfig() {
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
