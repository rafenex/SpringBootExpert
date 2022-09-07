package com.example.demo.rest.controller;

import com.example.demo.domain.entity.Cliente;
import com.example.demo.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {


    private Clientes clienteRepository;

    public ClienteController(Clientes clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Integer id){
        return clienteRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save (@RequestBody @Valid Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clienteRepository.findById(id).map(
                cliente -> {
                    clienteRepository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody @Valid Cliente cliente){
        clienteRepository.findById(id).map(
                clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity find (Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Cliente> lista =clienteRepository.findAll(example);
        return ResponseEntity.ok(lista);

    }

}
