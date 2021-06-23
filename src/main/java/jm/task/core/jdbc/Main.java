package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Anton", "Ivanov", (byte) 23);
        userService.saveUser("Anna", "Sergeeva", (byte) 28);
        userService.saveUser("Sergey", "Petrov", (byte) 35);
        userService.saveUser("Marina", "Medvedeva", (byte) 39);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
