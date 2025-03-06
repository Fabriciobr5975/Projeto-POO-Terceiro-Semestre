package br.senac.sp.projetopoo.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senac.sp.projetopoo.modelo.Carroceria;

/**
 * TableModel de Carroceria para a criação de tabelas com os atributos dos objetos
 * Carroceria
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
@SuppressWarnings("serial")
public class CarroceriaTableModel extends AbstractTableModel {
	// Atributos;
	private List<Carroceria> lista;
	private String[] cabecalho = { "ID", "Categoria" };

	/**
	 * Método construtor para inicializar a List através de uma List de marcas
	 * 
	 * @param lista - Recebe uma List com as Carrocerias
	 */
	public CarroceriaTableModel(List<Carroceria> lista) {
		this.lista = lista;
	}
	
	/**
	 * Método para setar a lista
	 * 
	 * @param lista - Recebe a nova lista
	 */
	public void setLista(List<Carroceria> lista) {
		this.lista = lista;
	}


	/**
	 * Método para contar a quantidade de linhas da linha da tabela
	 */
	@Override
	public int getRowCount() {
		return lista.size();
	}

	/**
	 * Método para contar a quantidade de colunas da tabela
	 */
	@Override
	public int getColumnCount() {
		return this.cabecalho.length;
	}

	/**
	 * Método adicionar os elementos dentro da tabela
	 * 
	 * @param rowIndex    - Recebe a posição da linha
	 * @param columnIndex - Recebe a posição da coluna
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Carroceria categoria = lista.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return categoria.getId();
		case 1:
			return categoria.getCarroceria().getTipo();
		default:
			return null;
		}
	}
	
	/**
	 * Método para pegar o cabeçalho da tabela
	 * 
	 * @param column - Recebe as colunas da tabela
	 */
	@Override
	public String getColumnName(int column) {
		return cabecalho[column];
	}
}