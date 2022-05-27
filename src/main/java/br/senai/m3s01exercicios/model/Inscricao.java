package br.senai.m3s01exercicios.model;

import javax.persistence.*;

@Entity
@Table(name = "INSCRICOES")
public class Inscricao {

    @Id
    @Column(name = "ID_INSCRICAO")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATRICULA_ALUNO", referencedColumnName = "MATRICULA_ALUNO")
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CODIGO_CURSO", referencedColumnName = "CODIGO_CURSO")
    private Curso curso;

    public Inscricao() {}

    public Inscricao(String id, Aluno aluno, Curso curso) {
        this.id = id;
        this.aluno = aluno;
        this.curso = curso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
