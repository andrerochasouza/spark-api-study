package br.com.totvs.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

    private static SQLiteConnection instance;
    private Connection connection;

    private SQLiteConnection() {
        try {

            File file = new File(getClass().getResource("/").getPath() + "db.sqlite");

            if(!file.exists()){
                System.out.println("Arquivo db.sqlite não encontrado");
                System.out.println("Criando arquivo db.sqlite...");

                boolean fileCreated = file.createNewFile();

                if(fileCreated){
                    System.out.println("Arquivo db.sqlite criado com sucesso!");
                } else {
                    System.out.println("Erro ao criar arquivo db.sqlite");
                }
            }

            String url = getClass().getResource("/db.sqlite").getPath();
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);

        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public boolean testConnection() {
        if (connection != null) {
            System.out.println("Conexão com o banco de dados estabelecida.");
            return true;
        } else {
            System.out.println("Conexão com o banco de dados não estabelecida.");
            return false;
        }
    }


}
