package io.github.yellowfantt.libaryApi.controller.dto;

import io.github.yellowfantt.libaryApi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatorio!")  // PRA STRINGS
        @Size( min = 2, max = 100, message = "Campo fora do tamanho padrão!")
        String nome,
        @NotNull(message = "Campo obrigatorio!")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,
        @Size( min = 1, max = 50, message = "Campo fora do tamanho padrão!")
        @NotBlank(message = "Campo obrigatorio!")
        String nacionalidade) {

    public Autor mapearAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
