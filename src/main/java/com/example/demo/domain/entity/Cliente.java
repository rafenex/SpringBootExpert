package com.example.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "nome", length = 100)
	@NotEmpty(message="{campo.nome.obrigatorio}")
	private String nome;

	@NotEmpty(message = "{campo.cpf.obrigatorio}")
	@CPF(message = "{campo.cpf.invalido}")
	@Column(name="cpf", length = 11)
	private String cpf;

	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private Set<Pedido> pedidos;


	

}
