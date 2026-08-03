package io.github.yellowfantt.libaryApi.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {

    default URI gerarHeaderController(UUID uuid) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(uuid).toUri();

    }
}
