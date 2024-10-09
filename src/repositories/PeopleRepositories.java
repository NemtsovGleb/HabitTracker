package repositories;

import models.Person;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class PeopleRepositories {
    private static final String FILE_NAME = "people.db";
    public static void saveData(List<Person> people) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(people);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для загрузки данных из файла
    public static List<Person> loadData() {

        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Person>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Если файла нет, возвращаем пустой список
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
