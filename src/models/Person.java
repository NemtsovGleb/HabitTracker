package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Person {
    private static final Scanner scanner = new Scanner(System.in);
    static List<Person> persons = new ArrayList<>();

    String username;

    String password;

    String email;

    List<Habit> habits;

    public Person(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public static List<Person> getPersons() {
        return persons;
    }

    public static void setPersons(List<Person> persons) {
        Person.persons = persons;
    }

    public Person(){}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public static void createPerson() {
        Person person = new Person();
        System.out.println("Придумайте логин:");
        String username = scanner.nextLine();


        for(Person p: persons) {
            if(p.getUsername().equals(username))
                System.out.println("Аккаунт с таким логином уже существует. Попробуйте другой.");
        }

    }

    static {
        persons.add(new Person("Gleb", "123", "gleb@mail.ru"));
        persons.add(new Person("Sergey", "456", "sergey@mail.ru"));
        persons.add(new Person("Ilya", "789", "ilya@mail.ru"));
        persons.add(new Person("Andrey", "101112", "andrey@mail.ru"));
    }


}
