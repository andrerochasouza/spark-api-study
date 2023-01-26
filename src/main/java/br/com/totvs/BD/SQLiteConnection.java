package br.com.totvs.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

    private static SQLiteConnection instance;
    private Connection connection;

    private SQLiteConnection() {
        try {
            // criar url do banco de dados db.sqlite dentro do resources
            String url = getClass().getResource("/db.sqlite").getPath();
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLiteConnection getInstance() {
        if (instance == null) {
            instance = new SQLiteConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void testConnection() {
        try {
            if (connection != null) {
                System.out.println("Conexão com o banco de dados estabelecida.");
            } else {
                System.out.println("Conexão com o banco de dados não estabelecida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
