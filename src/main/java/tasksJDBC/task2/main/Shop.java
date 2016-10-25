package tasksJDBC.task2.main;

/*Создать проект «База данных заказов». Создать
таблицы «Товары» , «Клиенты» и «Заказы».
Написать код для добавления новых клиентов,
товаров и оформления заказов.
*/

import tasksJDBC.task2.logic.DataManager;

import java.util.Scanner;

public class Shop {
    public static void main(String[] args) {
        DataManager manager = new DataManager();

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
                        showAllGoods(manager);
                        break;
                    case "3":
                        showAllClients(manager);
                        break;
                    case "4":
                        showAllOrders(manager);
                        break;
                    case "5":
                        addNewProduct(manager, scanner);
                        break;
                    case "6":
                        addNewClient(manager, scanner);
                        break;
                    case "7":
                        makePurchase(manager, scanner);
                    default:
                        return;
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("1: Print this menu. 2: Show all goods. 3: Show all clients. 4: Show all orders. 5: Add new product. " +
                "6: Add new client. 7: To make a purchase. 8: Leave");
    }

    private static void showAllGoods(DataManager manager) {
        manager.getAllGoods();
    }

    private static void showAllClients(DataManager manager) {
        manager.getAllClients();
    }

    private static void showAllOrders(DataManager manager) {
        manager.getAllOrders();
    }

    private static void addNewProduct(DataManager manager, Scanner scanner) {
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();

        System.out.println("Enter product count: ");
        String count = scanner.nextLine();

        System.out.println("Enter product cost: ");
        String cost = scanner.nextLine();

        manager.addGoods(name, count, cost);
    }

    private static void addNewClient(DataManager manager, Scanner scanner) {
        System.out.println("Enter client name: ");
        String name = scanner.nextLine();

        System.out.println("Enter client surname: ");
        String surname = scanner.nextLine();

        manager.addClient(name, surname);
    }

   private static void makePurchase(DataManager manager, Scanner scanner) {
       System.out.println("Enter product name: ");
       String productName = scanner.nextLine();

       System.out.println("Enter how many want to by: ");
       String sold = scanner.nextLine();

       System.out.println("Enter client name: ");
       String clientName = scanner.nextLine();

       System.out.println("Enter client surname: ");
       String clientSurname = scanner.nextLine();

       manager.makePurchase(productName, clientName, clientSurname, sold);
   }
}
