package services;

import models.Habit;
import models.Person;
import repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HabitService {
    private final PeopleRepository peopleRepository;
    private static final Scanner scanner = new Scanner(System.in);

    public HabitService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public void addHabit(Person person) {
        System.out.print("Введите название новой привычки: ");
        String name = scanner.nextLine().trim();

        System.out.print("Введите описание привычки: ");
        String description = scanner.nextLine().trim();

        Habit habit = new Habit(name, description, person);
        person.addHabit(habit);

        System.out.println("Привычка успешно добавлена!");
        peopleRepository.saveData();  // Сохраняем изменения в базе данных
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

        int choice = scanner.nextInt();
        scanner.nextLine();  // Поглощаем оставшийся `\n`

        if (choice < 1 || choice > habits.size()) {
            System.out.println("Неверный выбор!");
            return;
        }

        Habit habitToRemove = habits.get(choice - 1);
        person.removeHabit(habitToRemove);
        System.out.println("Привычка успешно удалена!");
        peopleRepository.saveData();  // Сохраняем изменения в базе данных
    }

    public void viewHabits(Person person) {
        List<Habit> habits = person.getHabits();

        if (habits.isEmpty()) {
            System.out.println("\nПривычки отсутствуют.");
        } else {
            System.out.println("\n--- Ваши привычки ---");
            for (Habit habit : habits) {
                System.out.println("Название: " + habit.getName() + "\nОписание: " + habit.getDescription() + "\nДата создания: " + habit.getCreateAt());
                System.out.println("---------------");
            }
        }
    }
}
