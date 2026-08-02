package io.github.yellowfantt.libaryApi.controller.dto;

import io.github.yellowfantt.libaryApi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisLivroDTO( UUID id, String isbn, String titulo, LocalDate dataPublicacao, GeneroLivro genero,
                                       BigDecimal preco, AutorDTO autor ) {
}
