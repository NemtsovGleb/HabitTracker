package security;

import models.Person;

import java.util.Scanner;

public class AuthProviderImpl {
    private static final Scanner scanner = new Scanner(System.in);
    public static void authenticate() {
        System.out.println("\n--- ВХОД ---");
        System.out.print("Введите логин: ");
        String username = scanner.nextLine().trim();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine().trim();

        for(Person p: Person.getPersons()) {
            if(p.getUsername().equals(username) && p.getPassword().equals(password)) {
                System.out.println("Вы успешно вошли");
                break;
            } else {
                System.out.println("Ошибка авторизации. Проверьте логин и пароль.");

            }
        }
    }


}
