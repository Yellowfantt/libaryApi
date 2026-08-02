package io.github.yellowfantt.libaryApi.controller.mappers;

import io.github.yellowfantt.libaryApi.controller.dto.AutorDTO;
import io.github.yellowfantt.libaryApi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);
    AutorDTO toDto(Autor autor);
}
