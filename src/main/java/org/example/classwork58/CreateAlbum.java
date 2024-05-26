package org.example.classwork58;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateAlbum {
    private static final String URL = "jdbc:postgresql://localhost:5432/classwork57";
    private static final String USER = "postgres";
    private static final String PASSWORD = "220411";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Список артистов:");
            displayArtists(conn);

            System.out.println("Введите ID артиста:");
            int artistId = Integer.parseInt(scanner.nextLine());

            System.out.println("Введите название альбома:");
            String albumName = scanner.nextLine();

            addAlbum(conn, albumName, artistId);
            System.out.println("Альбом успешно добавлен.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayArtists(Connection conn) throws SQLException {
        String query = "select id, name from artist";
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("id: " + id + ", Имя: " + name);
            }
        }
    }

    private static void addAlbum(Connection conn, String name, int artistId) throws SQLException {
        String query = "insert into album (name, artist_id) values (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, artistId);
            statement.executeUpdate();
        }
    }
}

