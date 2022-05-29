package br.senai.m3s01exercicios.controller;

import br.senai.m3s01exercicios.dto.AlunoDTO;
import br.senai.m3s01exercicios.mapper.AlunoMapper;
import br.senai.m3s01exercicios.model.Aluno;
import br.senai.m3s01exercicios.service.AlunoService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/alunos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlunosController {

    @Inject
    private AlunoService alunoService;

    @POST
    public Response inserir(@Valid AlunoDTO alunoRequest) {
        Aluno aluno = AlunoMapper.INSTANCE.toModel(alunoRequest);
        if(aluno!=null) {
            return Response.status(409).entity("Aluno já existe!").build();
        }
        alunoService.inserir(aluno);
        return Response.created(URI.create(aluno.getMatricula().toString())).entity(alunoRequest).build();
    }

    @PUT
    @Path("/{matricula}")
    public Response alterar(@PathParam(("matricula")) Integer matricula, @Valid AlunoDTO alunoRequest) {
        Aluno aluno = AlunoMapper.INSTANCE.toModel(alunoRequest);
        if(!matricula.equals(alunoRequest.getMatricula())){
            return Response.status(404).entity("Aluno não encontrado!").build();
        }
        aluno.setMatricula(matricula);
        alunoService.alterar(aluno);
        return Response.ok(alunoRequest).build();
    }

    @DELETE
    @Path("/{matricula}")
    public Response remover(@PathParam("matricula") Integer matricula) {
       AlunoDTO alunoDTO = AlunoMapper.INSTANCE.toDTO(alunoService.obterAlunoPorMatricula(matricula));
        if(alunoDTO==null) {
            return Response.status(404).entity("Aluno não encontrado!").build();
        }
        alunoService.excluir(matricula);
        return Response.noContent().build();
    }

    @GET
    public Response obter(@QueryParam("sort") String nome) {
        List<Aluno> alunos = alunoService.obter(nome);
        if(alunos.isEmpty()){
            return Response.status(404).entity("Nenhum aluno encontrado com esse parâmetro").build();
        }
        List<AlunoDTO> resp = alunos.stream().map(AlunoMapper.INSTANCE::toDTO).collect(toList());
        return Response.ok(resp).build();
    }

    @GET
    @Path("/{matricula}")
    public Response obterPorMatricula(@PathParam("matricula") Integer matricula) {
        Aluno aluno = alunoService.obterAlunoPorMatricula(matricula);
        if(aluno==null){
            return Response.status(404).entity("Aluno não encontrado!").build();
        }
        AlunoDTO resp = AlunoMapper.INSTANCE.toDTO(aluno);
        return Response.ok(resp).build();
    }
}
