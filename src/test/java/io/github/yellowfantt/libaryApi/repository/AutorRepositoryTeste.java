package io.github.yellowfantt.libaryApi.repository;

import io.github.yellowfantt.libaryApi.model.Autor;
import io.github.yellowfantt.libaryApi.model.GeneroLivro;
import io.github.yellowfantt.libaryApi.model.Livro;
import io.github.yellowfantt.libaryApi.repository.AutorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTeste {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;


    @Test
    public void salvarAutorTeste(){
        Autor autor = new Autor();
        autor.setNome("Luiz");
        autor.setDataNascimento(LocalDate.of(2026,1,10));
        autor.setNacionalidade("Brasileiro");
        var currentAutor = autorRepository.save(autor);
        System.out.println("Autor salvo com sucesso!" +  currentAutor);

    }
    @Test
    public void atualizarAutor(){
        var  id = UUID.fromString("7ac6a381-590a-4ae6-8e74-47dc15df77c0");
        Autor autor = autorRepository.findById(id).orElse(null);
        if(autor == null){
            System.out.println("Autor nullo "+ autor);
        }
        else{
            autor.setNacionalidade("Brasileiro");
            autorRepository.save(autor);
        }

    }
    @Test
    public void listarTodos(){
       var getallautores =  autorRepository.findAll();

       getallautores.forEach(System.out::println);
    }

    @Test
    public void contarAutores(){
      System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deletarAutorByUUID(){
        UUID id = UUID.fromString("e6f50355-8abd-466d-8652-ea51c21fb6ac");
        autorRepository.deleteById(id);

    }

    @Test
    public void salvarAutorComLivros(){
        Autor autor = new Autor();
        autor.setNome("Gaby");
        autor.setDataNascimento(LocalDate.of(2026,1,10));
        autor.setNacionalidade("Brasileiro");
        autor.setLivros(new ArrayList<Livro>());
        autorRepository.save(autor);

        // criando os livros para colocar no arrayliste do autor
        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(new BigDecimal("30.00"));
        livro.setIsbn("333-333");
        livro.setTitulo("Maria da penha");
        livro.setDataPublicacao(LocalDate.of(1990, 10, 10));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setPreco(new BigDecimal("50.00"));
        livro2.setIsbn("444-0-44");
        livro2.setTitulo("Souls dark");
        livro2.setDataPublicacao(LocalDate.of(1990, 10, 10));
        livro2.setAutor(autor);

        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        livroRepository.saveAll(autor.getLivros());

    }


    @Test
    //@Transactional pra buscar dados de outras entidades sem o fetch
    public void buscarLivro(){
        Autor autor = autorRepository.findById(UUID.fromString("50c44be8-5e90-47f8-b1aa-dda7e18e7548")).orElse(null);
        List<Livro> listaLivros =  livroRepository.findByAutor(autor);
        autor.setLivros(listaLivros);
        autor.getLivros().forEach(System.out::println);




    }

}
