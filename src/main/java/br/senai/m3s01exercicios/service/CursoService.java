package br.senai.m3s01exercicios.service;

import br.senai.m3s01exercicios.exception.RegistroExistenteException;
import br.senai.m3s01exercicios.exception.RegistroNaoEncontradoException;
import br.senai.m3s01exercicios.model.Curso;
import br.senai.m3s01exercicios.repository.CursoRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class CursoService {

    @Inject
    private CursoRepository cursoRepository;

    public Curso inserir(Curso curso) {
        verificarSeExisteCursoPorCodigo(curso);
        cursoRepository.salvar(curso);
        return curso;
    }

    public Curso alterar(Curso curso) {
        obterCursoPorCodigo(curso.getCodigo());
        verificarSeExisteCursoPorCodigo(curso);
        cursoRepository.atualizar(curso);
        return curso;
    }

    public void excluir(String codigo) {
        obterCursoPorCodigo(codigo);
        cursoRepository.deletar(codigo);
    }

    public List<Curso> obter(String sort, Integer limit) {
        List<Curso> cursos = cursoRepository.obterTodos();
        ordenarResultadoPor(sort,cursos);
        cursos = cursos.subList(0, limit);
        return cursos;
    }

    public Curso obter(String codigo) {
        Optional<Curso> cursoOpt = cursoRepository.obterPorCodigo(codigo);
        return cursoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Curso", codigo));
    }

    public Curso obterCursoPorCodigo(String codigo) {
        Optional<Curso> cursoOpt = cursoRepository.obterPorCodigo(codigo);
        return cursoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Curso", codigo));
    }
    private void verificarSeExisteCursoPorCodigo(Curso curso) {
        Optional<Curso> cursoOpt = cursoRepository.obterPorCodigo(curso.getCodigo());
        if (cursoOpt.isPresent())
            throw new RegistroExistenteException("Curso", curso.getCodigo());
    }
    private void ordenarResultadoPor(String ordenadoPor, List<Curso> cursos) {
        if (ordenadoPor.equals("assunto"))
            Collections.sort(cursos, Comparator.comparing(Curso::getAssunto));
        else if (ordenadoPor.equals("duracao"))
            Collections.sort(cursos, Comparator.comparing(Curso::getDuracao));
    }
}
