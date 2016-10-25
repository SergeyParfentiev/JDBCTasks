package tasksJDBC.task2.logic;

import tasksJDBC.task1.dbManagers.DBProperties;
import tasksJDBC.task2.dbManagers.creareAdnFill.CreateData;
import tasksJDBC.task2.dbManagers.workWithData.Clients;
import tasksJDBC.task2.dbManagers.workWithData.Goods;
import tasksJDBC.task2.dbManagers.workWithData.Orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DataManager {

    private DBProperties properties;
    private CreateData createData;
    private Clients clients;
    private Goods goods;
    private Orders orders;


    private List<Integer> godsIdAndCost;
    private int idClients;

    public DataManager() {
        properties = new DBProperties("dbOrders.properties");
        createData = new CreateData();

//        create();

        clients = new Clients();
        goods = new Goods();
        orders = new Orders();
    }

    private void create() {
        try {
            Class.forName(properties.getClassInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                properties.getUser(), properties.getPassword())) {

            createData.createAndFill(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllClients() {
        try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                properties.getUser(), properties.getPassword())) {

            clients.getAllClients(connection).forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllGoods() {
        try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                properties.getUser(), properties.getPassword())) {

            goods.getAllGoods(connection).forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllOrders() {
        try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                properties.getUser(), properties.getPassword())) {

            orders.getAllOrders(connection).forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addClient(String name, String surname) {
        try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                properties.getUser(), properties.getPassword())) {

            if (!clients.isDuplicateName(connection, name, surname)) {
                clients.add(connection, name, surname);

                System.out.println("The client added. Name: " + name + ". Surname: " + surname);
            } else {
                System.out.println("The client already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGoods(String name, String countStr, String costStr) {
        if (isCorrectIntFromString(countStr) && isCorrectIntFromString(costStr)) {
            int count = Integer.parseInt(costStr);
            int cost = Integer.parseInt(costStr);

            try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                    properties.getUser(), properties.getPassword())) {
                if (!goods.isDuplicateName(connection, name)) {
                    goods.add(connection, name, count, cost);

                    System.out.println("The goods added. Name: " + name + ". Count: " + count + ". Cost: " + cost);
                } else {
                    System.out.println("The goods already exists");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You entered an incorrect value");
        }
    }

    private boolean isCorrectIntFromString(String value) {
        return value.matches("\\d+");
    }

    public void makePurchase(String goodsName, String clientName, String clientSurname, String soldStr) {
        try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                properties.getUser(), properties.getPassword())) {

            if (isCorrectValuesInOrderRequest(connection, goodsName, clientName, clientSurname, soldStr)) {

                if (isGoodsCountMoreThenBy(connection, soldStr)) {
                    orders.add(connection, godsIdAndCost.get(0), idClients, godsIdAndCost.get(1), Integer.parseInt(soldStr));

                    System.out.println("You purchase is successful");
                } else {
                    System.out.println("Count is less than you want to by");
                }

            } else {
                System.out.println("You entered an incorrect value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isCorrectValuesInOrderRequest(Connection connection, String goodsName, String clientName, String clientSurname, String soldStr) {
        return soldStr.matches("\\d+") && (godsIdAndCost = goods.getIdAndCostByName(connection, goodsName)).size() > 0 &&
                (idClients = clients.getIdByName(connection, clientName, clientSurname)) != 0;
    }

    private boolean isGoodsCountMoreThenBy(Connection connection, String soldStr) {
        return goods.isCountMoreThenBy(connection, godsIdAndCost.get(0), Integer.parseInt(soldStr));
    }

    public static void main(String[] args) throws SQLException{
        DBProperties properties = new DBProperties("dbOrders.properties");
        try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                properties.getUser(), properties.getPassword())) {
            System.out.println();
        }
    }
}
