package io.github.yellowfantt.libaryApi.controller.mappers;

import io.github.yellowfantt.libaryApi.controller.dto.CadastroLivroDTO;
import io.github.yellowfantt.libaryApi.model.Livro;
import io.github.yellowfantt.libaryApi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(cadastroLivroDTO.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO cadastroLivroDTO);

}
