package br.senai.m3s01exercicios.repository;

import br.senai.m3s01exercicios.model.Inscricao;

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
public class InscricaoRepository {

    @PersistenceContext(unitName = "INSCRICAOPU")
    EntityManager em;

    public void salvar(Inscricao inscricao){
        em.persist(inscricao);
    }

    public List<Inscricao> obterTodos(){
        return em.createQuery("SELECT i FROM Inscricao i", Inscricao.class).getResultList();
    }

    public Optional<Inscricao> obter(Integer id){
        TypedQuery<Inscricao> query = em.createQuery("SELECT i FROM Inscricao i WHERE i.id = ?1", Inscricao.class);
        try {
            Inscricao inscricao = query.setParameter(1, id).getSingleResult();
            return Optional.of(inscricao);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void deletar(Integer id){
        Inscricao inscricao = em.find(Inscricao.class, id);
        em.remove(inscricao);
    }
}
