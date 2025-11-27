package com.Biblioteca.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Biblioteca.model.Emprestimo;
import com.Biblioteca.util.DatabaseConnetion;

public class EmprestimoDao {
    
    //função aux 
    private Emprestimo criarEmprestimoFromResultSet(ResultSet resultSet) throws SQLException{
        java.sql.Date dataDevolucaoReal = resultSet.getDate("data_devolucao_real");
        LocalDate dataReal = (dataDevolucaoReal != null) ? dataDevolucaoReal.toLocalDate() : null;

        return new Emprestimo(
            resultSet.getInt("id"), 
            resultSet.getInt("livro_id"), 
            resultSet.getInt("usuario_id"), 
            resultSet.getDate("data_emprestimo").toLocalDate(), 
            resultSet.getDate("data_devolucao_prevista").toLocalDate(), 
            dataReal, 
            resultSet.getString("status"));
    }

    //fazer emprestimo 
    public void realizarEmpretimo(Emprestimo emprestimos) throws SQLException{
        String sql = "INSERT INTO emprestimo (livro_id, usuario_id, data_devolucao_prevista) VALUES (?, ?, ?)";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1,emprestimos.getLivro_id());
                stmt.setInt(2,emprestimos.getUsuario_id());
                stmt.setDate(3,Date.valueOf(emprestimos.getData_devolucao_prevista()));

                stmt.executeUpdate();
            }
    }

    //buscar emprestimo por id
    public Emprestimo buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM emprestimo WHERE id = ?";
        Emprestimo emprestimo = null;

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, id);
                ResultSet resultSet = stmt.executeQuery();

                if(resultSet.next()){
                    Emprestimo empretimo = criarEmprestimoFromResultSet(resultSet);
                }
            }
        return emprestimo;    
    }

    //listar emprestimo ativo 
    public List<Emprestimo> listarEmpretimoAtivo() throws SQLException{
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE status = 'ATIVO' ORDER BY data_emprestimo DESC";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery(sql)){

                while(resultSet.next()){
                    Emprestimo emprestimo = criarEmprestimoFromResultSet(resultSet);
                    emprestimos.add(emprestimo);
                }
            }
        return emprestimos;    
    }

    //buscar emprestio por usuario_id
    public List<Emprestimo> listarPorUsuario (int usuario_id) throws SQLException{
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WRERE usuario_id = ? ORDER BY data_emprestimo DESC";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, usuario_id);
                ResultSet resultSet = stmt.executeQuery();

                while(resultSet.next()){
                    Emprestimo emprestimo = criarEmprestimoFromResultSet(resultSet);
                    emprestimos.add(emprestimo);
                }
            }
        return emprestimos;    
    }

    // verificar se livro está emprestado
    public boolean livroEmprestado(int livro_id) throws SQLException{
        String sql = "SELECT 1 FROM emprestimo WHERE livro_id = ? AND status = 'ATIVO'";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, livro_id);
                ResultSet resultSet = stmt.executeQuery();
                
                return resultSet.next();
            }
    }

    //Verificar empréstimos ativos de um usuário
    public List<Emprestimo> listarAtivosPorUsuario(int usuario_id) throws SQLException{
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ? AND  status = 'ATIVO' ORDER BY data_emprestimo DESC";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, usuario_id);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    Emprestimo emprestimo = criarEmprestimoFromResultSet(resultSet);
                    emprestimos.add(emprestimo);
                }
            }
        return emprestimos;    
    }

    //registrar devolução
    public void resgistarDevolucao(int id) throws SQLException{
        String sql = "UPDATE emprestimo SET data_devolucao_real = CURRENT_DATE , status = 'FINALIZADO' WHERE id = ?";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, id);

                int linhasAfetadas = stmt.executeUpdate();

                if(linhasAfetadas == 0){
                    throw new SQLException("Falha ao registrar devolução - empréstimo não encontrado");
                }
            }
    }


    //registrar devolução com data específica
    public void resgistarDevolucao(int id, LocalDate data_devolucao) throws SQLException{
        String sql = "UPDATE emprestimo SET data_devolucao_real = ? , status = 'FINALIZADO' WHERE id = ?";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setDate(1, Date.valueOf(data_devolucao));
                stmt.setInt(2, id);

                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    throw new SQLException("Falha ao registrar devolução - empréstimo não encontrado"); 
                }
            }
    }

    //listar empréstimos atrasados
    public List<Emprestimo> listarEmprestimoAtrasado () throws SQLException{
        List<Emprestimo>  emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE status = 'ATIVO' AND data_devolucao_prevista < CURRENT_DATE ORDER BY data_devolucao_prevista ASC";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()){

                while(resultSet.next()){
                    Emprestimo emprestimo = criarEmprestimoFromResultSet(resultSet);
                    emprestimos.add(emprestimo);
                }
            }
        return emprestimos;    
    }

    //histórico completo de empréstimos
    public List<Emprestimo> listarHistorico () throws SQLException{
        List<Emprestimo>  emprestimos = new ArrayList<>();
        String slq = "SELECT * FROM emprestimo ORDER BY data_emprestimo DESC";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(slq);
            ResultSet resultSet = stmt.executeQuery()){

                while(resultSet.next()){
                    Emprestimo emprestimo = criarEmprestimoFromResultSet(resultSet);
                    emprestimos.add(emprestimo);
                }
            }
        return emprestimos;
        
    }

    //contar empréstimos ativos
    public int contarEmprestimoAtivo() throws SQLException{
        String sql = "SELECT COUNT(*) as total FROM emprestimo WHERE status = 'ATIVO'";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()){

                if(resultSet.next()){
                    return resultSet.getInt("total");
                }
            }
        return 0;    
    }

    //contar empréstimos atrasados
    public int contarEmprestimoAtrasdo () throws SQLException{
        String sql = "SELECT COUNT(*) as total FROM emprestimo WHERE status = 'ATIVO' AND data_devolucao_prevista < CURRENT_DATE";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()){

                if(resultSet.next()){
                    return resultSet.getInt("total");
                }
            }
        return 0;    
    }

    //verificar se empréstimo existe
    public boolean verificarSeEmprestimoExiste (int id) throws SQLException{
        String sql = "SELECT 1 FROM emprestimo WHERE id = ?";

        try(Connection conn = DatabaseConnetion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setInt(1, id);
                ResultSet resultSet = stmt.executeQuery();

                return resultSet.next();
            }
    }
}
