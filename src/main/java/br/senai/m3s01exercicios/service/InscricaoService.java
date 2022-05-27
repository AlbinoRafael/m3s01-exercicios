package br.senai.m3s01exercicios.service;

import br.senai.m3s01exercicios.exception.RegistroExistenteException;
import br.senai.m3s01exercicios.exception.RegistroNaoEncontradoException;
import br.senai.m3s01exercicios.model.Inscricao;
import br.senai.m3s01exercicios.repository.InscricaoRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class InscricaoService {

    @Inject
    private InscricaoRepository inscricaoRepository;


    public Inscricao inserir(Inscricao inscricao) {
        verificarSeExisteInscricaoPorId(inscricao);
        inscricaoRepository.salvar(inscricao);
        return inscricao;
    }

    public void excluir(Integer id) {
        obterInscricaoPorId(id);
        inscricaoRepository.deletar(id);
    }

    public List<Inscricao> obter() {
        return inscricaoRepository.obterTodos();
    }

    public Inscricao obterInscricaoPorId(Integer id) {
        Optional<Inscricao> inscricaoOpt = inscricaoRepository.obter(id);
        return inscricaoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Aluno", id.toString()));
    }
    private void verificarSeExisteInscricaoPorId(Inscricao inscricao) {
        Optional<Inscricao> inscricaoOpt = inscricaoRepository.obter(inscricao.getId());
        if (inscricaoOpt.isPresent())
            throw new RegistroExistenteException("Inscricao", inscricao.getId().toString());
    }
}
