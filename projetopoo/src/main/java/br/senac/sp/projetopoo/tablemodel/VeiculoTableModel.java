package br.senac.sp.projetopoo.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senac.sp.projetopoo.modelo.Veiculo;
import br.senac.sp.projetopoo.util.FormatadorDataHorarioUtil;

/**
 * TableModel de Veiculo para a criação de tabelas com os atributos dos objetos
 * Veiculo
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
@SuppressWarnings("serial")
public class VeiculoTableModel extends AbstractTableModel {
	// Atributos;
	private List<Veiculo> lista;
	private String[] cabecalho = { "ID", "Nome", "Preço", "Fabricação", "Importação", "Câmbio", "Direção", "Marca",
			"Categoria" };

	/**
	 * Método construtor para inicializar a List através de uma List de Veiculo
	 * 
	 * @param lista - Recebe uma List com os Veiculos
	 */
	public VeiculoTableModel(List<Veiculo> lista) {
		this.lista = lista;
	}

	/**
	 * Método para setar a lista
	 * 
	 * @param lista - Recebe a nova lista
	 */
	public void setLista(List<Veiculo> lista) {
		this.lista = lista;
	}

	/**
	 * Método para contar a quantidade de linhas da linha da tabela
	 */
	@Override
	public int getRowCount() {
		return this.lista.size();
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
		Veiculo produto = lista.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return produto.getId();
		case 1:
			return produto.getNome();
		case 2:
			return produto.getPreco();
		case 3:
			String data;
			
			try {
				data = FormatadorDataHorarioUtil.formatarData(produto.getFabricacao());
			} catch (Exception e) {
				e.printStackTrace();
				return "Erro ao carregar a data";
			}
			return data;
		case 4:
			return (produto.isImportacao() ? "Importado" : "Nacional");
		case 5:
			return produto.getCambio().getTipo();
		case 6:
			return produto.getDirecao().getTipo();
		case 7:
			return produto.getMarca().getNome();
		case 8:
			return produto.getCarroceria();
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
