package com.example.demo.service.impl;

import com.example.demo.domain.entity.Cliente;
import com.example.demo.domain.entity.ItemPedido;
import com.example.demo.domain.entity.Pedido;
import com.example.demo.domain.entity.Produto;
import com.example.demo.domain.enums.StatusPedido;
import com.example.demo.domain.repository.Clientes;
import com.example.demo.domain.repository.ItensPedido;
import com.example.demo.domain.repository.Pedidos;
import com.example.demo.domain.repository.Produtos;
import com.example.demo.exception.PedidoNaoEncontradoExeption;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.rest.dto.ItemPedidoDTO;
import com.example.demo.rest.dto.PedidoDTO;
import com.example.demo.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final Produtos produtosRepository;
    private final Pedidos pedidosRepository;
    private final Clientes clientesRepository;
    private final ItensPedido itemsPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() ->
                new RegraNegocioException("Código de cliente inválido"));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
        List<ItemPedido> itemsPedidos = converterItems(pedido, dto.getItems());
        pedido.setItems(itemsPedidos);
        pedidosRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedidos);
        return pedido;
    }


    private List<ItemPedido> converterItems(Pedido pedido,List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items");
        }
        return items.stream()
                .map(dto-> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository.findById(idProduto)
                            .orElseThrow(() ->
                                    new RegraNegocioException("Código de produto inválido: "
                                            + idProduto
                                    ));


                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                     }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findById(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidosRepository.findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidosRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoExeption());
    }
}
