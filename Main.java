import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "textfile.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nОберіть дію:");
            System.out.println("1 - Записати у файл");
            System.out.println("2 - Прочитати вміст файлу");
            System.out.println("3 - Прочитати рядки з діапазону");
            System.out.println("4 - Записати в обраний рядок");
            System.out.println("5 - Вийти з редактора");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                writeToFile();
            } else if (choice.equals("2")) {
                readFromFile();
            } else if (choice.equals("3")) {
                readRange();
            } else if (choice.equals("4")) {
                insertAtLine();
            } else if (choice.equals("5")) {
                System.out.println("Вихід з редактора.");
                break;
            } else {
                System.out.println("Некоректний вибір, спробуйте ще раз.");
            }
        }
    }

    private static void writeToFile() {
        System.out.println("Оберіть режим запису:");
        System.out.println("1 - Перезаписати файл");
        System.out.println("2 - Додати в кінець файлу");
        String mode = scanner.nextLine();
        boolean append = mode.equals("2");

        System.out.println("Введіть кількість рядків для запису:");
        int count = Integer.parseInt(scanner.nextLine());

        String[] lines = new String[count];
        for (int i = 0; i < count; i++) {
            System.out.print("Рядок " + (i + 1) + ": ");
            lines[i] = scanner.nextLine();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, append))) {
            for (int i = 0; i < count; i++) {
                bw.write(lines[i]);
                bw.newLine();
            }
            System.out.println("Дані успішно записані у файл.");
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл: " + e.getMessage());
        }
    }

    private static void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int number = 1;
            while ((line = br.readLine()) != null) {
                System.out.println(number + ": " + line);
                number++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не знайдено. Спочатку запишіть дані.");
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
        }
    }

    private static void readRange() {
        System.out.print("Введіть початковий номер рядка: ");
        int start = Integer.parseInt(scanner.nextLine());
        System.out.print("Введіть кінцевий номер рядка: ");
        int end = Integer.parseInt(scanner.nextLine());

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int number = 1;
            while ((line = br.readLine()) != null) {
                if (number >= start && number <= end) {
                    System.out.println(number + ": " + line);
                }
                if (number > end) {
                    break;
                }
                number++;
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
        }
    }

    private static void insertAtLine() {
        System.out.print("Введіть номер рядка для вставки: ");
        int target = Integer.parseInt(scanner.nextLine());

        System.out.println("Введіть рядок для вставки:");
        String newLine = scanner.nextLine();

        String[] lines = new String[1000];
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null && count < lines.length) {
                lines[count] = line;
                count++;
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < count; i++) {
                if (i == target - 1) {
                    bw.write(newLine);
                    bw.newLine();
                }
                bw.write(lines[i]);
                bw.newLine();
            }
            if (target > count) {
                bw.write(newLine);
                bw.newLine();
            }
            System.out.println("Рядок успішно вставлений.");
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл: " + e.getMessage());
        }
    }
}
