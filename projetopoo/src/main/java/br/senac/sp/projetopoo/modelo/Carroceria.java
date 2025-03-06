package br.senac.sp.projetopoo.modelo;

import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Classe para a criação e manipulação dos objetos Carroceria. Essa classe está
 * usando o Lombok para a criação dos getters e setters de forma automática, e o
 * JPA (Java Persistence API) para a criação das tabelas do banco de dados a
 * partir dessa classe
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
@Data
@Entity
public class Carroceria {
	// Atributos;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "tipo", columnDefinition = "varchar(30)", nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private CarroceriasVeiculo carroceria;
	
	@Override
	public String toString() {
		return carroceria.getTipo();
	}
}

