package Repository;

import br.senai.m3s01exercicios.model.Curso;

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
public class CursoRepository {

    @PersistenceContext(unitName = "INSCRICAOPU")
    EntityManager em;

    public void salvar(Curso curso){
        em.persist(curso);
    }

    public List<Curso> obterTodos(){
        List<Curso> cursos = em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
        return cursos;
    }

    public Optional<Curso> obterPorCodigo(String codigo){
        TypedQuery<Curso> query = em.createQuery("SELECT c FROM Curso C WHERE c.codigo = ?1", Curso.class);
        try {
            Curso curso = query.setParameter(1, codigo).getSingleResult();
            return Optional.of(curso);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void deletar(String codigo){
        Curso curso = em.find(Curso.class, codigo);
        em.remove(curso);
    }

    public void atualizar(Curso alterado){
        Curso cursoBD = em.find(Curso.class, alterado.getCodigo());
        cursoBD.setAssunto(alterado.getAssunto());
        cursoBD.setDuracao(alterado.getDuracao());
        em.merge(cursoBD);
    }
}
