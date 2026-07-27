package io.github.yellowfantt.libaryApi.validator;


import io.github.yellowfantt.libaryApi.exceptions.RegistroDuplicadoException;
import io.github.yellowfantt.libaryApi.model.Autor;
import io.github.yellowfantt.libaryApi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutorValidador {

    private final AutorRepository autorRepository;

    private boolean existeAutorCadastrado(Autor autor) {

        Optional<Autor> autorBanco =
                autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade());

        // Cadastro novo
        if (autor.getId() == null) {
            return autorBanco.isPresent();
        }

        // Atualização
        if (autorBanco.isPresent()) {
            return !autor.getId().equals(autorBanco.get().getId());
        }

        return false;
    }

    public void validarAutor(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }

    }
}
