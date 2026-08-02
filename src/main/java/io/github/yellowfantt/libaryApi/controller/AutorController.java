package io.github.yellowfantt.libaryApi.controller;

import io.github.yellowfantt.libaryApi.controller.dto.AutorDTO;
import io.github.yellowfantt.libaryApi.controller.dto.ErroResposta;
import io.github.yellowfantt.libaryApi.controller.mappers.AutorMapper;
import io.github.yellowfantt.libaryApi.exceptions.OperacaoNaoPermitidaException;
import io.github.yellowfantt.libaryApi.exceptions.RegistroDuplicadoException;
import io.github.yellowfantt.libaryApi.model.Autor;
import io.github.yellowfantt.libaryApi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;
    private final AutorMapper mapper;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
        try{


            Autor autorEntidade = mapper.toEntity(dto);
            autorService.salvarAutor(autorEntidade);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorEntidade.getId()).toUri();


            return ResponseEntity.created(location).build();
        }catch(RegistroDuplicadoException e){
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }


    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes (@PathVariable("id")  String id) {
        var id_autor = UUID.fromString(id);

        return autorService.obterOporId(id_autor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDto(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
       /*
        if (autor.isPresent()) {
            Autor autorEntidade = autor.get();
            AutorDTO autorDTO = mapper.toDto(autorEntidade);
            return ResponseEntity.ok(autorDTO);
        }
        return ResponseEntity.notFound().build();
    }*/
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar (@PathVariable("id")  String id) {
        try {
            var id_autor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterOporId(id_autor);
            if (autor.isEmpty()) {
                return ResponseEntity.notFound().build();

            }
            autorService.deletar(autor.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);

        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false)  String nome, @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar (@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO) {
        try {
            var id_autor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterOporId(id_autor);
            if (autor.isEmpty()) {
                return ResponseEntity.notFound().build();

            }

            var autorBanco = autor.get();
            autorBanco.setNome(autorDTO.nome());
            autorBanco.setDataNascimento(autorDTO.dataNascimento());
            autorBanco.setNacionalidade(autorDTO.nacionalidade());
            autorService.atualizar(autorBanco);
            return ResponseEntity.noContent().build();
        } catch(RegistroDuplicadoException e){
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);

        }
    }


}
