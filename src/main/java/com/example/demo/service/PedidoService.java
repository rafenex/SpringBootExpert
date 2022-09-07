package com.example.demo.service;

import com.example.demo.domain.entity.Pedido;
import com.example.demo.domain.enums.StatusPedido;
import com.example.demo.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
