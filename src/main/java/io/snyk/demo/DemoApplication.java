package io.snyk.demo;

import io.snyk.demo.controller.TokenUtil;
import io.snyk.demo.domain.Message;
import io.snyk.demo.domain.User;
import io.snyk.demo.repo.MessageRepo;
import io.snyk.demo.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(MessageRepo repository, UserRepo userRepo) {
        return (args) -> {
            // save a few of items to the grocery list
            repository.save(new Message("Admin", "Welcome to this messageboard"));
            repository.save(new Message("Admin", "Please be nice :)"));

            userRepo.save(new User("foo", "bar", TokenUtil.createNewToken()));


            // fetch all items on the grocery list
            System.out.println("Messages found with");
            System.out.println("-------------------------------");
            repository.findAll().forEach(System.out::println);

        };
    }

}

