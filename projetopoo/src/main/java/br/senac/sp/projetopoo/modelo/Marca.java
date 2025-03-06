package br.senac.sp.projetopoo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
/**
 * Classe para a criação e manipulação dos objetos Marca. Essa classe está
 * usando o Lombok para a criação dos getters e setters de forma automática, e o
 * JPA (Java Persistence API) para a criação das tabelas do banco de dados a
 * partir dessa classe
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public class Marca {
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nome", columnDefinition = "varchar(100)", nullable = false, unique = true)
	private String nome;
	@Column(name = "logo", columnDefinition = "mediumblob", nullable = false)
	private byte[] logo;
	
	@Override
	public String toString() {
		return nome;
	}
	
	
	
}
