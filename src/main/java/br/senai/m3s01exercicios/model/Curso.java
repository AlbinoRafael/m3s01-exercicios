package br.senai.m3s01exercicios.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CURSOS")
public class Curso {


    @Id
    @Column(name = "CODIGO_CURSO")
    private String codigo;

    private String assunto;

    private Integer duracao; //em dias

    public Curso() {
    }

    public Curso(String codigo, String assunto, Integer duracao) {
        this.codigo = codigo;
        this.assunto = assunto;
        this.duracao = duracao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
}
