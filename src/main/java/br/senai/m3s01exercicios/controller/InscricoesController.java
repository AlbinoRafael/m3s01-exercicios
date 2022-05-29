package br.senai.m3s01exercicios.controller;

import br.senai.m3s01exercicios.dto.InscricaoReqDTO;
import br.senai.m3s01exercicios.dto.InscricaoRespDTO;
import br.senai.m3s01exercicios.mapper.InscricaoMapper;
import br.senai.m3s01exercicios.model.Inscricao;
import br.senai.m3s01exercicios.service.InscricaoService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/inscricoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InscricoesController {

    @Inject
    private InscricaoService inscricaoService;

    @POST
    public Response inserir(@Valid InscricaoReqDTO inscricaoRequest) {
        Inscricao inscricao = InscricaoMapper.INSTANCE.toModel(inscricaoRequest);
        if(inscricao!=null) {
            return Response.status(409).entity("Inscrição já existe!").build();
        }
        inscricaoService.inserir(inscricao);
        return Response.created(URI.create(inscricao.getId().toString())).entity(inscricaoRequest).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Integer id) {
       InscricaoReqDTO inscricaoDTO = InscricaoMapper.INSTANCE.toRequest(inscricaoService.obterInscricaoPorId(id));
        if(inscricaoDTO==null) {
            return Response.status(404).entity("Inscrição não encontrada!").build();
        }
        inscricaoService.excluir(id);
        return Response.noContent().build();
    }

    @GET
    public Response obter() {
        List<Inscricao> inscricoes = inscricaoService.obter();
        if(inscricoes.isEmpty()){
            return Response.status(404).entity("Nenhuma inscrição encontrada!").build();
        }
        List<InscricaoRespDTO> resp = inscricoes.stream().map(InscricaoMapper.INSTANCE::toResponse).collect(toList());
        return Response.ok(resp).build();
    }

}
