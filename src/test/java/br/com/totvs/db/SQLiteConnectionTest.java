package br.com.totvs.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionTest {

    private static SQLiteConnectionTest instance;
    private Connection connection;

    private SQLiteConnectionTest() {
        try {

            File file = new File(getClass().getResource("/").getPath() + "dbtest.sqlite");

            if(!file.exists()){
                System.out.println("Arquivo dbtest.sqlite não encontrado");
                System.out.println("Criando arquivo dbtest.sqlite...");

                boolean fileCreated = file.createNewFile();

                if(fileCreated){
                    System.out.println("Arquivo dbtest.sqlite criado com sucesso!");
                } else {
                    System.out.println("Erro ao criar arquivo dbtest.sqlite");
                }
            }

            String url = getClass().getResource("/dbtest.sqlite").getPath();
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SQLiteConnectionTest getInstance() {
        if (instance == null) {
            instance = new SQLiteConnectionTest();
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
