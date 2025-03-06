package br.senac.sp.projetopoo.modelo;

import java.time.LocalDate;

import br.senac.sp.projetopoo.modelo.enums.CambioVeiculo;
import br.senac.sp.projetopoo.modelo.enums.DirecaoVeiculo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
/**
 * Classe para a criação e manipulação dos objetos Veículo. Essa classe está
 * usando o Lombok para a criação dos getters e setters de forma automática, e o
 * JPA (Java Persistence API) para a criação das tabelas do banco de dados a
 * partir dessa classe
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public class Veiculo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nome", columnDefinition = "varchar(100)", nullable = false)
	private String nome;

	@Column(name = "informacoes", columnDefinition = "TEXT")
	private String informacoes;

	@Column(name = "preco", nullable = false)
	private double preco;

	@Column(name = "fabricacao", nullable = false)
	private LocalDate fabricacao;

	@Column(name = "importacao", nullable = false)
	private boolean importacao;

	@Column(name = "cambio", columnDefinition = "varchar(100)", nullable = false)
	@Enumerated(EnumType.STRING)
	private CambioVeiculo cambio;

	@Column(name = "direcao", columnDefinition = "varchar(100)", nullable = false)
	@Enumerated(EnumType.STRING)
	private DirecaoVeiculo direcao;

	@ManyToOne
	@JoinColumn(name = "fk_marca", nullable = false, foreignKey = @ForeignKey(name = "fk_marca_constraint"))
	private Marca marca;

	@ManyToOne
	@JoinColumn(name = "fk_categoria", nullable = false, foreignKey = @ForeignKey(name = "fk_categoria_constraint"))
	private Carroceria carroceria;

	@Column(name = "imagem_veiculo", columnDefinition = "mediumblob", nullable = false)
	private byte[] imagemVeiculo;

	/*
	 * Caso seja necessário criar um atributo que não seja passado para o BD
	 * 
	 * @Transient Permite que um atributo não seja criado como um item no banco de
	 * dados, ou seja ele é criado na classe e não é levado para o banco de dados
	 */
}
