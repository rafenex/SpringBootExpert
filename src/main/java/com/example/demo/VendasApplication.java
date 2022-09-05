package com.example.demo;

import java.util.List;

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
	public CommandLineRunner init(@Autowired Clientes clientes) {
		return args -> {
			System.out.println("Salvando clientes");
			Cliente cliente = new Cliente();
			cliente.setNome("Rafael");
			clientes.save(cliente);
			Cliente cliente2 = new Cliente();
			cliente2.setNome("Rodrigos");
			clientes.save(cliente2);
			
			List<Cliente> result = clientes.encontrarPorNome("Rodrigo");
			result.forEach(System.out::println);
			//boolean existe =  clientes.existsByNomeLike("%Rodrigo%");
			//System.out.println("Existe um cliente chamdo Rodrigo?" + existe);


		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
