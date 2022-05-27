package br.senai.m3s01exercicios.model;

import javax.persistence.*;

@Entity
@Table(name = "INSCRICOES")
public class Incricao {

    @Id
    @Column(name = "ID_INSCRICAO")
    private String id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Aluno aluno;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Curso curso;

    public Incricao() {}

    public Incricao(String id, Aluno aluno, Curso curso) {
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
