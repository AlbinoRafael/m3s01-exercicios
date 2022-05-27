package br.senai.m3s01exercicios.service;

import br.senai.m3s01exercicios.exception.RegistroExistenteException;
import br.senai.m3s01exercicios.exception.RegistroNaoEncontradoException;
import br.senai.m3s01exercicios.model.Aluno;
import br.senai.m3s01exercicios.repository.AlunoRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequestScoped
public class AlunoService {

    @Inject
    private AlunoRepository alunoRepository;

    public Aluno inserir(Aluno aluno) {
        verificarSeExisteAlunoPorMatricula(aluno);
        alunoRepository.salvar(aluno);
        return aluno;
    }

    public Aluno alterar(Aluno aluno) {
        obterAlunoPorMatricula(aluno.getMatricula());
        verificarSeExisteAlunoPorMatricula(aluno);
        alunoRepository.atualizar(aluno);
        return aluno;
    }

    public void excluir(Integer codigo) {
        obterAlunoPorMatricula(codigo);
        alunoRepository.deletar(codigo);
    }

    public List<Aluno> obter(String nomePesquisa) {
        List<Aluno> alunos = alunoRepository.obterTodos();
        if(nomePesquisa!=null){
            alunos = alunos.stream().filter(a -> a.getNome().contains(nomePesquisa)).collect(toList());
        }
        return alunos;
    }

    public Aluno obterAlunoPorMatricula(Integer matricula) {
        Optional<Aluno> alunoOpt = alunoRepository.obterPorMatricula(matricula);
        return alunoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Aluno", matricula.toString()));
    }
    private void verificarSeExisteAlunoPorMatricula(Aluno aluno) {
        Optional<Aluno> alunoOpt = alunoRepository.obterPorMatricula(aluno.getMatricula());
        if (alunoOpt.isPresent())
            throw new RegistroExistenteException("Aluno", aluno.getMatricula().toString());
    }
}
