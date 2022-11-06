package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLOutput;
import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    private static final User user1 = new User("Bov", "Morley", (byte) 25);
    private static final User user2 = new User("John", "Weak", (byte) 40);
    private static final User user3 = new User("Cole", "Bennet", (byte) 27);

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());

        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        List<User> strings = userService.getAllUsers();

        userService.removeUserById(3);

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeConnection();
    }
}
