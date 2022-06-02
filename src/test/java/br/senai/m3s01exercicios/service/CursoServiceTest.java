package br.senai.m3s01exercicios.service;

import br.senai.m3s01exercicios.exception.RegistroExistenteException;
import br.senai.m3s01exercicios.exception.RegistroNaoEncontradoException;
import br.senai.m3s01exercicios.model.Curso;
import br.senai.m3s01exercicios.repository.CursoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;



    @Test
    @DisplayName("Quando codigo já existe, Deve lançar exceção")
    void inserirCurso_falhaCodigo(){
        Curso curso = new Curso("codigo", "assunto", 30);
        when(cursoRepository.obterPorCodigo(anyString())).thenReturn(Optional.of(curso));
        assertThrows(RegistroExistenteException.class, () -> cursoService.inserir(curso));
    }

    @Test
    @DisplayName("Quando dados válidos de curso, Deve gravar e retornar Curso")
    void inserirCurso_sucesso() {

        Curso curso = new Curso("codigo", "assunto", 30);
        when(cursoRepository.obterPorCodigo(anyString())).thenReturn(Optional.empty());
        Curso result = cursoService.inserir(curso);

        assertNotNull(result);
        assertInstanceOf(Curso.class, result);
        assertNotNull(result.getCodigo());
        assertNotNull(result.getAssunto());
        assertNotNull(result.getDuracao());
    }
    @Test
    @DisplayName("Quando curso não for encontrado, não altera e Deve lançar exceção")
    void alterarCurso_falha() {
        when(cursoRepository.obterPorCodigo(anyString())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class, () -> cursoService.obterCursoPorCodigo(anyString()));
    }


    @Test
    @DisplayName("Quando curso encontrado, nao deve retornar curso nulo")
    void alterarCurso_sucesso() {
        Curso curso = new Curso("codigo", "assunto", 30);
        when(cursoRepository.obterPorCodigo(anyString())).thenReturn(Optional.of(curso));
        Curso result = cursoService.alterar(curso);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Quando não excluir, Deve lançar exceção")
    void excluirCurso_falha() {
        when(cursoRepository.obterPorCodigo(anyString())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class,() -> cursoService.excluir(anyString()));
    }

    @Test

    @DisplayName("Quando excluir com sucesso, não deve lançar exceção")
    void excluirCurso_sucesso() {
        when(cursoRepository.obterPorCodigo(anyString())).thenReturn(Optional.of(new Curso()));
        assertDoesNotThrow(() -> cursoService.excluir("codigo"));
    }

    @Test
    @DisplayName("Quando curso nao existir, Deve lançar exceção")
    void obterCursoPorCodigo_falha() {
        when(cursoRepository.obterPorCodigo(anyString())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class, () -> cursoService.obterCursoPorCodigo(anyString()));
    }

    @Test
    @DisplayName("Quando video existir, Deve retornar curso instanciado")
    void obterCursoPorCodigo_sucesso(){
        Curso curso = new Curso("codigo", "assunto", 30);
        when(cursoRepository.obterPorCodigo(anyString())).thenReturn(Optional.of(curso));

        Curso result = cursoService.obterCursoPorCodigo(anyString());
        assertNotNull(result);
        assertInstanceOf(Curso.class, result);
    }
}