package com.Biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Biblioteca.model.Livro;
import com.Biblioteca.util.DatabaseConnetion;

public class LivroDao {

    //cadastrar livros
    public void cadastrarLivro(Livro livros) throws SQLException{
        String sql = "INSERT INTO livros (titulo, autor, isbn,ano_publicacao,disponivel) VALUES (?,?,?,?,?)";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1,livros.getTitulo());
                stmt.setString(2,livros.getAutor());
                stmt.setString(3,livros.getIsbn());
                stmt.setInt(4,livros.getAno_publicado());
                stmt.setBoolean(5,livros.isDisponivel());

                stmt.executeUpdate();
            }
    }

    //buscar por id
    public Livro buscarPorID(int id) throws  SQLException{
        String sql = "SELECT * FROM livros WHERE id = ?";
        Livro livro = null;

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, id);
                ResultSet resultSet = stmt.executeQuery();

                if(resultSet.next()){
                    livro = new Livro(
                        resultSet.getInt("id"), 
                        resultSet.getString("titulo"), 
                        resultSet.getString("autor"), 
                        resultSet.getString("isbn"), 
                        resultSet.getInt("ano_publicacao"), 
                        resultSet.getBoolean("disponivel"));
                }
            }
            return livro;
    }

    //listar todos os livros
    public List<Livro> listarTodos() throws  SQLException{
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros ORDER BY titulo NULLS LAST";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery(sql)){
            
                if(!resultSet.next()){
                    System.out.println("Nenhum Livro Encontrado!");
                    return livros;
                }
                do{
                    Livro livro = new Livro(
                        resultSet.getInt("id"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getString("isbn"),
                        resultSet.getInt("ano_publicacao"),
                        resultSet.getBoolean("disponivel")
                    );
                    livros.add(livro); 
                }while(resultSet.next());
            }
        return livros;    
    }

    //buscar livros por titulos
    public List<Livro> buscarPorTitulo(String titulo) throws SQLException{
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE titulo ILIKE ? ORDER BY titulo";

        try(Connection conn = DatabaseConnetion.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,"%" + titulo + "%");
            ResultSet resultSet = stmt.executeQuery();

            if(!resultSet.next()){
                    System.out.println("Nenhum Livro Encontrado!");
                    return livros;
                }
                do{
                    Livro livro = new Livro(
                        resultSet.getInt("id"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getString("isbn"),
                        resultSet.getInt("ano_publicacao"),
                        resultSet.getBoolean("disponivel")
                    );
                    livros.add(livro); 
                }while(resultSet.next());
        }
        return livros;
    }

    //buscar livros disponivel
    public List<Livro> listarLivrosDisponivel() throws SQLException{
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE disponivel = true ORDER BY titulo";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery(sql)){

                if(!resultSet.next()){
                    System.out.println("Nenhum Livro Encontrado!");
                    return livros;
                }
                do{
                    Livro livro = new Livro(
                        resultSet.getInt("id"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getString("isbn"),
                        resultSet.getInt("ano_publicacao"),
                        resultSet.getBoolean("disponivel")
                    );
                    livros.add(livro); 
                }while(resultSet.next());
            }
            return livros;
        }

    //buscar livros por autor
    public List<Livro> buscarPorAutor(String autor) throws SQLException{
            List<Livro> livros = new ArrayList<>();
            String sql = "SELECT * FROM livros WHERE autor ILIKE ? ORDER BY titulo";

            try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1, "%"+ autor + "%");
                ResultSet resultSet = stmt.executeQuery();
                
                if(!resultSet.next()){
                    System.out.println("Nenhum Livro Encontrado!");
                    return livros;
                }
                do{
                    Livro livro = new Livro(
                        resultSet.getInt("id"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autor"),
                        resultSet.getString("isbn"),
                        resultSet.getInt("ano_publicacao"),
                        resultSet.getBoolean("disponivel")
                    );
                    livros.add(livro); 
                }while(resultSet.next());
            }
            return livros;
        }

        //atualizarDisponibilidade
        public void atualizarDisponibilidade(int livroId, boolean disponivel) throws SQLException{
            String sql = "UPDATE livros SET disponivel = ? WHERE id = ?";

            try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setBoolean(1, disponivel);
                stmt.setInt(2, livroId);

                int linhasAfetadas = stmt.executeUpdate();

                if(linhasAfetadas == 0){
                    throw new SQLException("Falha ao atualizar disponibilidade - livro não encontrado");
                }
            }
        }

        //excluir livro
        public void excluirLivro(int id) throws SQLException{
            String sql = "DELETE FROM livros WHERE id = ?";

            try(Connection conn = DatabaseConnetion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){

                    stmt.setInt(1, id);

                    int linhasAfetadas = stmt.executeUpdate();

                    if(linhasAfetadas == 0){
                        throw new SQLException("Falha ao Deletar - Livro não encontardo");
                    }
                }
        }

        //verificar se livro existe
        public boolean  excluiLivro(int id) throws SQLException{
            String sql = "SELECT 1 FROM livros WHERE id = ?";

            try(Connection conn = DatabaseConnetion.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){

                    stmt.setInt(1, id);
                    ResultSet resultSet = stmt.executeQuery();

                    return resultSet.next();
                }
        }

        //contar livros 
        public int contarTotalLivro() throws SQLException{
            String sql = "SELECT COUNT(*) as total FROM livros";

            try(Connection conn = DatabaseConnetion.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(sql)){

                    if(resultSet.next()){
                        return resultSet.getInt("total");
                    }
                    return 0;
                }
        }

        //contar livros disponivel
        public int contarLivrosDisponivel() throws SQLException{
            String sql = "SELECT COUNT(*) as total FROM livros WHERE disponivel = true";

            try(Connection conn = DatabaseConnetion.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(sql)){

                    if(resultSet.next()){
                        return resultSet.getInt("total");
                    }
                    return 0;
                }
        }

}
