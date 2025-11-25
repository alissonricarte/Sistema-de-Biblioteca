package com.Biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Biblioteca.model.Usuario;
import com.Biblioteca.util.DatabaseConnetion;

public class UsuarioDao {
    
    //cadastrar usuario
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

    //buscar usuario por ID
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

    //buscar usuario por EMAIL
    public Usuario buscarPorEmail(String email) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE email = ?";
        Usuario usuario = null;

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1,email);
                ResultSet resultSet = stmt.executeQuery();

                if(resultSet.next()){
                    usuario = new Usuario(resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("telefone"),
                        resultSet.getDate("data_cadastro").toLocalDate());
                }
            }
        return usuario;    
    }

    //listar todos os usuario
    public List<Usuario> listarTodos() throws SQLException{
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY nome";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()){

                if(!resultSet.next()){
                    System.out.println("Não exite usuarios");
                    return usuarios;
                }
                do { 
                    Usuario usuario = new Usuario(resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("telefone"),
                        resultSet.getDate("data_cadastro").toLocalDate());
                        usuarios.add(usuario);
                } while (resultSet.next());
            }
        return usuarios;
    }

    //atualizar usuario
    public void atualizar(Usuario usuarios) throws SQLException{
        String sql = "UPDATE usuario SET nome=?, email=?, telefone=? WHERE id=?";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                
                stmt.setString(1,usuarios.getNome());
                stmt.setString(2,usuarios.getEmail());
                stmt.setString(3,usuarios.getTelefone());
                stmt.setInt(4,usuarios.getId());

                int linhasAfetadas = stmt.executeUpdate();

                if(linhasAfetadas == 0){
                    throw new SQLException("Falha ao atualizar usuário - usuário não encontrado");
                }
            }
    }

    //excluir usuario
    public void excluir(int id) throws SQLException{
        String sql = "DELETE FROM usuario WHERE id = ?";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, id);

                int linhasAfetadas = stmt.executeUpdate();

                if(linhasAfetadas == 0){
                    throw new SQLException("Falha ao Deletar - Livro não encontardo");
                }
            }
    }

    //verificar se usuario existe 
    public boolean existeUsuario(int id)  throws SQLException{
        String sql = "SELECT 1 FROM usuario WHERE id = ?";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, id);
                ResultSet resultSet = stmt.executeQuery();

                return resultSet.next();
            }
    }
}
