package com.example.demo.exception;

public class PedidoNaoEncontradoExeption extends RuntimeException {
    public PedidoNaoEncontradoExeption() {
        super("Pedido não encontrado");
    }
}
