import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

public class UserDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
        String input = scanner.nextLine();
        scanner.close();

        try {
            processUserData(input);
        } catch (InvalidDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void processUserData(String input) throws InvalidDataException {
        String[] userData = input.split("\\s+");

        if (userData.length != 6) {
            throw new InvalidDataException("Неверное количество данных. Введите данные в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол");
        }

        try {
            String surname = userData[0];
            String name = userData[1];
            String patronymic = userData[2];

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date birthDate = dateFormat.parse(userData[3]);

            long phoneNumber = Long.parseLong(userData[4]);

            char gender = userData[5].charAt(0);
            if (gender != 'f' && gender != 'm') {
                throw new InvalidDataException("Неверный формат пола. Используйте 'f' или 'm'.");
            }

            String fileName = surname + ".txt";
            String dataToWrite = String.format("%s %s %s %s %d %c%n", surname, name, patronymic, dateFormat.format(birthDate), phoneNumber, gender);

            writeToFile(fileName, dataToWrite);
        } catch (ParseException | NumberFormatException e) {
            throw new InvalidDataException("Неверный формат данных. Проверьте правильность ввода.");
        }
    }

    private static void writeToFile(String fileName, String data) throws InvalidDataException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
            System.out.println("Данные успешно записаны в файл: " + fileName);
        } catch (IOException e) {
            throw new InvalidDataException("Ошибка записи в файл. Проверьте права доступа или свободное место на диске.");
        }
    }
}
