package tasksJDBC.task2.dbManagers.creareAdnFill;

import java.sql.*;

public class FillData {

    private ConvertDate convertDate;

    public FillData() {
        convertDate = new ConvertDate();
    }

    public void fill(Connection connection) {
        try {
            fillGoods(connection);
            fillClients(connection);
            fillOrders(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillGoods(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO goods (name, count, cost) " +
                "VALUES(?, ?, ?)")) {

            preparedStatement.setString(1, "book");
            preparedStatement.setInt(2, 10);
            preparedStatement.setInt(3, 30);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "cup");
            preparedStatement.setInt(2, 20);
            preparedStatement.setInt(3, 15);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "sofa");
            preparedStatement.setInt(2, 4);
            preparedStatement.setInt(3, 100);
            preparedStatement.executeUpdate();
        }
    }

    private void fillClients(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO clients (name, surname) " +
                "VALUES(?, ?)")) {

            preparedStatement.setString(1, "Vanya");
            preparedStatement.setString(2, "Pavlov");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Alex");
            preparedStatement.setString(2, "Kamushkin");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Sveta");
            preparedStatement.setString(2, "Bukina");
            preparedStatement.executeUpdate();
        }
    }

    private void fillOrders(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders (goods_id, clients_id, " +
                "cost, sold, totalCost, date) " +
                "VALUES(?, ?, ?, ?, ?, ?)")) {

            String stringDate = "2012-09-30";
            Date date = convertDate.convert(stringDate);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 30);
            preparedStatement.setInt(4, 2);
            preparedStatement.setInt(5, 60);
            preparedStatement.setDate(6, date);
            preparedStatement.executeUpdate();

            stringDate = "2014-11-18";
            date = convertDate.convert(stringDate);
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, 2);
            preparedStatement.setInt(3, 15);
            preparedStatement.setInt(4, 5);
            preparedStatement.setInt(5, 75);
            preparedStatement.setDate(6, date);
            preparedStatement.executeUpdate();

            stringDate = "2000-01-04";
            date = convertDate.convert(stringDate);
            preparedStatement.setInt(1, 3);
            preparedStatement.setInt(2, 3);
            preparedStatement.setInt(3, 100);
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5, 100);
            preparedStatement.setDate(6, date);
            preparedStatement.executeUpdate();
        }
    }
}
