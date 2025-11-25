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
}
