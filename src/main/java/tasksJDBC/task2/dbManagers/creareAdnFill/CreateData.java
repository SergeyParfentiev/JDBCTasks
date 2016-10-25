package tasksJDBC.task2.dbManagers.creareAdnFill;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateData {

    private FillData fillData;
    public CreateData() {
        fillData = new FillData();
    }

    public void createAndFill(Connection connection) {
        try {
            cleanDB(connection);

            createTables(connection);

            createProcedures(connection);

            fillData.fill(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cleanDB(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS orders");
            statement.execute("DROP TABLE IF EXISTS clients");
            statement.execute("DROP TABLE IF EXISTS goods");
        }
    }

    private void createTables(Connection connection) throws SQLException {
        createGoodsTable(connection);
        createClientTable(connection);
        createOrdersTable(connection);
    }

    private void createGoodsTable(Connection connection) throws SQLException {
        String createGoods = "CREATE TABLE goods(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15) NOT NULL, " +
                "count INT NOT NULL, cost INT NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createGoods);
        }
    }

    private void createClientTable(Connection connection) throws SQLException {
        String createClients = "CREATE TABLE clients(id INT AUTO_INCREMENT PRIMARY KEY , name VARCHAR(15) NOT NULL, " +
                "surname VARCHAR(20) NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createClients);
        }
    }

    private void createOrdersTable(Connection connection) throws SQLException {
        String createOrders = "CREATE TABLE orders(id INT AUTO_INCREMENT PRIMARY KEY, goods_id INT NOT NULL, " +
                "clients_id INT NOT NULL, cost INT NOT NULL, sold INT NOT NULL, totalCost INT NOT NULL," +
                "date DATE NOT NULL,  FOREIGN KEY (goods_id) REFERENCES goods (id), " +
                "FOREIGN KEY (clients_id) REFERENCES clients (id))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createOrders);
        }
    }

    private void createProcedures(Connection connection) throws SQLException {
        checkGoodsNameProcedure(connection);
        checkGoodsCountProcedure(connection);
        checkClientsNameProcedure(connection);
    }

    private void checkGoodsNameProcedure(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP PROCEDURE IF EXISTS checkGoodsName");
        statement.execute("CREATE PROCEDURE checkGoodsName(IN goods_name VARCHAR(30), OUT res BOOLEAN)" +
                "BEGIN DECLARE i INT DEFAULT 1; DECLARE search_name VARCHAR(30); DECLARE id_count INT DEFAULT 2;" +
                "SELECT COUNT(*) INTO id_count FROM goods; SET res = FALSE;" +
                "loop_label: LOOP IF (res = TRUE OR i > id_count) THEN LEAVE loop_label; END IF;" +
                "SELECT goods.name INTO search_name FROM goods WHERE id = i;" +
                "IF (goods_name = search_name) THEN SET res = TRUE; END IF; SET i = i + 1; END LOOP; END");
    }

    private void checkGoodsCountProcedure(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP PROCEDURE IF EXISTS checkCount");
        statement.execute("CREATE PROCEDURE checkCount(IN goods_id INT, IN sold INT, OUT res BOOLEAN)" +
                "BEGIN DECLARE goods_count INT DEFAULT 0; SELECT goods.count INTO goods_count FROM goods WHERE id = goods_id; " +
                "IF (goods_count >= sold) THEN SET res = TRUE; ELSE SET res = FALSE; END IF; END");
    }

    private void checkClientsNameProcedure(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP PROCEDURE IF EXISTS checClientkName");
        statement.execute("CREATE PROCEDURE checClientkName(IN client_name VARCHAR(30), IN client_surname VARCHAR(30), OUT res BOOLEAN)" +
                "BEGIN DECLARE i INT DEFAULT 1; DECLARE search_name VARCHAR(30); DECLARE search_surname VARCHAR(30); DECLARE id_count INT;" +
                "SELECT COUNT(*) INTO id_count FROM clients; SET res = FALSE; " +
                "loop_label: LOOP IF (res = TRUE OR i < id_count) THEN LEAVE loop_label; END IF; " +
                "SELECT clients.name, clients.surname INTO search_name, search_surname FROM clients WHERE id = i;" +
                "IF (client_name = search_name AND client_surname = search_surname) THEN SET res = TRUE; END IF; SET i = i + 1; END LOOP; END");
    }
}
