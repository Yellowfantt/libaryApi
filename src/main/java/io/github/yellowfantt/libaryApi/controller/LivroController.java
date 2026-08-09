package io.github.yellowfantt.libaryApi.controller;


import io.github.yellowfantt.libaryApi.controller.dto.CadastroLivroDTO;
import io.github.yellowfantt.libaryApi.controller.dto.ErroResposta;
import io.github.yellowfantt.libaryApi.controller.dto.ResultadoPesquisLivroDTO;
import io.github.yellowfantt.libaryApi.controller.mappers.LivroMapper;
import io.github.yellowfantt.libaryApi.exceptions.RegistroDuplicadoException;
import io.github.yellowfantt.libaryApi.model.GeneroLivro;
import io.github.yellowfantt.libaryApi.model.Livro;
import io.github.yellowfantt.libaryApi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor

public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO) {

        Livro livro = livroMapper.toEntity(cadastroLivroDTO); // aqui bascimante passo um dto e recebe um livro
        livroService.salvar(livro);
        var url = gerarHeaderController(livro.getId());
        return ResponseEntity.created(url).build();


    }
    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisLivroDTO> obterDetalhes(@PathVariable String id) {
        return livroService.obterPorId(UUID.fromString(id)).map(livro -> {var dto = livroMapper.toDTO(livro);
            return ResponseEntity.ok(dto);}).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarLivro(@PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nomeAutor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "anoPublicacao", required = false)
            Integer anoPublicacao, @RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina) {

        var resultadoPagina =  livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisLivroDTO> resultado = resultadoPagina.map(livroMapper::toDTO);

        return ResponseEntity.ok(resultado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable String id,
            @RequestBody CadastroLivroDTO cadastroLivroDTO) {

        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {

                    Livro entidadeAuxiliar = livroMapper.toEntity(cadastroLivroDTO);

                    livro.setDataPublicacao(entidadeAuxiliar.getDataPublicacao());
                    livro.setTitulo(entidadeAuxiliar.getTitulo());
                    livro.setPreco(entidadeAuxiliar.getPreco());
                    livro.setIsbn(entidadeAuxiliar.getIsbn());
                    livro.setGenero(entidadeAuxiliar.getGenero());
                    livro.setAutor(entidadeAuxiliar.getAutor());

                    livroService.atualizar(livro);

                    return ResponseEntity.noContent().build();

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //FINALIZADA A API agr é iniciar a documentação com o swagger

}
