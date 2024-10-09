import models.Person;
import security.AuthProviderImpl;
import services.PersonService;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);



    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Войти");
            System.out.println("2. Создать аккаунт");
            System.out.println("3. Выйти из программы");
            System.out.print("Выберите действие (1, 2 или 3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                     AuthProviderImpl.authenticate();
                    break;
                case "2":
                     PersonService.createPerson();
                    break;
                case "3":
                    System.out.println("До свидания!");
                    running = false;  // Завершаем цикл, чтобы выйти из программы
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите 1, 2 или 3.");
            }
        }
    }
}