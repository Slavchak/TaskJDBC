package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS Users " +
                "(id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name text NOT NULL, " +
                "lastName text NOT NULL, " +
                "age int not null)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreate);
        } catch (SQLException throwables) {
            System.out.println("Ошибка при создании таблицы");
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            System.out.println("Таблицы удалена");
        } catch (SQLException throwables) {
            System.out.println("Ошибка при удалении таблицы");
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSave = "INSERT INTO Users (name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSave)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            System.out.println("Ошибка при добавлении данных в таблицу");
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlRemoveById = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRemoveById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Ошибка при добавлении данных в таблицу");
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listUsers.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            System.out.println("При попытке достать всех пользователей из базы данных произошло исключение");
            e.printStackTrace();
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE Users");
            System.out.println("Данные из таблицы были удалены");
        } catch (SQLException throwables) {
            System.out.println("Ошибка при уданелении данных из таблицы");
            throwables.printStackTrace();
        }
    }
}
