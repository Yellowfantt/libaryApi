package io.github.yellowfantt.libaryApi.service;


import io.github.yellowfantt.libaryApi.model.GeneroLivro;
import io.github.yellowfantt.libaryApi.model.Livro;
import io.github.yellowfantt.libaryApi.repository.LivroRepository;
import io.github.yellowfantt.libaryApi.repository.specs.LivroSpecs;
import io.github.yellowfantt.libaryApi.validator.LivroValidation;
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
    private final LivroValidation livroValidation;

    public Livro salvar(Livro livro) {
        livroValidation.validar(livro);
        return livroRepository.save(livro);

    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao
    ) {
        Specification<Livro> specs = Specification.allOf();

        if (isbn != null && !isbn.isBlank()) {
            specs = specs.and(LivroSpecs.isbnEquals(isbn));
        }

        if (titulo != null && !titulo.isBlank()) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if (genero != null) {
            specs = specs.and(LivroSpecs.generoEquals(genero));
        }

        if (anoPublicacao != null) {
            specs = specs.and(LivroSpecs.anoPublicacaoEquals(anoPublicacao));
        }

        if (nomeAutor != null && !nomeAutor.isBlank()) {
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs);
    }

    public void atualizar(Livro livro) {

        if(livro.getId() == null) {
            throw  new IllegalArgumentException("Para atualizar um livro é necessário que ele exista já na base de dados.");
        }
        livroValidation.validar(livro);
        livroRepository.save(livro);
    }
}