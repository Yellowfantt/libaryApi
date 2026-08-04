package io.github.yellowfantt.libaryApi.service;


import io.github.yellowfantt.libaryApi.model.GeneroLivro;
import io.github.yellowfantt.libaryApi.model.Livro;
import io.github.yellowfantt.libaryApi.repository.LivroRepository;
import io.github.yellowfantt.libaryApi.repository.specs.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Contract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;


    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);

    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {
//        Specification<Livro> specs  = Specification.where
//                (LivroSpecs.isbnEquals(isbn)).
//                 and(LivroSpecs.tituloLike(titulo)
//                .and(LivroSpecs.generoEquals(genero)));

        Specification<Livro> specs = (root, query, criteriaBuilder) ->  criteriaBuilder.conjunction();

        if(isbn != null) {
            specs = specs.and(LivroSpecs.isbnEquals(isbn));
        }
        if(titulo != null) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }
        if(genero != null) {
            specs = specs.and(LivroSpecs.generoEquals(genero));
        }
        if (anoPublicacao != null) {
            specs = specs.and(LivroSpecs.anoPublicacaoEquals(anoPublicacao));
        }

        return livroRepository.findAll(specs); // buscando por especs

    }
}
