package io.github.yellowfantt.libaryApi.service;


import io.github.yellowfantt.libaryApi.model.Livro;
import io.github.yellowfantt.libaryApi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;


    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);

    }
}
