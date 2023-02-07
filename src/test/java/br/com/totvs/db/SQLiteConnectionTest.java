package br.com.totvs.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionTest {

    private Connection connection;

    public SQLiteConnectionTest() {
        try {

            File file = new File(getClass().getResource("/").getPath() + "dbtest.sqlite");

            if(!file.exists()){
                boolean fileCreated = file.createNewFile();
            }

            String url = getClass().getResource("/dbtest.sqlite").getPath();
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnectionAndDeleteFile() throws SQLException {
        if(connection != null){
            connection.close();
        }

        File file = new File(getClass().getResource("/").getPath() + "dbtest.sqlite");

        if(file.exists()){
            file.delete();
        }
    }

    public boolean testConnection() {
        if (connection != null) {
            return true;
        } else {
            return false;
        }
    }

}
