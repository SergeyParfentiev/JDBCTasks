package tasksJDBC.task2.dbManagers.workWithData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Goods {

    public Goods() {
    }

    public void add(Connection connection, String name, int count, int cost) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO goods (name, count, cost) " +
                "VALUES(?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, count);
            preparedStatement.setInt(3, cost);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDuplicateName(Connection connection, String name) {
        boolean result;
        try (CallableStatement callableStatement = connection.prepareCall("{call checkGoodsName(?,?)}")) {

            callableStatement.setString(1, name);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.execute();

            result = callableStatement.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    public List<String> getAllGoods(Connection connection) {
        List<String> allGoods = new ArrayList<>();

        Formatter formatter = new Formatter();
        Object[] types = {"Name", "Count", "Cost"};
        String formattedTypes = formatter.format("%-15s%-10s%-8s",
                types).toString();
        allGoods.add("==============================");
        allGoods.add(formattedTypes);
        allGoods.add("==============================");

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM goods")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Object[] data = new Object[3];
                data[0] = resultSet.getString(2);
                data[1] = resultSet.getInt(3);
                data[2] = resultSet.getInt(4);
                formatter = new Formatter();
                String formattedData = String.valueOf(formatter.format("%-15s%-10d%-8d", data));
                allGoods.add(formattedData);
                allGoods.add("------------------------------");

            }
            allGoods.remove(allGoods.size() - 1);
            allGoods.add("==============================");
        } catch (SQLException e) {
            e.printStackTrace();
            return allGoods;
        }
        return allGoods;
    }

    public List<Integer> getIdAndCostByName(Connection connection, String name) {
        List<Integer> idAndCost = new ArrayList<>();
        String getId = "SELECT id, cost FROM goods WHERE name = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(getId)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                idAndCost.add(resultSet.getInt("id"));
                idAndCost.add(resultSet.getInt("cost"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return idAndCost;
    }

    public boolean isCountMoreThenBy(Connection connection, int id, int sold) {
        boolean result;
        try (CallableStatement callableStatement = connection.prepareCall("{call checkGoodsCount(?,?,?)}")) {
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, sold);
            callableStatement.registerOutParameter(3, Types.BOOLEAN);
            callableStatement.execute();

            result = callableStatement.getBoolean(3);
        } catch (SQLException e) {
            result = false;
        }
        return result;
    }
}
