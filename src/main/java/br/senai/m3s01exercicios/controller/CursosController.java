package br.senai.m3s01exercicios.controller;

import br.senai.m3s01exercicios.dto.CursoDTO;
import br.senai.m3s01exercicios.mapper.CursoMapper;
import br.senai.m3s01exercicios.model.Curso;
import br.senai.m3s01exercicios.service.CursoService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/cursos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CursosController {

    @Inject
    private CursoService cursoService;

    @POST
    public Response inserir(@Valid CursoDTO cursoRequest) {
        Curso curso = CursoMapper.INSTANCE.toModel(cursoRequest);
        if(curso!=null) {
            return Response.status(409).entity("Curso já existe!").build();
        }
        cursoService.inserir(curso);
        return Response.created(URI.create(curso.getCodigo())).entity(cursoRequest).build();
    }

    @PUT
    @Path("/{codigo}")
    public Response alterar(@PathParam(("codigo")) String codigo, @Valid CursoDTO cursoRequest) {
        Curso curso = CursoMapper.INSTANCE.toModel(cursoRequest);
        if(!codigo.equals(cursoRequest.getCodigo())){
            return Response.status(404).entity("Curso não encontrado!").build();
        }
        curso.setCodigo(codigo);
        cursoService.alterar(curso);
        return Response.ok(cursoRequest).build();
    }

    @DELETE
    @Path("/{codigo}")
    public Response remover(@PathParam("codigo") String codigo) {
       CursoDTO cursoDTO = CursoMapper.INSTANCE.toDTO(cursoService.obter(codigo));
        if(cursoDTO==null) {
            return Response.status(404).entity("Curso não encontrado!").build();
        }
        cursoService.excluir(codigo);
        return Response.noContent().build();
    }

    @GET
    public Response obter(@QueryParam("sort") String sortBy, @QueryParam("limit") Integer limite) {
        List<Curso> cursos = cursoService.obter(sortBy, limite);
        if(cursos.isEmpty()){
            return Response.status(404).entity("Nenhum curso encontrado com esses parâmetros").build();
        }
        List<CursoDTO> resp = cursos.stream().map(CursoMapper.INSTANCE::toDTO).collect(toList());
        return Response.ok(resp).build();
    }

    @GET
    @Path("/{codigo}")
    public Response obterPorCodigo(@PathParam("codigo") String codigo) {
        Curso curso = cursoService.obterCursoPorCodigo(codigo);
        if(curso==null){
            return Response.status(404).entity("Curso não encontrado!").build();
        }
        CursoDTO resp = CursoMapper.INSTANCE.toDTO(curso);
        return Response.ok(resp).build();
    }
}
