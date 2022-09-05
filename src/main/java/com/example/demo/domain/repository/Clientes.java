package com.example.demo.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.entity.Cliente;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query(value = " select c from Cliente c where c.nome like :nome ")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    @Modifying
    void deleteByNome(String nome);

    Cliente findOneByNome(String nome);

    boolean existsByNomeLike(String nome);
}
