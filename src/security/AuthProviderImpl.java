package security;

import models.Person;
import repositories.PeopleRepository;

import java.util.Optional;
import java.util.Scanner;

public class AuthProviderImpl {

    private final PeopleRepository peopleRepository;
    private Person currentPerson;


    private static final Scanner scanner = new Scanner(System.in);

    public AuthProviderImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public boolean authenticate() {
        Optional<Person> person;

        while (true) {
            System.out.print("Введите логин или exit для выхода: ");
            String username = scanner.nextLine().trim();
            if(username.equals("exit"))
                return false;
            person = peopleRepository.findPersonByName(username);
            if (person.isPresent()) {
                break; // логин верный
            }
            System.out.println("Логин введен неправильно, попробуйте еще.");
        }

            Person person1 = person.get();

            while(true) {
                System.out.print("Введите пароль или exit для выхода: ");
                String password = scanner.nextLine().trim();
                if(password.equals("exit"))
                    return false;

                if (person1.getPassword().equals(password)) {
                    System.out.println("Вы успешно вошли в свой аккаунт");
                    currentPerson = person1;
                    break; // пароль верный
                }
                System.out.println("Пароль введен неправильно, поробуйте еще");
            }
            return true;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }


}
