package io.github.yellowfantt.libaryApi.model;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public") //  o nome da tabela do banco de dados
@Getter //anotações do lombok. Isso faz com que em tempo de compilação sejam gerados os getrs e sets sozinho
@Setter
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class) // vai ficar ouvindo qualquer atulizaçao e vai verificar se tem os atributos que eu marquei com lastmodify e create data
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID) // isso aqui serve pra dizer que vai ser gerado automaticamento o id
    private UUID id;
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;
    @Column(name = "nacionalidade",length = 100, nullable = false)
    private String nacionalidade;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    @Column(name = "id_usuario")
    private UUID usuarioId;


    //mapedby vai dizer que a entidade n tem essa coluna no banco é apenas o relacionamento
    //Lazy = vai pegar somente os dados da entidade, se ele tiver uma associação n pega dados dela, pra isso usamso o transacional. o
    // Eager por padrão pega tudo então se tiver relacionamento ele vai pegar dados se outras tabela na busca tmb isso deixa lento
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // ou seja um autor pode ter vários livros, o primeiro sempre se refere a entidade que eu to escrevendo nesse caso autor pode ter vários livros
    private List<Livro> livros;



}
