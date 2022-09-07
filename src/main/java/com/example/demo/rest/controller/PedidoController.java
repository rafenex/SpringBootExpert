package com.example.demo.rest.controller;


import com.example.demo.domain.entity.ItemPedido;
import com.example.demo.domain.entity.Pedido;
import com.example.demo.domain.enums.StatusPedido;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.rest.dto.AtualizacaoStatusPedidoDTO;
import com.example.demo.rest.dto.InformacoesItemPedidoDTO;
import com.example.demo.rest.dto.InformacoesPedidoDTO;
import com.example.demo.rest.dto.PedidoDTO;
import com.example.demo.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {
    PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save (@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id)
                .map( p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestBody AtualizacaoStatusPedidoDTO dto,
                             @PathVariable Integer id){
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));

    }

    private List<InformacoesItemPedidoDTO> converter(List<ItemPedido> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map(
                item -> InformacoesItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }

    private InformacoesPedidoDTO converter (Pedido pedido){
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItems()))
                .build();
    }
}
