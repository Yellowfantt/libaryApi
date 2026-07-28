package io.github.yellowfantt.libaryApi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data // dentro da data tem essas anotaçoes @getters e setrs to string equals e requiredargs construtor
@Table(name = "livro")
@ToString(exclude = "autor")
@EntityListeners(AuditingEntityListener.class)
public class Livro {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "isbn", nullable = false,length = 30)
    private String isbn;
    @Column(name = "titulo",length = 150, nullable = false)
    private String titulo;
    @Column(name = "dataPublicacao",nullable = false)
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;
    @Column(name = "preco",precision =  18, scale = 2)
    private BigDecimal preco;

    @ManyToOne(
           // cascade = CascadeType.ALL // literalmente eu consigo salvar um autor que n existe na base com o cascadeall, pra salvar um livro eu preciso de um id de um autor
            fetch = FetchType.LAZY // o padrão é o eager, que faz com que quando a gente realize uma busca no banco de dados ele traga os dados do autor junto, mas n queremos que ele faça isso, pq a query fica lenta, por ocnta qque o relacionamento é mantytoone

    )   //o oautor pode 1 ou mais livros
    @JoinColumn(name = "id_autor") // join colum pra dizer que vai ser uma chave estrangeira
    private Autor autor;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    @Column(name = "id_usuario")
    private UUID usuarioId;




}
