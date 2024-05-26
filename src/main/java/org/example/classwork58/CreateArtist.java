package org.example.classwork58;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateArtist {
    private static final String URL = "jdbc:postgresql://localhost:5432/classwork57";
    private static final String USER = "postgres";
    private static final String PASSWORD = "220411";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя артиста:");
        String artistName = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (artistExists(conn, artistName)) {
                System.out.println("Артист с таким именем уже существует.");
            } else {
                addArtist(conn, artistName);
                System.out.println("Артист успешно добавлен.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean artistExists(Connection conn, String name) throws SQLException {
        String query = "select count(*) from artist where name = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private static void addArtist(Connection conn, String name) throws SQLException {
        String query = "insert into artist (name) values (?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }
}

