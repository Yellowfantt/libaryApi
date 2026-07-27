package io.github.yellowfantt.libaryApi.repository;

import io.github.yellowfantt.libaryApi.model.Autor;
import io.github.yellowfantt.libaryApi.model.GeneroLivro;
import io.github.yellowfantt.libaryApi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarLivro() {
        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(new BigDecimal("30.00"));
        livro.setIsbn("313182");
        livro.setTitulo("Naruto");
        livro.setDataPublicacao(LocalDate.of(1990, 10, 10));

        Autor autor = autorRepository.findById(UUID.fromString("2812196d-0081-4616-a645-8b72f271afb8")).orElse(null);
        livro.setAutor(autor);
        livroRepository.save(livro);


    }
    @Test
    void salvarLivroCascata() {
        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(new BigDecimal("20.00"));
        livro.setIsbn("9A233");
        livro.setTitulo("O lobo e a boneca");
        livro.setDataPublicacao(LocalDate.of(1990, 10, 10));

        Autor autor = new Autor();
        autor.setNome("Geraldo");
        autor.setDataNascimento(LocalDate.of(2026,1,10));
        autor.setNacionalidade("Portugues");

        livro.setAutor(autor);
        livroRepository.save(livro);


    }

    @Test
    void atualizarAutorLivro() {
        Livro livro = livroRepository.findById(UUID.fromString("b5fe4d9f-05d7-43b7-bf78-2e505c47b27a")).orElse(null);
        if (livro != null) {
            Autor autor = autorRepository.findById(UUID.fromString("098f2837-2de1-4cb2-97f2-3cd389c971e9")).orElse(null);
            if(autor != null){
                livro.setAutor(autor);
                livroRepository.save(livro);
            }
            else{
                System.out.println("Autor não encontrado! ");
            }
        }
        else{
            System.out.println("Livro não encontrado! ");
        }

    }

    @Test
    @Transactional // ai tipo, o transacional ele abre uma janela no banco de dados e permite que vc busque join ou seja, os dados do autor, então quando eu precisar eu anoto com o transacional
    void buscarLivroTeste(){

       Livro livro =  livroRepository.findById(UUID.fromString("01eba5ba-c35f-47b1-834b-514db8e5b20f")).orElse(null);
       System.out.println(livro.getTitulo()); // basicamente quando temos um relacionamento manytomany as buscar envolvem também o autor, deixando a qery mais lenta e pra isso usamso o fetch em lazy o padrão é eager
       System.out.println(livro.getAutor().getNome());

    }

    @Test
    void buscarLivroTitulo(){
        List<Livro> listaLivros = livroRepository.findByTitulo("O lobo e a boneca");
        listaLivros.forEach(System.out::println);

    }
    // usando jpql query metodos
    @Test
    void buscarLivroOrdenados(){
        List<Livro> livros = livroRepository.listarTodosOsLivrosByTitulo();
        livros.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = livroRepository.listarAutoresPorLivro();
        resultado.forEach(System.out::println);
    }


    @Test
    void listarLivrosDistintos(){
        var resultado = livroRepository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLiroGeneroAutoresEOrdenados(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    //USANDOP PARAMETROS NO JQPQUERY

    @Test
    void listarLivrosUsandoParametros(){
        var resultado = livroRepository.findByGenero(GeneroLivro.CIENCIA,"dataPublicacao");
        resultado.forEach(System.out::println);

    }

    //Deletando e atualizando dados do banco de dados

    @Test
    void deletarLivroByGenero(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);

    }
}