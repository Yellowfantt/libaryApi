package io.github.yellowfantt.libaryApi.repository;

import io.github.yellowfantt.libaryApi.model.Autor;
import io.github.yellowfantt.libaryApi.model.GeneroLivro;
import io.github.yellowfantt.libaryApi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
* @see LivroRepositoryTest
*/

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {
    //Query metodo para buscar os livros do autor pelo id; select * from livro where id_autor = "aaaaa"

    boolean  existsByAutor (Autor autor);

    Optional<Livro> findByIsbn (String isbn);

    List<Livro> findByAutor(Autor autor); // iso é uma query metodo

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByPrecoAndTitulo(BigDecimal bigDecimal, String titulo);
    // select * from livro where data_publicacao between ? and ? // lisstar todas os livros de uma data até otura
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // agora vamos aprender com JPQL Query
    // No caso as querys aqui eu referncio as entidades por isso, Livro ali é maiusculo e não ao banco
    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listarTodosOsLivrosByTitulo();

    @Query("select a from Livro l join l.autor a ")
    List<Autor> listarAutoresPorLivro();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            select l.genero 
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileiro'
            order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();

    //named parametro
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdencacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdencacao") String paramOrdencacao);

    //poscional parameter
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPosicional(GeneroLivro generoLivro,  String paramOrdencacao);

    //atualizar e deletar

    @Modifying
    @Transactional
    @Query("delete from Livro where genero =?1")
    void deleteByGenero(GeneroLivro generoLivro);


    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao =?1")
    void updateData(LocalDate novaDataPublicacao);


}
