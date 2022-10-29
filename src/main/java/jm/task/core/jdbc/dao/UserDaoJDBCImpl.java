package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection conn;

    static {
        conn = Util.getConnection();
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql= "CREATE TABLE IF NOT EXIST user("+
                "id BIGINT(255) PRIMARY KEY AUTO_INCREMENT," +
                "Name varchar(255) NOT NULL," +
                "LastName varchar(50) NOT NULL," +
                "age INT(3)" +
                ");";
        try (Statement stm = conn.createStatement()) {
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS user " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(255), lastName VARCHAR(255), age INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void dropUsersTable() {
        try (Statement stm = conn.createStatement()) {
            stm.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")){
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement prst = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            prst.setLong(1, id);
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("last_name"), rs.getByte("age"));

                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
