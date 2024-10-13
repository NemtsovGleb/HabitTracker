package services;

import models.Habit;
import models.Person;
import repositories.PeopleRepository;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class HabitService {
    private final PeopleRepository peopleRepository;
    private static final Scanner scanner = new Scanner(System.in);
    private List<Habit> habits;

    public HabitService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public void viewHabits(Person currentPerson) {
        habits = currentPerson.getHabits();
        outer:
        while(true) {

            if (habits.isEmpty()) {
                System.out.println("\nПривычки отсутствуют.");
            } else {
                System.out.println("\n--- Ваши привычки ---");
                for (Habit habit : habits) {
                    System.out.println("Название: " + habit.getName() + "\nОписание: " + habit.getDescription() + "\nДата создания: " + habit.getCreateAt());
                    System.out.println("---------------");
                }
            }

            System.out.println("\n--- Управление ---");
            System.out.println("1. Добавить привычку");
            System.out.println("2. Посмотреь статистику по привычке");
            System.out.println("3. Удалить привычку");
            System.out.println("4. Редактировать привычку");
            System.out.println("5. Вернуться");

            System.out.print("Выберите действие (1-4): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    addHabit(currentPerson);
                    break;
                case "2":
                    break;
                case "3":
                    removeHabit(currentPerson);
                    break;
                case "4":
                    editHabit(currentPerson);
                    break;
                case "5":
                    break outer;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }
        }
    }

    public void addHabit(Person person) {
        System.out.print("Введите название новой привычки или exit если передумали: ");
        String name = scanner.nextLine().trim();
        if(name.equals("exit"))
            return;

        System.out.print("Введите описание привычки или exit если передумали: ");
        String description = scanner.nextLine().trim();
        if(description.equals("exit"))
            return;

        boolean status = true;
        String frequency = null;
        while(status) {
            System.out.println("Выберите частоту выполнения привычки:");
            System.out.println("1. Ежедневно");
            System.out.println("2. Еженедельно");
            System.out.println("Введите 1 или 2: ");

             frequency = scanner.nextLine().trim();

            switch(frequency) {
                case "1":
                    status = false;
                    frequency = "Ежедневно";
                    break;
                case "2":
                    status = false;
                    frequency = "Еженедельно";
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");

            }

        }

        Habit habit = new Habit(name, description, frequency, person);
        person.addHabit(habit);

        System.out.println("Привычка успешно добавлена!");
        peopleRepository.saveData();  // Сохраняем изменения в базе данных
    }

    public void editHabit(Person person) {
        boolean status = true;
        while(status) {
            System.out.println("\n--- РЕДАКТОР ПРИВЫЧЕК: ---");
            System.out.println("--- Ваши привычки ---");
            for (int i = 0; i < habits.size(); i++)
                System.out.println((i + 1) + ". " + habits.get(i).getName());

            System.out.println("Введите номер привычки для редактирования (1-" + habits.size()+ ") или exit если передумали: ");

            String choice = scanner.nextLine().trim();
            if(choice.equals("exit"))
                return;

            int intChoice = Integer.parseInt(choice);

            if (intChoice < 1 || intChoice > habits.size())
                System.out.println("Неверный выбор, попробуйте еще!");

            Habit habitToEdit = habits.get(intChoice - 1);

            if(!showEditMenu(habitToEdit))
                continue;


        }
    }

    private boolean showEditMenu(Habit habit) {

        while(true) {
            System.out.println("\nИнформация о привычке:");
            System.out.println("1. Название привычки: " + habit.getName());
            System.out.println("2. Описание привычки: " + habit.getDescription());
            System.out.println("3. Частота выполнения: " + habit.getFrequency());

            System.out.println("Введите номер параметра для редактирования (1-3) или exit если передумали: ");
            String choice = scanner.nextLine().trim();

            if(choice.equals("exit"))
                return false;

            switch(choice) {
                case "1":
                    editName(habit);
                    break;
                case "2":
                    editDescription(habit);
                    break;
                case "3":
                    editFrequency(habit);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }


        }
    }

    public void editName(Habit habit) {
        System.out.println("Введите новое название:");
        String name = scanner.nextLine().trim();
        habit.setName(name);
        System.out.println("Вы успешно изменили название!");
    }

    public void editDescription(Habit habit) {
        System.out.println("Введите новое описание:");
        String description = scanner.nextLine().trim();
        habit.setDescription(description);
        System.out.println("Вы успешно изменили описание!");
    }

    public void editFrequency(Habit habit) {
        boolean status = true;
        String frequency = null;
        while(status) {
            System.out.println("Выберите частоту выполнения привычки:");
            System.out.println("1. Ежедневно");
            System.out.println("2. Еженедельно");
            System.out.println("Введите 1 или 2: ");

            frequency = scanner.nextLine().trim();

            switch(frequency) {
                case "1":
                    status = false;
                    habit.setFrequency("Ежедневно");
                    break;
                case "2":
                    status = false;
                    habit.setFrequency("Еженедельно");
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");

            }
            System.out.println("Вы успешно изменили частоту привычки!");

        }
    }

    public void removeHabit(Person person) {
        List<Habit> habits = person.getHabits();

        if (habits.isEmpty()) {
            System.out.println("Нет привычек для удаления.");
            return;
        }

        System.out.println("Выберите привычку для удаления: ");
        for (int i = 0; i < habits.size(); i++) {
            System.out.println((i + 1) + ". " + habits.get(i).getName());
        }

        System.out.println("Введите номер привычки (1-" + habits.size()+ ") или exit если передумали: ");
        String choice = scanner.nextLine().trim();

        if(choice.equals("exit"))
            return;

        int intChoice = Integer.parseInt(choice);

        if (intChoice < 1 || intChoice > habits.size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        Habit habitToRemove = habits.get(intChoice - 1);
        person.removeHabit(habitToRemove);
        System.out.println("Привычка успешно удалена!");
        peopleRepository.saveData();  // Сохраняем изменения в базе данных
    }

}
