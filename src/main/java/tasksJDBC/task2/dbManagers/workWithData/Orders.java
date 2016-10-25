package tasksJDBC.task2.dbManagers.workWithData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

public class Orders {

    public Orders() {
    }

    public List<String> getAllOrders(Connection connection) {
        List<String> allOrders = new ArrayList<>();

        Formatter formatter = new Formatter();
        Object[] types = {"Goods name", "Clients name", "Clients surname", "Cost", "Sold", "TotalCost", "Date"};
        String formattedTypes = formatter.format("%-15s%-20s%-20s%-8s%-8s%-10s%-10s",
                types).toString();
        allOrders.add("===========================================================================================");
        allOrders.add(formattedTypes);
        allOrders.add("===========================================================================================");

        String allOrdersRequest = "SELECT g.name, c.name, c.surname, o.cost, o.sold, o.totalCost, o.date FROM orders o " +
                "INNER JOIN goods g ON g.id = o.goods_id INNER JOIN clients c ON c.id = o.clients_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(allOrdersRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object[] data = new Object[7];
                data[0] = resultSet.getString(1);
                data[1] = resultSet.getString(2);
                data[2] = resultSet.getString(3);
                data[3] = resultSet.getInt(4);
                data[4] = resultSet.getInt(5);
                data[5] = resultSet.getInt(6);
                data[6] = resultSet.getString(7);

                formatter = new Formatter();
                String formattedData = String.valueOf(formatter.format("%-15s%-20s%-20s%-8d%-8d%-10d%-10s", data));
                allOrders.add(formattedData);
                allOrders.add("-------------------------------------------------------------------------------------------");

            }
            allOrders.remove(allOrders.size() - 1);
            allOrders.add("===========================================================================================");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allOrders;
    }

    public void add(Connection connection, int goodsId, int clientId, int cost, int sold) throws SQLException {
        try {
            connection.setAutoCommit(false);

            changeGoodsCount(connection, goodsId, sold);

            addOrder(connection, goodsId, clientId, cost, sold);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void changeGoodsCount(Connection connection, int goodsId, int sold) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  goods SET count = count - ? " +
                "WHERE id = ?")) {
            preparedStatement.setInt(1, sold);
            preparedStatement.setInt(2, goodsId);
            preparedStatement.executeUpdate();
        }
    }

    private void addOrder(Connection connection, int goodsId, int clientId, int cost, int sold) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders (goods_id, clients_id, " +
                "cost, sold, totalCost, date) " +
                "VALUES(?, ?, ?, ?, ?, ?)")) {

            Date date = new Date(Calendar.getInstance().getTime().getTime());

            preparedStatement.setInt(1, goodsId);
            preparedStatement.setInt(2, clientId);
            preparedStatement.setInt(3, cost);
            preparedStatement.setInt(4, sold);
            preparedStatement.setInt(5, cost * sold);
            preparedStatement.setDate(6, date);
            preparedStatement.executeUpdate();
        }
    }
}

