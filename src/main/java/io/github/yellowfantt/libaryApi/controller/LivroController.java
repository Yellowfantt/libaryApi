package io.github.yellowfantt.libaryApi.controller;


import io.github.yellowfantt.libaryApi.controller.dto.CadastroLivroDTO;
import io.github.yellowfantt.libaryApi.controller.dto.ErroResposta;
import io.github.yellowfantt.libaryApi.controller.mappers.LivroMapper;
import io.github.yellowfantt.libaryApi.exceptions.RegistroDuplicadoException;
import io.github.yellowfantt.libaryApi.model.Livro;
import io.github.yellowfantt.libaryApi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor

public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody  @Valid CadastroLivroDTO  cadastroLivroDTO){

        try{
            Livro livro = livroMapper.toEntity(cadastroLivroDTO); // aqui bascimante passo um dto e recebe um livro
            livroService.salvar(livro);
            var url = gerarHeaderController(livro.getId());
            return ResponseEntity.created(url).build();

        }catch(RegistroDuplicadoException e){
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(erroDto);
        }

    }

}
