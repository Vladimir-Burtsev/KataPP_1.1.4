package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        List<User> users = new ArrayList<>();
        users.add(new User("Victor", "Semionov", (byte) 34));
        users.add(new User("Svetlana", "Ignatieva", (byte) 28));
        users.add(new User("Atrem", "Sviridov", (byte) 51));
        users.add(new User("Alla", "Veselchuk", (byte) 78));
         for (User user : users){
             userService.saveUser(user.getName(), user.getLastName(), user.getAge());
             System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
         }
         userService.getAllUsers();
         userService.cleanUsersTable();
         userService.dropUsersTable();
    }
}
