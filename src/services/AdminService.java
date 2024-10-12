package services;

import models.Person;
import repositories.PeopleRepository;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    private final PeopleRepository peopleRepository;
    List<Person> people;
    private static final Scanner scanner = new Scanner(System.in);

    public AdminService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public void showUsers() {
        people = peopleRepository.getAllPeople();
        boolean status = true;
        while(status) {


            int index = 1;
            System.out.println("\n Список пользователй:");
            for (Person person : people) {

                System.out.println(index + ". " + person.getUsername() + " " + person.getRole());
                index++;
            }

            System.out.println("\n--- Управление ---");
            System.out.println("1. Удалить пользователя");
            System.out.println("2. Заблокировать пользователя");
            System.out.println("3. Вернуться");

            System.out.print("Выберите действие (1 или 2): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    removeByName();
                    break;
                case "2":
                    break;
                case "3":
                    status = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }

        }
    }


    public void removeByName() {
        boolean status = true;
        while(status) {
            System.out.println("Напишите имя человека, которого хотите удалить или exit если передумали:");
            String name = scanner.nextLine().trim();
            if(name.equals("exit"))
                break;


            if(peopleRepository.findPersonByName(name).isEmpty()) {
                System.out.println("Такого человека нет, попробуйте еще");
                continue;
            }

            peopleRepository.removeByName(name);

            status = false;

        }
    }
}
