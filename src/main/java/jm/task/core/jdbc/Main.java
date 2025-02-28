package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Миша", "Павлов", (byte) 19);
        userService.saveUser("Алёна", "Петрова", (byte) 22);
        userService.saveUser("Римма", "Стоцкая", (byte) 20);
        userService.saveUser("Василий", "Глашин", (byte) 24);

        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.closeSessionFactory();
    }
}
