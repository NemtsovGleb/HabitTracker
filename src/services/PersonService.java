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

    private Person currentPerson;

    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        this.personValidator =  new PersonValidator(peopleRepository);
        this.authProvider = new AuthProviderImpl(peopleRepository);
    }



    public void logout() {
        this.currentPerson = null;
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

    public boolean login() {
        return authProvider.authenticate();
    }

    public void showUserMenu() {
        boolean loggedIn = true;
        currentPerson = authProvider.getCurrentPerson();
        while(loggedIn) {
            System.out.println("\n --- Добро пожаловать " + currentPerson.getUsername() + "!");
            System.out.println("1. Показать информацию о пользователе");
            System.out.println("2. Выйти из аккаунта");
            System.out.print("Выберите действие (1 или 2): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    showUserInfo();
                    break;
                case "2":
                    System.out.println("Выход из аккаунта");
                    logout();
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }

        }
    }

    public void showUserInfo() {
        boolean status = true;
        while(status) {
            System.out.println("Имя пользователя: " + currentPerson.getUsername());
            System.out.println("Почта пользователя: " + currentPerson.getEmail());

            System.out.println("1. Изменить данные");
            System.out.println("2. Вернуться в меню");
            System.out.print("Выберите действие (1 или 2): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    editUserInfo();
                    break;
                case "2":
                    status = false;
                    break;
                default:
                    System.out.println("Неверный выбор пожалуйста повторите попытку");
            }



        }
    }

    public void editUserInfo() {
        boolean status = true;
        while(status) {

            System.out.println("1. Изменить логин");
            System.out.println("2. Изменить почту");
            System.out.println("3. Изменить пароль");
            System.out.println("4. Вернуться");

            System.out.print("Выберите действие (1,2,3 или 4): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    while (true) {
                        System.out.print("Введите новый логин или exit если передумали: ");
                        String username = scanner.nextLine().trim();
                        if(username.equals("exit"))
                            break;

                        if (personValidator.validateUsername(username)) {
                            currentPerson.setUsername(username);
                            System.out.println("Вы успешно изменили свой логин!");
                            break;
                        }

                        System.out.println("Логин введен неправильно, попробуйте еще.");
                    }
                    break;

                case "2":
                    System.out.println("Введите новую почту: ");
                    String email = scanner.nextLine().trim();
                    if (personValidator.validateEmail(email)) {
                        currentPerson.setEmail(email);
                        System.out.println("Вы успешно изменили свою почту!");
                    }
                    break; //

                case "3":
                    System.out.println("Введите ваш текущий пароль: ");
                    String password = scanner.nextLine().trim();
                    if(password.equals(currentPerson.getPassword())){
                        System.out.println("Введите новый пароль: ");
                        password = scanner.nextLine().trim();
                    }
                    System.out.println("Повторите новый пароль:");
                    String password2 = scanner.nextLine().trim();
                    if(password2.equals(password)) {
                        currentPerson.setPassword(password);
                        System.out.println("Вы успешно изменили свой пароль");
                    }
                    break;
                case "4":
                    status = false;
                    break;
                default:
                    System.out.println("Неверный выбор пожалуйста повторите попытку");
            }
            }


    }
}
