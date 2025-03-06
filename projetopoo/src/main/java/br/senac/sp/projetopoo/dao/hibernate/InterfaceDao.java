package br.senac.sp.projetopoo.dao.hibernate;

import java.util.List;

/**
 * Interface genérica com os métodos voltado para a manipulação do CRUD (Create
 * - Read - Update - Delete). As classes que implementarem essa interface terão
 * que construir a lógica dos métodos
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public interface InterfaceDao<T> {

	/**
	 * Método para inserir novos objetos genéricos dentro do banco de dados
	 * 
	 * @param objeto - Recebe um objeto qualquer que será inserido no banco de dados
	 * 
	 * @return Retorna 1 para informar se os dados foram persistidos ou 0 caso os
	 *         dados não seja persistidos
	 * 
	 * @throws Exception Caso ocorra alguma exceção na hora da inserção, como erros
	 *                   de comunicação entre a aplicação e o banco de dados
	 */
	public int inserir(T objeto) throws Exception;

	/**
	 * Método para alterar os dados salvos de algum elemento dentro do banco de
	 * dados
	 * 
	 * @param objeto - Recebe um objeto qualquer que será alterado dentro do
	 *               sistema, onde esses novos dados passaram para o banco de dados
	 * 
	 * @return Retorna 1 para informar se os dados foram persistidos ou 0 caso os
	 *         dados não seja persistidos
	 * 
	 * @throws Exception Caso ocorra alguma exceção na hora da alteração, como erros
	 *                   de comunicação entre a aplicação e o banco de dados
	 */
	public int alterar(T objeto) throws Exception;

	/**
	 * Método para buscar um elemento dentro do banco de dados
	 * 
	 * @param id - Recebe um valor inteiro que representa o ID do elemento que está
	 *           sendo procurado
	 * 
	 * @return Retorna o objeto qualquer obtido através da busca no banco de dados
	 * 
	 * @throws Exception Caso ocorra alguma exceção na hora da inserção, como erros
	 *                   de comunicação entre a aplicação e o banco de dados
	 */
	public T buscar(int id) throws Exception;

	/**
	 * Método para excluir um elemento de dentro do banco de dados
	 * 
	 * @param id - Recebe um valor inteiro que representa o ID do elemento que está
	 *           sendo procurado, neste caso estamos procurando esse elemento para
	 *           realizar a sua exclusão
	 * 
	 * @return Retorna 1 para informar se os dados foram persistidos ou 0 caso os
	 *         dados não seja persistidos
	 * 
	 * @throws Exception Caso ocorra alguma exceção na hora da inserção, como erros
	 *                   de comunicação entre a aplicação e o banco de dados
	 */
	public int excluir(int id) throws Exception;

	/**
	 * Método que busca todos os elementos de dentro do banco de dados e salva todos
	 * esses elementos dentro de uma lista, no caso um List genérico. Geralmente é
	 * usado para a criação de JTable, mas pode ser usado para outras finalidades
	 * 
	 * @return Retorna uma lista genérica com todos os elementos encontrados dentro
	 *         do banco de dados
	 * 
	 * @throws Exception Caso ocorra alguma exceção na hora da inserção, como erros
	 *                   de comunicação entre a aplicação e o banco de dados
	 */
	public List<T> listar() throws Exception;
}
