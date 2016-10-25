package tasksJDBC.task2.dbManagers.workWithData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Clients {

    public Clients() {
    }

    public void add(Connection connection, String name, String surname) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO clients (name, surname) " +
                "VALUES(?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.executeUpdate();
            System.out.println("The client " + name + " " + surname + " added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDuplicateName(Connection connection, String name, String surname) {
        boolean result;

        try (CallableStatement callableStatement = connection.prepareCall("{call checkClientName(?,?,?)}")) {

            callableStatement.setString(1, name);
            callableStatement.setString(2, surname);
            callableStatement.registerOutParameter(3, Types.BOOLEAN);
            callableStatement.execute();

            result = callableStatement.getBoolean(3);
        } catch (SQLException e) {
            result = true;
        }
        return result;
    }

    public List<String> getAllClients(Connection connection) {
        List<String> allClients = new ArrayList<>();

        Formatter formatter = new Formatter();
        Object[] types = {"Name", "Surname"};
        String formattedTypes = formatter.format("%-20s%-20s",
                types).toString();
        allClients.add("==============================");
        allClients.add(formattedTypes);
        allClients.add("==============================");

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clients")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Object[] data = new Object[2];
                data[0] = resultSet.getString(2);
                data[1] = resultSet.getString(3);
                formatter = new Formatter();
                String formattedData = String.valueOf(formatter.format("%-20s%-20s", data));
                allClients.add(formattedData);
                allClients.add("------------------------------");

            }
            allClients.remove(allClients.size() - 1);
            allClients.add("==============================");
        } catch (SQLException e) {
            e.printStackTrace();
            return allClients;
        }
        return allClients;
    }

    public int getIdByName(Connection connection, String name, String surname) {
        int id = 0;
        String getId = "SELECT id FROM clients WHERE name = ? AND surname = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(getId)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
