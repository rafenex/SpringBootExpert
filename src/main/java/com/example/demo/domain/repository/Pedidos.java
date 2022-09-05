package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository <Pedido,Integer> {
}
