package br.senac.sp.projetopoo.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.senac.sp.projetopoo.dao.hibernate.EMFactory;
import br.senac.sp.projetopoo.dao.hibernate.InterfaceDao;
import br.senac.sp.projetopoo.dao.hibernate.VeiculoDaoHibernate;
import br.senac.sp.projetopoo.modelo.Veiculo;
import br.senac.sp.projetopoo.modelo.enums.CambioVeiculo;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import br.senac.sp.projetopoo.modelo.enums.DirecaoVeiculo;
import br.senac.sp.projetopoo.tablemodel.VeiculoTableModel;
import br.senac.sp.projetopoo.util.FrameUtil;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

/**
 * Classe do Frame da listagem dos Veiculos.
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public class FrameListagemVeiculo extends JFrame {
	// Atributos do Veiculo
	private List<Veiculo> veiculos;
	private InterfaceDao<Veiculo> daoVeiculo;

	// Atributos do Frame
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// Atributos da Busca
	private JLabel lblBusca;
	private JTextField tfBuscar;
	private JComboBox<String> cmbBusca;
	private JButton btnBuscar;

	/*
	 * Atributo que guarda os elementos em String referente as buscas que usuário
	 * pode realizar no sistema
	 */
	private String[] tiposBuscas;

	// Atributo do painel de rolagem
	private JScrollPane scrollPane;

	// Atributo da tabela
	private JTable tbVeiculo;

	// Atributo da tableModel
	private VeiculoTableModel tableModel;

	// Botões
	private JButton btnAbrirTelaVeiculo;
	private JButton btnVoltarParaMenuPrincipal;
	private JButton btnResetarTabela;
	private JButton btnLimparCampoBusca;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameListagemVeiculo frame = new FrameListagemVeiculo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameListagemVeiculo() {
		// Instanciando o objeto dao para possibilitar o CRUD
		daoVeiculo = new VeiculoDaoHibernate(EMFactory.getEntityManager());
		// Inicializando o vetor com os tipos de busca para a listagem
		tiposBuscas = new String[8];
		// Colocando no vetor os tipos de busca
		colocarTiposDeBusca();

		try {
			// Pegando todas os veículos criadas e salvando em uma lista de veiculos
			veiculos = daoVeiculo.listar();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(FrameListagemVeiculo.this, "Ocorreu um erro ao carregar a lista de véiculos",
					"Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		// Instanciando o tableModel para criar a tabela com todas as marcas criadas
		tableModel = new VeiculoTableModel(veiculos);

		// Atributos do Frame
		setTitle("Listagem de Véiculos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Centralizando a tela
		FrameUtil.centralizarTela(this);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Label Busca
		lblBusca = new JLabel("Buscar:");
		lblBusca.setFont(new Font("Arial", Font.BOLD, 12));
		lblBusca.setBounds(10, 10, 45, 30);
		contentPane.add(lblBusca);

		// TextField Buscar
		tfBuscar = new JTextField();
		tfBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
		tfBuscar.setBounds(65, 11, 503, 30);
		contentPane.add(tfBuscar);
		tfBuscar.setColumns(10);

		// ComboBox Busca
		cmbBusca = new JComboBox<String>(tiposBuscas);
		cmbBusca.setFont(new Font("Arial", Font.BOLD, 12));
		cmbBusca.setBounds(592, 10, 152, 30);
		contentPane.add(cmbBusca);

		// Button Buscar
		btnBuscar = new JButton("Buscar");
		btnBuscar.setMnemonic('B');
		btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
		btnBuscar.setBackground(Color.DARK_GRAY);
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setBorder(new LineBorder(Color.BLACK));
		btnBuscar.setBounds(754, 10, 120, 30);

		// Adicionando evento ao botão de buscar
		btnBuscar.addActionListener(e -> {
			try {
				determinarTipoBusca();
			} catch (Exception ex) {
				if (ex instanceof NumberFormatException) {
					JOptionPane.showMessageDialog(FrameListagemVeiculo.this,
							"Esse campo só aceita valores númericos, por favor digite apenas valores númericos", "Erro",
							JOptionPane.WARNING_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(FrameListagemVeiculo.this, "Erro: " + ex.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		contentPane.add(btnBuscar);

		// Adicionando a tabela dentro do scrollPane, assim a tabela tem uma rolagem
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 864, 383);
		contentPane.add(scrollPane);

		// Tabela de Véiculo
		tbVeiculo = new JTable(tableModel);
		tbVeiculo.setRowSelectionAllowed(false);
		tbVeiculo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbVeiculo.getColumnModel().getColumn(0).setPreferredWidth(60);
		tbVeiculo.getColumnModel().getColumn(1).setPreferredWidth(91);
		tbVeiculo.getColumnModel().getColumn(2).setPreferredWidth(95);
		tbVeiculo.getColumnModel().getColumn(3).setPreferredWidth(95);
		tbVeiculo.getColumnModel().getColumn(4).setPreferredWidth(100);
		tbVeiculo.getColumnModel().getColumn(5).setPreferredWidth(100);
		tbVeiculo.getColumnModel().getColumn(6).setPreferredWidth(100);
		tbVeiculo.getColumnModel().getColumn(7).setPreferredWidth(100);
		tbVeiculo.getColumnModel().getColumn(8).setPreferredWidth(120);
		tableModel.fireTableDataChanged();

		scrollPane.setViewportView(tbVeiculo);

		// Button de abrir a tela de manipulação do veículo
		btnAbrirTelaVeiculo = new JButton("Abrir tela do Veículo");
		btnAbrirTelaVeiculo.setMnemonic('V');
		btnAbrirTelaVeiculo.setFont(new Font("Arial", Font.BOLD, 12));
		btnAbrirTelaVeiculo.setBounds(10, 478, 204, 30);
		btnAbrirTelaVeiculo.setBackground(Color.DARK_GRAY);
		btnAbrirTelaVeiculo.setForeground(Color.WHITE);
		btnAbrirTelaVeiculo.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(btnAbrirTelaVeiculo);

		// Adicionando evento ao botão de abrir a tela de manipulação do veículo
		btnAbrirTelaVeiculo.addActionListener(e -> {
			new FrameManipulacaoVeiculo();
			dispose();
		});

		// Button Voltar para o menu principal
		btnVoltarParaMenuPrincipal = new JButton("Voltar para o Menu Principal");
		btnVoltarParaMenuPrincipal.setMnemonic('M');
		btnVoltarParaMenuPrincipal.setFont(new Font("Arial", Font.BOLD, 12));
		btnVoltarParaMenuPrincipal.setBounds(670, 478, 204, 30);
		btnVoltarParaMenuPrincipal.setBackground(Color.DARK_GRAY);
		btnVoltarParaMenuPrincipal.setForeground(Color.WHITE);
		btnVoltarParaMenuPrincipal.setBorder(new LineBorder(Color.BLACK));

		// Adicionando evento para o botão de voltar para menu principal
		btnVoltarParaMenuPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrameInicial();
				dispose();
			}
		});

		contentPane.add(btnVoltarParaMenuPrincipal);

		// Button Resetar tabela
		btnResetarTabela = new JButton("Resetar Tabela");
		btnResetarTabela.setMnemonic('R');
		btnResetarTabela.setFont(new Font("Arial", Font.BOLD, 12));
		btnResetarTabela.setBounds(224, 478, 222, 30);
		btnResetarTabela.setBackground(Color.DARK_GRAY);
		btnResetarTabela.setForeground(Color.WHITE);
		btnResetarTabela.setBorder(new LineBorder(Color.BLACK));

		// Adicionando evento ao botão de resetar a tabela
		btnResetarTabela.addActionListener(e -> {
			try {
				// Listando todas os Veiculos salvos
				veiculos = daoVeiculo.listar();
				// Mudando a lista que será usada para exibir os dados na tela
				tableModel.setLista(veiculos);
				// Para atualizar os dados dentro da tabela
				tableModel.fireTableDataChanged();

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(FrameListagemVeiculo.this, "Erro: " + e1.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		});

		contentPane.add(btnResetarTabela);

		// Button Limpar Campo de Busca
		btnLimparCampoBusca = new JButton("Limpar Busca");
		btnLimparCampoBusca.setMnemonic('L');
		btnLimparCampoBusca.setFont(new Font("Arial", Font.BOLD, 12));
		btnLimparCampoBusca.setBounds(456, 478, 204, 30);
		btnLimparCampoBusca.setBackground(Color.DARK_GRAY);
		btnLimparCampoBusca.setForeground(Color.WHITE);
		btnLimparCampoBusca.setBorder(new LineBorder(Color.BLACK));

		// Adicionando evento ao botão de limpar os campos da busca
		btnLimparCampoBusca.addActionListener(e -> {
			limparCampoBusca();
		});

		contentPane.add(btnLimparCampoBusca);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Método para colocar no vetor de tipos de buscas, todas as buscas que os
	 * usuários poderam realizar
	 */
	private void colocarTiposDeBusca() {
		tiposBuscas[0] = ("ID");
		tiposBuscas[1] = ("Nome");
		tiposBuscas[2] = ("Marca");
		tiposBuscas[3] = ("Câmbio");
		tiposBuscas[4] = ("Categoria");
		tiposBuscas[5] = ("Direção");
		tiposBuscas[6] = ("Preço");
		tiposBuscas[7] = ("Data de Fabricação");
	}

	/**
	 * Método para pegar o valor selecionado na ComboBox e realizar a listagem de
	 * acordo com a opção selecionada
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	private void determinarTipoBusca() throws NumberFormatException, NullPointerException, Exception {
		// Verificando se o campo não está vazia
		verificarSeCampoBuscaEstaVazia(tfBuscar.getText().trim());

		/*
		 * Criando uma dao a partir da classe concreta, pois precisamos usar um método
		 * de busca presente somenta nesta classes, como a dao é criada a partir da
		 * interface não conseguimos utilizar os métodos criados dentro da classe
		 * concretas, somente os métodos criados e implementados pelas classes que
		 * realizaram um "contrato" com a interface
		 */
		VeiculoDaoHibernate daoBusca = (VeiculoDaoHibernate) daoVeiculo;

		switch (cmbBusca.getSelectedIndex()) {
		case 0:
			// Listando pelo id
			veiculos = daoBusca.listarPorId(Long.parseLong(tfBuscar.getText().replaceAll("[ ]", "")));

			// Método para validar se a lista não está vazia
			verificarSeListaEstaVazia(veiculos);

			// Mudando a lista que será usada para exibir os dados na tela
			tableModel.setLista(veiculos);

			// Para atualizar os dados dentro da tabela
			tableModel.fireTableDataChanged();
			break;

		case 1:
			// Listando pelo nome
			veiculos = daoBusca.listarPorNome(tfBuscar.getText().trim());

			// Método para validar se a lista não está vazia
			verificarSeListaEstaVazia(veiculos);

			// Mudando a lista que será usada para exibir os dados na tela
			tableModel.setLista(veiculos);

			// Para atualizar os dados dentro da tabela
			tableModel.fireTableDataChanged();
			break;

		case 2:
			// Listando pela marca
			veiculos = daoBusca.listarPorMarca(tfBuscar.getText().trim());

			// Método para validar se a lista não está vazia
			verificarSeListaEstaVazia(veiculos);

			// Mudando a lista que será usada para exibir os dados na tela
			tableModel.setLista(veiculos);

			// Para atualizar os dados dentro da tabela
			tableModel.fireTableDataChanged();
			break;

		case 3:
			/*
			 * Criando um atributo da enum de câmbio para realizar a busca através do valor
			 * da enum
			 */
			CambioVeiculo cambioBusca = null;

			// Verificando se o elemento corresponde a alguma enum
			for (CambioVeiculo i : CambioVeiculo.values()) {
				if (i.getTipo().equals(tfBuscar.getText().trim())) {
					cambioBusca = i;
					break;
				}
			}

			// Se o elemento não corresponder a uma enum retorna uma NullPointerException
			if (cambioBusca == null) {
				throw new NullPointerException(
						"O câmbio " + tfBuscar.getText().trim() + " não existe, por favor insira um câmbio válido");
			}

			// Listando pelo câmbio
			veiculos = daoBusca.listarPorCambio(cambioBusca);

			// Mudando a lista que será usada para exibir os dados na tela
			tableModel.setLista(veiculos);

			// Para atualizar os dados dentro da tabela
			tableModel.fireTableDataChanged();
			break;

		case 4:
			/*
			 * Criando um atributo da enum de carrocerias para realizar a busca através do
			 * valor da enum
			 */
			CarroceriasVeiculo carroceriaBusca = null;

			// Verificando se o elemento corresponde a alguma enum
			for (CarroceriasVeiculo i : CarroceriasVeiculo.values()) {
				if (i.getTipo().equals(tfBuscar.getText().trim())) {
					carroceriaBusca = i;
					break;
				}
			}

			// Se o elemento não corresponder a uma enum retorna uma NullPointerException
			if (carroceriaBusca == null) {
				throw new NullPointerException("A carroceria " + tfBuscar.getText().trim()
						+ " não existe, por favor insira uma carroceria válida");
			}

			// Listando pela categoria
			veiculos = daoBusca.listarPorCategoria(carroceriaBusca);

			// Mudando a lista que será usada para exibir os dados na tela
			tableModel.setLista(veiculos);

			// Para atualizar os dados dentro da tabela
			tableModel.fireTableDataChanged();
			break;

		case 5:
			/*
			 * Criando um atributo da enum de direção para realizar a busca através do valor
			 * da enum
			 */
			DirecaoVeiculo direcaoBusca = null;

			// Verificando se o elemento corresponde a alguma enum
			for (DirecaoVeiculo i : DirecaoVeiculo.values()) {
				if (i.getTipo().equals(tfBuscar.getText().trim())) {
					direcaoBusca = i;
					break;
				}
			}

			// Se o elemento não corresponder a uma enum retorna uma NullPointerException
			if (direcaoBusca == null) {
				throw new NullPointerException(
						"A direção " + tfBuscar.getText().trim() + " não existe, por favor insira uma direção válida");
			}

			// Listando pela direção
			veiculos = daoBusca.listarPorDirecao(direcaoBusca);

			// Mudando a lista que será usada para exibir os dados na tela
			tableModel.setLista(veiculos);

			// Para atualizar os dados dentro da tabela
			tableModel.fireTableDataChanged();
			break;

		case 6:
			// Listando pelo preço
			veiculos = daoBusca.listarPorPreco(Double.parseDouble(tfBuscar.getText().trim()));

			// Método para validar se a lista não está vazia
			verificarSeListaEstaVazia(veiculos);

			// Mudando a lista que será usada para exibir os dados na tela
			tableModel.setLista(veiculos);

			// Para atualizar os dados dentro da tabela
			tableModel.fireTableDataChanged();
			break;

		case 7:
			// Listando pela data de fabricação
			veiculos = daoBusca.listarPorDataFabricacao(formatarData());

			// Método para validar se a lista não está vazia
			verificarSeListaEstaVazia(veiculos);

			// Mudando a lista que será usada para exibir os dados na tela
			tableModel.setLista(veiculos);

			// Para atualizar os dados dentro da tabela
			tableModel.fireTableDataChanged();
			break;
		default:
			throw new Exception("Nenhum tipo de busca selecionado");
		}
	}

	/**
	 * Método para limpar os campos da busca
	 */
	private void limparCampoBusca() {
		tfBuscar.setText("");
		cmbBusca.setSelectedIndex(0);
	}

	/**
	 * Método para formatar a data que o usuário colocar no campo de busca e criar
	 * um novo objeto do tipo LocalDate para realizar a filtragem dos veículos
	 * criados através da data de fabricação
	 * 
	 * @return Retorna um objeto do tipo LocalDate para realizar a listagem dos
	 *         veículos através dessa data
	 * 
	 * @throws IllegalArgumentException - Caso o usuário não coloque a data
	 *                                  completa, ou essa data não seja um valor
	 *                                  númerico (int)
	 * 
	 * @throws NumberFormatException    - Caso a data em String não possa ser
	 *                                  convertida para int, impossibilitando a
	 *                                  continuação do método
	 * 
	 * @throws Exception                - Caso alguma exceção que não seja
	 *                                  IllegalArgumentException ou
	 *                                  NumberFormatException aconteça
	 */
	private LocalDate formatarData() throws IllegalArgumentException, NumberFormatException, Exception {
		// Variável que pega a data e Limpa os dados do campo data
		String data = tfBuscar.getText().replaceAll("[ -/]", "");
		// Variáveis para a data
		int dia = 0, mes = 0, ano = 0;

		/*
		 * Verificando se o valor passado no campo tem o tamanho correto para a data e
		 * contenha apenas valores numéricos
		 */
		if (data.length() != 8 || !data.matches("\\d{8}")) {
			throw new IllegalArgumentException("Digite a data no formatado DD/MM/AAAA com apenas números");
		}

		/*
		 * Atribuindo a cada variável da data o valor correto, correspodente a sua parte
		 * da data
		 */
		try {
			dia = Integer.parseInt(data.substring(0, 2));
			mes = Integer.parseInt(data.substring(2, 4));
			ano = Integer.parseInt(data.substring(4, 8));
		} catch (NumberFormatException ex) {
			throw new Exception(
					"Ocorreu um erro ao converter a data, por favor digite novamente no formato DD/MM/AAAA");
		}

		// Verificando se o valor passado para o dia é valido
		if (dia > 31 || dia < 1) {
			throw new IllegalArgumentException("O dia " + dia + " é inválido, por favor coloque um dia válido");
		}

		// Verificando se o valor passado para o mês é valido
		if (mes > 30 || mes < 1) {
			throw new IllegalArgumentException("O mês " + mes + " é inválido, por favor coloque um mês validao");
		}

		// Verificando se o valor passado para o ano é valido
		if (ano < 1885 || ano > LocalDate.now().getYear()) {
			throw new IllegalArgumentException(
					"O ano " + ano + " é inválido, por favor coloque um ano entre 1885 e " + LocalDate.now().getYear());
		}

		/*
		 * retornando um objeto do tipo LocalDate com as variáveis inteiras de cada
		 * parte da data
		 */
		return LocalDate.of(ano, mes, dia);
	}

	/**
	 * Método para verificar se o campo da busca está vazia
	 * 
	 * @param campo - Recebe o texto do campo da busca
	 * 
	 * @throws IllegalArgumentException - Caso o campo esteja vazio lança uma
	 *                                  IllegalArgumentException, pois a busca não
	 *                                  pode continuar
	 */
	private void verificarSeCampoBuscaEstaVazia(String campo) throws IllegalArgumentException {
		if (campo == null || campo.isEmpty()) {
			throw new IllegalArgumentException("O campo da busca não pode ficar vazio");
		}
	}

	/**
	 * Método para verificar se a lista está vazia
	 * 
	 * @param <T> Recebe um Lista de qualquer tipo
	 * 
	 * @throws NullPointerException - Caso seja verificado se a lista passada não
	 *                              tenha elementos
	 */
	private <T> void verificarSeListaEstaVazia(List<T> lista) throws NullPointerException {
		if (lista == null || lista.isEmpty()) {
			throw new NullPointerException("Não foi encontrado itens para essa busca");
		}
	}
}