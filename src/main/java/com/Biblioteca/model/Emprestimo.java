package com.Biblioteca.model;

import java.time.LocalDate;

public class Emprestimo {
    
    private int id;
    private int livro_id;
    private int usuario_id;
    private LocalDate data_emprestimo;
    private LocalDate data_devolucao_prevista;
    private LocalDate data_devolucao_real;
    private String status;

    public Emprestimo(int id, int livro_id, int usuario_id, LocalDate data_emprestimo, LocalDate data_devolucao_prevista, LocalDate data_devolucao_real, String status) {
        this.id = id;
        this.livro_id = livro_id;
        this.usuario_id = usuario_id;
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao_prevista = data_devolucao_prevista;
        this.data_devolucao_real = data_devolucao_real;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public int getLivro_id() {
        return livro_id;
    }
    public int getUsuario_id() {
        return usuario_id;
    }
    public LocalDate getData_emprestimo() {
        return data_emprestimo;
    }
    public LocalDate getData_devolucao_prevista() {
        return data_devolucao_prevista;
    }
    public LocalDate getData_devolucao_real() {
        return data_devolucao_real;
    }
    public String getStatus() {
        return status;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setLivro_id(int livro_id) {
        this.livro_id = livro_id;
    }
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    public void setData_emprestimo(LocalDate data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }
    public void setData_devolucao_prevista(LocalDate data_devolucao_prevista) {
        this.data_devolucao_prevista = data_devolucao_prevista;
    }
    public void setData_devolucao_real(LocalDate data_devolucao_real) {
        this.data_devolucao_real = data_devolucao_real;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
