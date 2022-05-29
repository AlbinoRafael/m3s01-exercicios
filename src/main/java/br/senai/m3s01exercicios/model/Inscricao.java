package br.senai.m3s01exercicios.model;

import javax.persistence.*;

@Entity
@Table(name = "INSCRICOES")
public class Inscricao {

    @Id
    @Column(name = "ID_INSCRICAO")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATRICULA_ALUNO", referencedColumnName = "MATRICULA_ALUNO")
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CODIGO_CURSO", referencedColumnName = "CODIGO_CURSO")
    private Curso curso;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
