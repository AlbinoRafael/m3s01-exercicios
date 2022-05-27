package br.senai.m3s01exercicios.repository;

import br.senai.m3s01exercicios.model.Aluno;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequestScoped
@Transactional
public class AlunoRepository {

    @PersistenceContext(unitName = "INSCRICAOPU")
    EntityManager em;

    public void salvar(Aluno aluno){
        em.persist(aluno);
    }

    public void deletar(Integer matricula){
        Aluno aluno = em.find(Aluno.class, matricula);
        em.remove(aluno);
    }

    public void atualizar(Aluno alterado){
        Aluno alunoBD = em.find(Aluno.class, alterado.getMatricula());
        alunoBD.setNome(alterado.getNome());
        em.merge(alunoBD);
    }

    public List<Aluno> obterTodos(){
        return em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
    }

    public Optional<Aluno> obterPorMatricula(Integer matricula){
        TypedQuery<Aluno> query = em.createQuery("SELECT a FROM Aluno a WHERE a.matricula = ?1", Aluno.class);
        try {
            Aluno aluno = query.setParameter(1, matricula).getSingleResult();
            return Optional.of(aluno);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
