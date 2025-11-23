package com.Biblioteca.dao;

import com.Biblioteca.model.Usuario;
import com.Biblioteca.util.DatabaseConnetion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {
    
    public void cadastarUsuario(Usuario usuarios) throws SQLException{
        String sql = "INSERT INTO usuario (nome, email, telefone) VALUES (?, ?, ?)";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,usuarios.getNome());
            stmt.setString(2,usuarios.getEmail());
            stmt.setString(3,usuarios.getTelefone());

            stmt.executeUpdate();
            }
    }
    public Usuario buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, id);
                ResultSet resultSet = stmt.executeQuery();

                if(resultSet.next()){
                    usuario = new Usuario(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("telefone"),
                        resultSet.getDate("data_cadastro").toLocalDate());
                }
            }
        return usuario;
    }
    public Usuario buscarPorEmail(String email) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE email = ?";
        Usuario usuario = null;

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1,email);
                ResultSet resultSet = stmt.executeQuery();

                
            }
        
    }
}
