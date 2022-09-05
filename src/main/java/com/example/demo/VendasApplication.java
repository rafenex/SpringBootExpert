package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.example.demo.domain.entity.Pedido;
import com.example.demo.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.domain.entity.Cliente;
import com.example.demo.domain.repository.Clientes;

@SpringBootApplication
public class VendasApplication {
	
	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes,
								  @Autowired Pedidos pedidos
					) {
		return args -> {
			System.out.println("Salvando clientes");
			Cliente cliente = new Cliente();
			cliente.setNome("Rafael");
			clientes.save(cliente);


			Pedido p = new Pedido();
			p.setCliente(cliente);
			p.setDataPedido(LocalDate.now());
			p.setTotal(BigDecimal.valueOf(100));
			pedidos.save(p);

			pedidos.findByCliente(cliente).forEach(System.out::println);






		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
