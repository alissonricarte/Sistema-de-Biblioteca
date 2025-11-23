package com.Biblioteca.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnetion {
    private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USER = "postgres";
    private static final String PASSWORD = "alisson";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
    /*public static void testaConexao(){
        try(Connection connection = getConnection()){
            if(connection != null){
                System.out.println("ok!");
            }else{
                System.out.println("erro");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }    
    }
    
    public static void main(String[] args) {
        testaConexao();
    }*/
}
