package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

//      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
//      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
//      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
//      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        userService.addUserWithCar(new User("User1", "Lastname1", "user1@mail.ru"), new Car("Audi", 1));
        userService.add(new User());
        userService.addUserWithCar(new User("User2", "Lastname2", "user2@mail.ru"), new Car("Mercedes", 2));
        userService.addUserWithCar(new User("User3", "Lastname3", "user3@mail.ru"), new Car("Ford", 3));
        userService.addUserWithCar(new User("User4", "Lastname4", "user4@mail.ru"), new Car("Renault", 4));

        List<User> users = userService.listUsersWithCars();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar());
            System.out.println();
        }
        System.out.println(userService.getUserByCarParam("Mercedes", 2));

        context.close();
    }
}
