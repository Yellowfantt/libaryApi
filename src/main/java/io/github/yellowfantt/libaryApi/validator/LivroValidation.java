package io.github.yellowfantt.libaryApi.validator;

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

    public void validar(Livro livro){
        if(existeLivroIsbn(livro)){
            throw new RegistroDuplicadoException("Já existe um livro com esse ISBN");
        }

    }

    private boolean existeLivroIsbn(Livro livro){
        Optional<Livro> livroCurrent = livroRepository.findByIsbn(livro.getIsbn());
        if(livro.getId() == null){
            return livroCurrent.isPresent();
        }

        return livroCurrent.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));


    }
}
