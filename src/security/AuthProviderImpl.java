package security;

import models.Person;
import org.w3c.dom.ls.LSOutput;
import repositories.PeopleRepository;

import java.util.Optional;
import java.util.Scanner;

public class AuthProviderImpl {

    private final PeopleRepository peopleRepository;


    private static final Scanner scanner = new Scanner(System.in);

    public AuthProviderImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public void authenticate() {
        Optional<Person> person;
        while (true) {
            System.out.print("Введите логин: ");
            String username = scanner.nextLine().trim();
            person = peopleRepository.findPersonByName(username);
            if (person.isPresent()) {
                break; // логин верный
            }
            System.out.println("Логин введен неправильно, попробуйте еще.");
        }

            Person person1 = person.get();

            while(true) {
                System.out.print("Введите пароль: ");
                String password = scanner.nextLine().trim();

                if (person1.getPassword().equals(password)) {
                    System.out.println("Вы успешно вошли в свой аккаунт");
                    break; // пароль верный
                }
                System.out.println("Пароль введен неправильно, поробуйте еще");
            }






    }


}
