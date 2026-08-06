package io.github.yellowfantt.libaryApi.validator;

import io.github.yellowfantt.libaryApi.exceptions.CampoInvalidoException;
import io.github.yellowfantt.libaryApi.exceptions.RegistroDuplicadoException;
import io.github.yellowfantt.libaryApi.model.Livro;
import io.github.yellowfantt.libaryApi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidation {

    private final LivroRepository livroRepository;
    private final Integer ANO_EXIGENCIA = 2020;

    public void validar(Livro livro){
        if(existeLivroIsbn(livro)){
            throw new RegistroDuplicadoException("Já existe um livro com esse ISBN");
        }
        if (isPrecoObrigatorioNullo(livro)){
            throw new CampoInvalidoException("Para livros com anos de publicaçao apartir de 2020 o preço é obrigatorio", "preço");
        }

    }
    private boolean isPrecoObrigatorioNullo(Livro livro){
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA;
    }
    private boolean existeLivroIsbn(Livro livro){
        Optional<Livro> livroCurrent = livroRepository.findByIsbn(livro.getIsbn());
        if(livro.getId() == null){
            return livroCurrent.isPresent();
        }

        return livroCurrent.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));


    }
}
