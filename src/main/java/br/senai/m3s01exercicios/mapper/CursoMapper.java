package br.senai.m3s01exercicios.mapper;

import br.senai.m3s01exercicios.dto.CursoDTO;
import br.senai.m3s01exercicios.model.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CursoMapper {

    CursoMapper INSTANCE = Mappers.getMapper(CursoMapper.class);

    @Mapping(source = "duracao", target = "duracaoEmDias")
    CursoDTO toDTO(Curso model);

    @Mapping(source = "duracaoEmDias", target = "duracao")
    Curso toModel(CursoDTO response);

}
