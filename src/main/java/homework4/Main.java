//package org.example;
//
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//public class Main {
//    public static void main(String[] args) throws Exception {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//        DataSource dataSource = context.getBean(DataSource.class);
//
//        try (Connection conn = dataSource.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT 1")) {
//
//            if (rs.next()) {
//                int result = rs.getInt(1);
//                System.out.println("✅ БД ответила: " + result);
//            }
//        }
//
//        context.close();
//    }
//}

package homework4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var dataSource = context.getBean(javax.sql.DataSource.class);

        // DAO и сервис
        var userDao = new UserDaoImpl(dataSource);
        var userService = new UserServiceImpl(userDao);


        // Создал таблицу
        userDao.createTable();

        // Создание
        userService.registerUser("User 1");

        // Получение
        var users = userService.getAllUsers();
        users.forEach(System.out::println);

        // Переименование
        if (!users.isEmpty()) {
            var firstUser = users.get(0);
            userService.renameUser(firstUser.getId(), "User 2");
            System.out.println("✅ После переименования:");
            userService.getAllUsers().forEach(System.out::println);
        }

        // Удаление
        if (!users.isEmpty()) {
            var firstUser = users.get(0);
            userService.deleteUser(firstUser.getId());
            System.out.println("✅ После удаления:");
            userService.getAllUsers().forEach(System.out::println);
        }

        // проверка валидации Null
        userService.registerUser(null);

        // проверка валидации на длинну имени
        userService.registerUser("Us");
        userService.registerUser("User User User User User User User");

        // проверка валидации на ^[a-zA-Z0-9_ ]+$
        userService.registerUser("User !");

        System.out.println("Записи в таблице:");
        userService.getAllUsers().forEach(System.out::println);

        userDao.dropTable();



        context.close();
    }
}