package tasksJDBC.task1.logic;

import tasksJDBC.task1.dbManagers.DBProperties;
import tasksJDBC.task1.dbManagers.FillData;
import tasksJDBC.task1.dbManagers.GetData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataManager {

    private DBProperties properties;
    private FillData fill;
    private GetData getData;

    public DataManager() {
        properties = new DBProperties("dbFlats.properties");
        fill = new FillData();
        getData = new GetData();
    }

    public void create() {
        try {
            Class.forName(properties.getClassInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                properties.getUser(), properties.getPassword())) {

            fill.createAndFill(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchByDistrict(String districtName) {
        nameSearch("district", districtName);
    }

    public void searchByAddress(String addressName) {
        nameSearch("address", addressName);
    }

    public void searchByArea(String from, String to) {
        searchByDoubleNumbers("area", from, to);
    }

    public void searchByRooms(String from, String to) {
        searchByIntNumbers("rooms", from, to);
    }

    public void searchByCost(String form, String to) {
        searchByDoubleNumbers("cost", form, to);
    }

    private void searchByIntNumbers(String columnName, String from, String to) {
        if (from.matches("\\d+") && to.matches("\\d+")) {
            try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                    properties.getUser(), properties.getPassword())) {

                getData.byIntNumbers(columnName, Integer.parseInt(from),
                        Integer.parseInt(to), connection).forEach(System.out::println);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println();
        } else {
            System.out.println("You entered an incorrect value");
        }
    }

    private void searchByDoubleNumbers(String columnName, String from, String to) {
        if (checkDouble(from) && checkDouble(to)) {
            try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                    properties.getUser(), properties.getPassword())) {

                getData.byDoubleNumbers(columnName, Double.parseDouble(from),
                        Double.parseDouble(to), connection).forEach(System.out::println);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println();
        } else {
            System.out.println("You entered an incorrect value");
        }
    }

    private boolean checkDouble(String value) {
        boolean result;
        try {
            Double.valueOf(value);
            result = true;
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    private void nameSearch(String columnName, String name) {
        if (name != null && !name.isEmpty()) {
            try (Connection connection = DriverManager.getConnection(properties.getUrl(),
                    properties.getUser(), properties.getPassword())) {

                getData.byName(columnName, name, connection).forEach(System.out::println);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println();
        } else {
            System.out.println("You entered an incorrect value");
        }
    }
}
