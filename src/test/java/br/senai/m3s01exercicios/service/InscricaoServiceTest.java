package br.senai.m3s01exercicios.service;

import br.senai.m3s01exercicios.exception.RegistroExistenteException;
import br.senai.m3s01exercicios.exception.RegistroNaoEncontradoException;
import br.senai.m3s01exercicios.model.Aluno;
import br.senai.m3s01exercicios.model.Curso;
import br.senai.m3s01exercicios.model.Inscricao;
import br.senai.m3s01exercicios.repository.InscricaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

    @Mock
    private InscricaoRepository inscricaoRepository;

    @InjectMocks
    private InscricaoService inscricaoService;

    @Test
    @DisplayName("Quando matricula ja existe, Deve lançar exceção")
    void inserirInscricao_falha() {
        Aluno aluno = new Aluno(1000, "Aluno1");
        Curso curso = new Curso("codigo", "assunto", 30);
        Inscricao inscricao = new Inscricao(1000,aluno,curso);
        when(inscricaoRepository.obter(anyInt())).thenReturn(Optional.of(inscricao));
        assertThrows(RegistroExistenteException.class, () -> inscricaoService.inserir(inscricao));
    }

    @Test
    @DisplayName("Quando dados válidos de aluno e curso, Deve gravar e retornar inscricao")
    void inserirInscricao_sucesso() {
        Aluno aluno = new Aluno(1000, "Aluno1");
        Curso curso = new Curso("codigo", "assunto", 30);
        Inscricao inscricao = new Inscricao(1000,aluno,curso);
        when(inscricaoRepository.obter(anyInt())).thenReturn(Optional.empty());
        Inscricao result = inscricaoService.inserir(inscricao);

        assertNotNull(result);
        assertInstanceOf(Inscricao.class, result);
        assertNotNull(result.getAluno());
        assertNotNull(result.getCurso());

    }

    @Test
    @DisplayName("Quando não excluir, Deve lançar exceção")
    void excluirInscricao_falha() {
        when(inscricaoRepository.obter(anyInt())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class,() -> inscricaoService.excluir(anyInt()));
    }

    @Test
    @DisplayName("Quando excluir com sucesso, não deve lançar exceção")
    void excluirInscricao_sucesso() {
        when(inscricaoRepository.obter(anyInt())).thenReturn(Optional.of(new Inscricao()));
        assertDoesNotThrow(() -> inscricaoService.excluir(anyInt()));
    }


    @Test
    @DisplayName("Quando inscricao nao existir, Deve lançar exceção")
    void obterInscricaoPorId_falha() {
        when(inscricaoRepository.obter(anyInt())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class, () -> inscricaoService.obterInscricaoPorId(anyInt()));
    }

    @Test
    @DisplayName("Quando inscricao existir, Deve retornar inscricao instanciado")
    void obterInscricaoPorId_sucesso() {
        Aluno aluno = new Aluno(1000, "Aluno1");
        Curso curso = new Curso("codigo", "assunto", 30);
        Inscricao inscricao = new Inscricao(1000,aluno,curso);

        when(inscricaoRepository.obter(anyInt())).thenReturn(Optional.of(inscricao));

        Inscricao result = inscricaoService.obterInscricaoPorId(anyInt());
        assertNotNull(result);
        assertInstanceOf(Inscricao.class, result);
    }
}