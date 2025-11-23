package com.Biblioteca.model;

import java.time.LocalDate;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataCadastro;

    public Usuario(int id, String nome, String email, String telefone, LocalDate dataCadastro){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }
    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
