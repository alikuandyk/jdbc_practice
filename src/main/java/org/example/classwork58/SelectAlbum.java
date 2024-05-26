package org.example.classwork58;

import org.example.classwork58.pojo.Album;
import org.example.classwork58.pojo.Artist;
import org.example.classwork58.pojo.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectAlbum {
    private static final String URL = "jdbc:postgresql://localhost:5432/classwork57";
    private static final String USER = "postgres";
    private static final String PASSWORD = "220411";

    public static void main(String[] args) {

    }

    public Artist findArtistById(int id) {
        String query = "select id, name from artist where id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int artistId = rs.getInt("id");
                    String name = rs.getString("name");

                    return new Artist(artistId, name);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Album findAlbumById(int id) {
        String query = "select id, name, artist_id from album where id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int albumId = rs.getInt("id");
                    String name = rs.getString("name");
                    int artistId = rs.getInt("artist_id");

                    Artist artist = findArtistById(artistId);
                    return new Album(albumId, name, artist);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Artist findMostActiveArtist() {
        String query = "SELECT artist.id, artist.name, COUNT(album.id) AS album_count\n" +
                "FROM artist\n" +
                "JOIN album ON artist.id = album.artist_id\n" +
                "GROUP BY artist.id, artist.name\n" +
                "ORDER BY album_count DESC\n" +
                "LIMIT 1;\n";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery())
        {
            if (rs.next()) {
                int artistId = rs.getInt("id");
                String name = rs.getString("name");

                return new Artist(artistId, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Artist> findArtistsWithoutAlbums() {
        String query = "select artist.id, artist.name\n" +
                "from artist\n" +
                "left join album on artist.id = album.artist_id\n" +
                "where album.id is null";

        List<Artist> artists = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery())
        {
            while (rs.next()) {
                int artistId = rs.getInt("id");
                String name = rs.getString("name");

                artists.add(new Artist(artistId, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artists;
    }

    public List<Song> findSongsByArtistId(int artistId) {
        String query = "select song.id, song.name, song.duration, song.album_id\n" +
                "from song\n" +
                "join album on song.album_id = album.id\n" +
                "where album.artist_id = ?";

        List<Song> songs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setInt(1, artistId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int songId = rs.getInt("id");
                    String name = rs.getString("name");
                    int duration = rs.getInt("duration");
                    int albumId = rs.getInt("album_id");

                    Album album = findAlbumById(albumId);
                    songs.add(new Song(songId, name, duration, album));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return songs;
    }
}
