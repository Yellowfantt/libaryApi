package io.github.yellowfantt.libaryApi.service;


import io.github.yellowfantt.libaryApi.exceptions.OperacaoNaoPermitidaException;
import io.github.yellowfantt.libaryApi.model.Autor;
import io.github.yellowfantt.libaryApi.repository.AutorRepository;
import io.github.yellowfantt.libaryApi.repository.LivroRepository;
import io.github.yellowfantt.libaryApi.validator.AutorValidador;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // pra isso os campos precisar ser privado entáo private final AutorRepository autorRepository;
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidador autorValidador;
    private final LivroRepository livroRepository;



    public Autor salvarAutor(Autor autor) {
        autorValidador.validarAutor(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {

        if(autor.getId() == null){
            throw  new IllegalArgumentException("Para atualizar é necessário que o autor eseja salvo na base de dados");
        }
        autorValidador.validarAutor(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterOporId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {

        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor que possui autor possui livros cadastrados");
        }
        autorRepository.delete(autor);

    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null ) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);

        }
        if(nome !=null ) {
            return autorRepository.findByNome(nome);
        }
        if(nacionalidade != null ) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);

    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher  matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id", "dataNascimento", "nacionalidade", "dataCadastro");
        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);



    }
}
