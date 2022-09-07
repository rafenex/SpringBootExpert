package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Cliente;
import com.example.demo.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface Pedidos extends JpaRepository <Pedido,Integer> {
    Set<Pedido> findByCliente(Cliente cliente);

    Optional<Pedido> findById(Integer id);
}
