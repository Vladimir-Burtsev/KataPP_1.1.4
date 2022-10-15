package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "create table Users (\n" +
                "id bigint not null auto_increment,\n" +
                "age tinyint,\n" +
                "lastName varchar(255),\n" +
                "name varchar(255),\n" +
                "primary key (id)\n" +
                ")";
        Session session = getSession();
        getTransaction();
        session.createSQLQuery(sql).executeUpdate();
        closeTransaction();
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        Session session = getSession();
        getTransaction();
        session.createSQLQuery(sql).executeUpdate();
        closeTransaction();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSession();
        getTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        closeTransaction();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSession();
        getTransaction();
        User user = session.load(User.class, id);
        session.delete(user);
        session.flush();
        closeTransaction();
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";

        Session session = getSession();
        getTransaction();
        SQLQuery query = session.createSQLQuery(sql).addEntity(User.class);
        List<User> usersList = query.list();
        closeTransaction();
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        Session session = getSession();
        getTransaction();
        session.createSQLQuery(sql).executeUpdate();
        closeTransaction();
    }
}
