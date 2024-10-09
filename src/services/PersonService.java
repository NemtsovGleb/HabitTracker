package services;

import models.Person;

import java.util.Scanner;

public class PersonService {


    private static final Scanner scanner = new Scanner(System.in);
    public static void createPerson() {
        Person person = new Person();
        System.out.println("Придумайте логин:");
        String username = scanner.nextLine();


        for(Person p: Person.getPersons()) {
            if(p.getUsername().equals(username))
                System.out.println("Аккаунт с таким логином уже существует. Попробуйте другой.");
        }

    }
}
