package tasksJDBC.task1.main;

import tasksJDBC.task1.logic.DataManager;

import java.util.Scanner;

/*Спроектировать базу «Квартиры». Каждая запись
в базе содержит данные о квартире (район,
адрес, площадь, кол. комнат, цена). Сделать
возможность выборки квартир из списка по
параметрам.
*/

public class Flats {

    public static void main(String[] args) {
        DataManager manager = new DataManager();
        manager.create();

        printMenu();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter number of menu: ");
                String value = scanner.nextLine();

                switch (value) {
                    case "1":
                        printMenu();
                        break;
                    case "2":
                        manager.searchByDistrict(searchByName(scanner));
                        break;
                    case "3":
                        manager.searchByAddress(searchByName(scanner));
                        break;
                    case "4":
                        manager.searchByArea(searchByNumberFrom(scanner), searchByNumberTo(scanner));
                        break;
                    case "5":
                        manager.searchByRooms(searchByNumberFrom(scanner), searchByNumberTo(scanner));
                        break;
                    case "6":
                        manager.searchByCost(searchByNumberFrom(scanner), searchByNumberTo(scanner));
                        break;
                    default:
                        return;
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("1: Print this menu. 2: Sear by district. 3: Sear by address. 4: Sear by area. 5: Sear by rooms. " +
                "6: Sear by cost. 7: Leave.");
    }

    private static String searchByName(Scanner scanner) {
        System.out.println("Enter name: ");
        return scanner.nextLine();
    }

    private static String searchByNumberFrom(Scanner scanner) {
        System.out.println("Enter from: ");
        return scanner.nextLine();
    }

    private static String searchByNumberTo(Scanner scanner) {
        System.out.println("Enter to: ");
        return scanner.nextLine();
    }
}
