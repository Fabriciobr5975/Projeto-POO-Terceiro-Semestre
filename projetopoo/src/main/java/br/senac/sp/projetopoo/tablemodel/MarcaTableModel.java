package br.senac.sp.projetopoo.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senac.sp.projetopoo.modelo.Marca;

/**
 * TableModel de Marca para a criação de tabelas com os atributos dos objetos
 * Marca
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
@SuppressWarnings("serial")
public class MarcaTableModel extends AbstractTableModel {
	// Atributos;
	private List<Marca> lista;
	private String[] cabecalho = { "ID", "Nome" };

	/**
	 * Método construtor para inicializar a List através de uma List de marcas
	 * 
	 * @param marcas - Recebe uma List com as marcas
	 */
	public MarcaTableModel(List<Marca> marcas) {
		this.lista = marcas;
	}

	/**
	 * Método para setar a lista
	 * 
	 * @param lista - Recebe a nova lista
	 */
	public void setLista(List<Marca> lista) {
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
		return cabecalho.length;
	}

	/**
	 * Método adicionar os elementos dentro da tabela
	 * 
	 * @param rowIndex    - Recebe a posição da linha
	 * @param columnIndex - Recebe a posição da coluna
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Marca marca = lista.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return marca.getId();
		case 1:
			return marca.getNome();
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
