package br.senai.m3s01exercicios.mapper;

import br.senai.m3s01exercicios.dto.AlunoDTO;
import br.senai.m3s01exercicios.model.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AlunoMapper {

    AlunoMapper INSTANCE = Mappers.getMapper(AlunoMapper.class);

    AlunoDTO toDTO(Aluno model);

    Aluno toModel(AlunoDTO response);

}
