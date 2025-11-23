package com.Biblioteca.model;

public class Livro{

    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private int ano_publicado;
    private boolean disponivel;

    public Livro(int id, String titulo, String autor, String isbn, int ano_publicado, boolean disponivel){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.ano_publicado = ano_publicado;
        this.disponivel = disponivel;
    }
    
    public int getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getAutor() {
        return autor;
    }
    public String getIsbn() {
        return isbn;
    }
    public int getAno_publicado() {
        return ano_publicado;
    }
    public boolean isDisponivel() {
        return disponivel;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public void setIsb(String isbn) {
        this.isbn = isbn;
    }
    public void setAno_publicado(int ano_publicado) {
        this.ano_publicado = ano_publicado;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
