package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Arrays;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootSecurityDemoApplication.class, args);

		UserServiceImpl userService = context.getBean(UserServiceImpl.class);
		RoleServiceImpl roleService = context.getBean(RoleServiceImpl.class);

		Role roleAdmin = new Role("ROLE_ADMIN");
		roleService.saveRole(roleAdmin);
		Role roleUser = new Role("ROLE_USER");
		roleService.saveRole(roleUser);

		User user1 = new User("Ivan", "Ivanov", 25, "ivan@email.com", "ivan");
		user1.setRoles(Arrays.asList(roleAdmin, roleUser));
		User user2 = new User("Petr", "Petrov", 36, "petr@email.com", "petr");
		user2.setRoles(Arrays.asList(roleUser));
		User user3 = new User("Masha", "Markova", 30, "masha@email.com", "masha");
		user3.setRoles(Arrays.asList(roleUser));

		userService.save(user1);
		userService.save(user2);
		userService.save(user3);



	}

}
