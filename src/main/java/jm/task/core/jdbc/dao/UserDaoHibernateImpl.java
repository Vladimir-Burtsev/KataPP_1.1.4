package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private Transaction transaction;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "create table if not exists Users\n" +
                "(\n" +
                "id int auto_increment\n" +
                "primary key,\n" +
                "name VARCHAR(64) null,\n" +
                "lastname VARCHAR(64) null,\n" +
                "age TINYINT null\n" +
                ");";

        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){transaction.rollback();}
            e.getStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){transaction.rollback();}
            e.getStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){transaction.rollback();}
            e.getStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.flush();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){transaction.rollback();}
            e.getStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()){
            TypedQuery<User> query =
                    session.createQuery("SELECT w FROM User w", User.class);
            return query.getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){transaction.rollback();}
            e.getStackTrace();
        }
    }
}
