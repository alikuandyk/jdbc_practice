package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCExample {
    public static void main(String[] args) throws SQLException {
//        пример для метода getById
        Cat catById = getById(1);
        System.out.println(catById);

//        пример для метода getAll
        List<Cat> allCats = getAll();
        for (Cat cat : allCats) {
            System.out.println(cat);
        }

//        пример для метода create
        Cat newCat = new Cat(0, "Васька", "черный");
        create(newCat);

//        пример для метода update
        newCat.setColor("белый");
        update(newCat);

//        пример для метода delete
        delete(1);
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/homework57";
        String user = "postgres";
        String password = "220411";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("");
        }
    }

    private static Cat getById(int id) {
        String sql = "select * from cat where id = ?";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String color = rs.getString("color");

                return new Cat(id, name, color);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Cat> getAll() {
        String sql = "select * from cat";
        List<Cat> cats = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String color = resultSet.getString("color");
                cats.add(new Cat(id, name, color));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cats;
    }

//    использовал гпт, че за executeUpdate
    private static void create(Cat cat) {
        String sql = "insert into cat (name, color) values (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, cat.getName());
            statement.setString(2, cat.getColor());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void update(Cat cat) {
        String sql = "update cat set name = ?, color = ? where id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cat.getName());
            statement.setString(2, cat.getColor());
            statement.setInt(3, cat.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void delete(int id) {
        String sql = "delete from cat where id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}