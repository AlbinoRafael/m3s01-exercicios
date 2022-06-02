package br.senai.m3s01exercicios.service;

import br.senai.m3s01exercicios.exception.RegistroExistenteException;
import br.senai.m3s01exercicios.exception.RegistroNaoEncontradoException;
import br.senai.m3s01exercicios.model.Aluno;
import br.senai.m3s01exercicios.repository.AlunoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {


    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @Test
    @DisplayName("Quando matricula ja existe, Deve lançar exceção")
    void inserirAluno_falha() {
        Aluno aluno = new Aluno(1000, "Aluno1");
        when(alunoRepository.obterPorMatricula(anyInt())).thenReturn(Optional.of(aluno));
        assertThrows(RegistroExistenteException.class, () -> alunoService.inserir(aluno));
    }


    @Test
    @DisplayName("Quando dados válidos de aluno, Deve gravar e retornar aluno")
    void inserirAluno_sucesso() {
        Aluno aluno = new Aluno(1000, "Aluno1");
        when(alunoRepository.obterPorMatricula(anyInt())).thenReturn(Optional.empty());
        Aluno result = alunoService.inserir(aluno);

        assertNotNull(result);
        assertInstanceOf(Aluno.class, result);
        assertNotNull(result.getMatricula());
        assertNotNull(result.getNome());

    }

    @Test
    @DisplayName("Quando aluno não for encontrado, não altera e Deve lançar exceção")
    void alterarAluno_falha() {
        Aluno aluno = new Aluno(1000, "Aluno1");
        when(alunoRepository.obterPorMatricula(anyInt())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class, () -> alunoService.alterar(aluno));
    }

    @Test
    @DisplayName("Quando aluno encontrado, nao deve retornar aluno nulo")
    void alterarAluno_sucesso() {
        Aluno aluno = new Aluno(1000, "Aluno1");
        when(alunoRepository.obterPorMatricula(anyInt())).thenReturn(Optional.of(aluno));
        Aluno result = alunoService.alterar(aluno);

        assertNotNull(result);
        assertInstanceOf(Aluno.class, result);
        assertNotNull(result.getMatricula());
        assertNotNull(result.getNome());
    }

    @Test
    @DisplayName("Quando não excluir, Deve lançar exceção")
    void excluirAluno_falha() {
        when(alunoRepository.obterPorMatricula(anyInt())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class,() -> alunoService.excluir(anyInt()));
    }

    @Test
    @DisplayName("Quando excluir com sucesso, não deve lançar exceção")
    void excluirAluno_sucesso() {
        when(alunoRepository.obterPorMatricula(anyInt())).thenReturn(Optional.of(new Aluno()));
        assertDoesNotThrow(() -> alunoService.excluir(anyInt()));
    }

    @Test
    @DisplayName("Quando aluno nao existir, Deve lançar exceção")
    void obterAlunoPorMatricula_falha() {
        when(alunoRepository.obterPorMatricula(anyInt())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class, () -> alunoService.obterAlunoPorMatricula(anyInt()));
    }
    
    @Test
    @DisplayName("Quando aluno existir, Deve retornar aluno instanciado")
    void obterAlunoPorMatricula_sucesso() {
        Aluno aluno = new Aluno(1000, "Aluno1");
        when(alunoRepository.obterPorMatricula(anyInt())).thenReturn(Optional.of(aluno));

        Aluno result = alunoService.obterAlunoPorMatricula(anyInt());
        assertNotNull(result);
        assertInstanceOf(Aluno.class, result);
    }
}