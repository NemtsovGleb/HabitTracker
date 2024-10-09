package services;

import models.Person;
import repositories.PeopleRepository;
import security.AuthProviderImpl;
import util.PersonValidator;

import java.util.Scanner;

public class PersonService {

    private final PeopleRepository peopleRepository;
    private final PersonValidator personValidator;
    private final AuthProviderImpl authProvider;

    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        this.personValidator =  new PersonValidator(peopleRepository);
        this.authProvider = new AuthProviderImpl(peopleRepository);
    }

    private static final Scanner scanner = new Scanner(System.in);
    public void addPerson() {
        String username;
        String email;
        String password;

        // Ввод логина с циклом валидации
        while (true) {
            System.out.print("Введите логин: ");
            username = scanner.nextLine();
            if (personValidator.validateUsername(username)) {
                break; // Логин валидный и уникальный — продолжаем
            }
            System.out.println("Попробуйте снова ввести логин.");
        }

        // Ввод email с циклом валидации
        while (true) {
            System.out.print("Введите email: ");
            email = scanner.nextLine();
            if (personValidator.validateEmail(email)) {
                break; // Email валидный — продолжаем
            }
            System.out.println("Попробуйте снова ввести email.");
        }

        // Ввод пароля с циклом валидации
        while (true) {
            System.out.print("Введите пароль: ");
            password = scanner.nextLine();
            if (personValidator.validatePassword(password)) {
                break; // Пароль валидный — продолжаем
            }
            System.out.println("Попробуйте снова ввести пароль.");
        }

        // Если все данные валидны, создаем нового пользователя
        Person newPerson = new Person(username, password, email); // Создаем объект Person
        peopleRepository.addPerson(newPerson); // Добавляем пользователя в репозиторий
        peopleRepository.saveData(); // Сохраняем изменения в файл
        System.out.println("Пользователь успешно зарегистрирован: " + username);
    }

    public void login() {
        authProvider.authenticate();
    }
}
