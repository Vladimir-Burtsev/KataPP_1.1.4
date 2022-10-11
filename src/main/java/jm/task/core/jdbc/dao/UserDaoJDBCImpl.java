package jm.task.core.jdbc.dao;


import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL = "create table if not exists Users\n" +
                "(\n" +
                "    id       int auto_increment\n" +
                "        primary key,\n" +
                "    name VARCHAR(64) null,\n" +
                "    lastname VARCHAR(64) null,\n" +
                "    age      TINYINT     null\n" +
                ");";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS Users";
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();

            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO Users (name, lastname, age)VALUES (?, ?, ?)";
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM Users WHERE id=?";
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM Users";
        try {
            Statement statement = getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        String SQL = "DELETE FROM Users";
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
