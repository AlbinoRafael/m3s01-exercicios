package br.senai.m3s01exercicios.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CursoDTO implements Serializable {

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String assunto;

    private Integer duracao; //em dias

    public CursoDTO() {
    }

    public CursoDTO(String codigo, String assunto, Integer duracao) {
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
