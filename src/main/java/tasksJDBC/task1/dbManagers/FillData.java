package tasksJDBC.task1.dbManagers;

import java.sql.*;

public class FillData {

    public FillData() {
    }

    public void createAndFill(Connection connection) {
        try {
            if (!wasTheTableCreated(connection)) {
                createTable(connection);
                insertData(connection);
                System.out.println("The table was created\n");
            } else {
                System.out.println("The table has already been created\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean wasTheTableCreated(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tablesResultSet = metaData.getTables(null, null, "flats", null);
        return tablesResultSet.next();
    }

    private void createTable(Connection connection) throws SQLException {
        String createTable = "CREATE TABLE flats(id INT AUTO_INCREMENT PRIMARY KEY, district VARCHAR(30) NOT NULL," +
                "address VARCHAR(30) NOT NULL, area DOUBLE NOT NULL, rooms INT NOT NULL, cost DOUBLE NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);
        }
    }

    private void insertData(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO flats (district, address, area, rooms, cost) " +
                "VALUES(?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, "Babergh");
            preparedStatement.setString(2, "Broadway");
            preparedStatement.setDouble(3, 150);
            preparedStatement.setInt(4, 4);
            preparedStatement.setDouble(5, 705.5);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Blaby");
            preparedStatement.setString(2, "Keswick Road");
            preparedStatement.setDouble(3, 200);
            preparedStatement.setInt(4, 5);
            preparedStatement.setDouble(5, 900);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Herefordshire");
            preparedStatement.setString(2, "Queenswood Drive");
            preparedStatement.setDouble(3, 70);
            preparedStatement.setInt(4, 2);
            preparedStatement.setDouble(5, 550.4);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Milton Keynes");
            preparedStatement.setString(2, "Oxenhope Way");
            preparedStatement.setDouble(3, 500.5);
            preparedStatement.setInt(4, 10);
            preparedStatement.setDouble(5, 2000.8);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "West Lindsey");
            preparedStatement.setString(2, "Owmby Road");
            preparedStatement.setDouble(3, 100);
            preparedStatement.setInt(4, 3);
            preparedStatement.setDouble(5, 630.2);
            preparedStatement.executeUpdate();
        }
    }
}
