package tasksJDBC.task1.dbManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class GetData {

    private final String byNames = "name";
    private final String byIntNumbers = "intNumber";
    private final String byDoubleNumbers = "byDoubleNumbers";

    private String name;
    private int intFrom;
    private int intTo;

    private double doubleFrom;
    private double doubleTo;

    public GetData() {
    }

    public List<String> byName(String columnName, String name, Connection connection) {
        this.name = name;
        return search(byNames, columnName, connection);
    }

    public List<String> byIntNumbers(String columnName, int from, int to, Connection connection) {
        this.intFrom = from;
        this.intTo = to;
        return search(byIntNumbers, columnName, connection);
    }

    public List<String> byDoubleNumbers(String columnName, double doubleFrom, double doubleTo, Connection connection) {
        this.doubleFrom = doubleFrom;
        this.doubleTo = doubleTo;
        return search(byDoubleNumbers, columnName, connection);
    }

    private List<String> search(String type, String columnName, Connection connection) {
        List<String> resultList = new ArrayList<>();

        Formatter formatter = new Formatter();
        Object[] types = {"District", "Address", "Area", "Rooms", "Cost"};
        String formattedTypes = formatter.format("%-15s%-25s%-8s%-7s%-4s",
                types).toString();
        resultList.add("================================================================");
        resultList.add(formattedTypes);
        resultList.add("================================================================");
        String request;
        PreparedStatement preparedStatement;
        try {
            switch (type) {
                case byNames:
                    request = "SELECT * FROM flats WHERE " + columnName + " = ?";
                    preparedStatement = connection.prepareStatement(request);
                    preparedStatement.setString(1, name);
                    break;
                case byIntNumbers:
                    request = "SELECT * FROM flats WHERE " + columnName + " BETWEEN ? AND ?";
                    preparedStatement = connection.prepareStatement(request);
                    preparedStatement.setInt(1, intFrom);
                    preparedStatement.setInt(2, intTo);
                    break;
                case byDoubleNumbers:
                    request = "SELECT * FROM flats WHERE " + columnName + " BETWEEN ? AND ?";
                    preparedStatement = connection.prepareStatement(request);
                    preparedStatement.setDouble(1, doubleFrom);
                    preparedStatement.setDouble(2, doubleTo);
                    break;
                default:
                    return resultList;
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object[] data = new Object[5];
                data[0] = resultSet.getString(2);
                data[1] = resultSet.getString(3);
                data[2] = resultSet.getDouble(4);
                data[3] = resultSet.getInt(5);
                data[4] = resultSet.getFloat(6);

                formatter = new Formatter();
                String formattedData = String.valueOf(formatter.format("%-15s%-25s%-8.1f%-7d%-4.3f", data));
                resultList.add(formattedData);
                resultList.add("----------------------------------------------------------------");

            }
            resultList.remove(resultList.size() - 1);
            resultList.add("================================================================");
        } catch (SQLException e) {
            e.printStackTrace();
            return resultList;
        }
        return resultList;
    }
}
