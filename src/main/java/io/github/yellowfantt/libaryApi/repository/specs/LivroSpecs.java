package io.github.yellowfantt.libaryApi.repository.specs;

import io.github.yellowfantt.libaryApi.model.Autor;
import io.github.yellowfantt.libaryApi.model.GeneroLivro;
import io.github.yellowfantt.libaryApi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEquals(String isbn){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn); // No caso o primeiro prara,etro é o que a gente copara co o segundo pra saber se tá igual
    };

    public static Specification<Livro> tituloLike(String titulo){
        //like pra quando ele digitar só um pouco do nome já ir buscar exemplo naruto e ele escreve nar

        return (root, query, criteriaBuilder) -> criteriaBuilder.
                like(criteriaBuilder.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%"); // os % é pra dizer se ele vai procurar algo igual antes ou depois
    }


    public static Specification<Livro> generoEquals(GeneroLivro genero){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEquals(Integer anoPublicacao){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal
                (criteriaBuilder.function("to_char", String.class, root.get("dataPublicacao"),criteriaBuilder.literal("YYYY")) ,anoPublicacao.toString());
    }


    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, criteriaBuilder) -> {

            Join<Livro, Autor> joinAutor =
                    root.join("autor", JoinType.INNER);

            return criteriaBuilder.like(
                    criteriaBuilder.upper(joinAutor.get("nome")),
                    "%" + nome.trim().toUpperCase() + "%"
            );
        };
    }
}
