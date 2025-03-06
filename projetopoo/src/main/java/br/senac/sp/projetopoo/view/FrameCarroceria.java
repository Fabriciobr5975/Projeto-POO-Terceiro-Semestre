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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.senac.sp.projetopoo.dao.hibernate.CarroceriaDaoHibernate;
import br.senac.sp.projetopoo.dao.hibernate.EMFactory;
import br.senac.sp.projetopoo.dao.hibernate.InterfaceDao;
import br.senac.sp.projetopoo.modelo.Carroceria;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import br.senac.sp.projetopoo.tablemodel.CarroceriaTableModel;
import br.senac.sp.projetopoo.util.FrameUtil;
import jakarta.persistence.NoResultException;

/**
 * Classe do Frame do CRUD da Carroceria.
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public class FrameCarroceria extends JFrame {
	// Atributos da Carroceria
	private Carroceria carroceria;
	private InterfaceDao<Carroceria> dao;
	private List<Carroceria> carrocerias;

	// Atributos do Frame
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// Atributo do ID
	private JLabel lblId;
	private JTextField tfId;

	// Atributos da Busca
	private JLabel lblBuscar;
	private JTextField tfBusca;
	private JComboBox<String> cmbBusca;
	private JButton btnBuscar;

	// Atributo do painel de rolagem
	private JScrollPane scrollPane;

	// Atributo da tabela
	private JTable tbCarrocerias;

	// Atributo da tableModel
	private CarroceriaTableModel tableModel;

	// Atributos da Carroceria
	private JLabel lblCarrocerias;
	private JComboBox<CarroceriasVeiculo> cmbCarrocerias;

	// Botões
	private JButton btnSalvarCarroceria;
	private JButton btnLimparCampos;
	private JButton btnVoltarMenu;
	private JButton btnExcluirCarroceria;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameCarroceria frame = new FrameCarroceria();
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
	public FrameCarroceria() {
		// Instanciando o objeto dao para possibilitar o CRUD
		dao = new CarroceriaDaoHibernate(EMFactory.getEntityManager());

		try {
			// Pegando todas as categorias criadas e salvando em uma lista de categorias
			carrocerias = dao.listar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(FrameCarroceria.this, "Não foi possível abrir a listagem",
					"Erro ao Abrir a Lista", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		// Instanciando o tableModel para criar a tabela com todas as carrocerias
		// criadas
		tableModel = new CarroceriaTableModel(carrocerias);

		// Atributos do Frame
		setTitle("Categoria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Centralizando a tela
		FrameUtil.centralizarTela(this);

		// Label Buscar
		lblBuscar = new JLabel("Buscar:");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
		lblBuscar.setBounds(10, 134, 45, 30);
		contentPane.add(lblBuscar);

		// TextField Buscar
		tfBusca = new JTextField();
		tfBusca.setFont(new Font("Arial", Font.PLAIN, 12));
		tfBusca.setBounds(65, 135, 236, 30);
		contentPane.add(tfBusca);
		tfBusca.setColumns(10);

		// ComboBox Buscar
		cmbBusca = new JComboBox<String>();
		cmbBusca.addItem("ID");
		cmbBusca.addItem("Nome");
		cmbBusca.setFont(new Font("Arial", Font.BOLD, 12));
		cmbBusca.setBounds(311, 135, 71, 30);
		contentPane.add(cmbBusca);

		// Button Buscar
		btnBuscar = new JButton("Buscar");
		btnBuscar.setMnemonic('B');
		btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
		btnBuscar.setBounds(391, 135, 85, 30);
		btnBuscar.setBackground(Color.DARK_GRAY);
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setBorder(new LineBorder(Color.BLACK));

		// Adicionando evento para o botão de buscar
		btnBuscar.addActionListener(e -> {
			try {
				// Verificando se o campo não está vazia
				verificarSeCampoBuscaEstaVazia(tfBusca.getText().trim());

				if (cmbBusca.getSelectedItem().equals("ID")) {
					carroceria = dao.buscar(Integer.parseInt(tfBusca.getText().trim()));

					// Se a categoria for nula
					if (carroceria == null) {
						limparCampos();
					}

					// Mudando o id e o cmd para o selecionado pela busca
					tfId.setText("" + carroceria.getId());
					cmbCarrocerias.setSelectedItem(carroceria.getCarroceria());

					// Mudando o tem item selecionado na tabela
					for (int i = 0; i < tbCarrocerias.getRowCount(); i++) {
						/*
						 * Se o elemento na linha i com a coluna 1 for igual a carroceria que veio pela
						 * busca
						 */
						if (tableModel.getValueAt(i, 1) == carroceria.getCarroceria().getTipo()) {
							// Selecionando a linha referente a carroceria que veio pela busca
							tbCarrocerias.setRowSelectionInterval(i, i);
							break;
						}
					}

					JOptionPane.showMessageDialog(FrameCarroceria.this, "Busca realizada com sucesso",
							"Busca de Carroceria", JOptionPane.INFORMATION_MESSAGE);

				} else {
					/*
					 * Criando uma dao a partir da classe concreta, pois precisamos usar um método
					 * de busca presente somenta nesta classes, como a dao é criada a partir da
					 * interface não conseguimos utilizar os métodos criados dentro da classe
					 * concretas, somente os métodos criados e implementados pelas classes que
					 * realizaram um "contrato" com a interface
					 */
					CarroceriaDaoHibernate daoBusca = (CarroceriaDaoHibernate) dao;

					// Pegando o elemento que o usuário passou e verificando se ele é uma enum
					CarroceriasVeiculo carroceriaBusca = pegarDadoCampoBusca();

					// Verificando o elemento passado pelo usuário que é uma enum já foi criada
					verificarSeDadoBuscaFoiCriado(carroceriaBusca);

					// Salvando os Objetos que vieram da listage apartir do nome da carroceria
					carroceria = daoBusca.buscar(carroceriaBusca);

					// Se a categoria for nula
					if (carroceria == null) {
						limparCampos();
					}

					// Mudando o id e o cmd para o selecionado pela busca
					tfId.setText("" + carroceria.getId());
					cmbCarrocerias.setSelectedItem(carroceria.getCarroceria());

					// Mudando o tem item selecionado na tabela
					for (int i = 0; i < tbCarrocerias.getRowCount(); i++) {
						/*
						 * Se o elemento na linha i com a coluna 1 for igual a carroceria que veio pela
						 * busca
						 */
						if (tableModel.getValueAt(i, 1) == carroceria.getCarroceria().getTipo()) {
							// Selecionando a linha referente a carroceria que veio pela busca
							tbCarrocerias.setRowSelectionInterval(i, i);
							break;
						}
					}

					JOptionPane.showMessageDialog(FrameCarroceria.this, "Busca realizada com sucesso",
							"Busca de Carroceria", JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (NullPointerException | NumberFormatException e1) {
				if (e1 instanceof NullPointerException) {
					JOptionPane.showMessageDialog(FrameCarroceria.this,
							"A carroceria " + tfBusca.getText().trim() + " não foi encontrada", "Erro",
							JOptionPane.WARNING_MESSAGE);

				} else if (e1 instanceof NumberFormatException) {
					JOptionPane.showMessageDialog(FrameCarroceria.this,
							"A busca por ID só aceita valores númericos, por favor digite apenas valores númericos",
							"Erro", JOptionPane.WARNING_MESSAGE);
				}

			} catch (Exception e1) {
				if (e1 instanceof IllegalArgumentException) {
					JOptionPane.showMessageDialog(FrameCarroceria.this, e1.getMessage(), "Erro",
							JOptionPane.WARNING_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(FrameCarroceria.this, "Erro: " + e1.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

				e1.printStackTrace();
			}
		});
		contentPane.add(btnBuscar);

		// Label ID
		lblId = new JLabel("ID:");
		lblId.setFont(new Font("Arial", Font.BOLD, 12));
		lblId.setBounds(10, 10, 15, 30);
		contentPane.add(lblId);

		// TextField ID
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setFont(new Font("Arial", Font.PLAIN, 12));
		tfId.setBounds(32, 10, 55, 30);
		contentPane.add(tfId);
		tfId.setColumns(10);

		// Lavel Categoria
		lblCarrocerias = new JLabel("Carroceria:");
		lblCarrocerias.setFont(new Font("Arial", Font.BOLD, 12));
		lblCarrocerias.setBounds(97, 10, 71, 30);
		contentPane.add(lblCarrocerias);

		// ComboBox Enum CategoriasVeiculo
		cmbCarrocerias = new JComboBox<CarroceriasVeiculo>(CarroceriasVeiculo.values());
		cmbCarrocerias.setFont(new Font("Arial", Font.PLAIN, 12));
		cmbCarrocerias.setBounds(175, 10, 207, 30);
		contentPane.add(cmbCarrocerias);

		// Button Salvar categorias
		btnSalvarCarroceria = new JButton("Salvar");
		btnSalvarCarroceria.setMnemonic('S');
		btnSalvarCarroceria.setFont(new Font("Arial", Font.BOLD, 12));
		btnSalvarCarroceria.setBounds(391, 10, 85, 30);
		btnSalvarCarroceria.setBackground(Color.DARK_GRAY);
		btnSalvarCarroceria.setForeground(Color.WHITE);
		btnSalvarCarroceria.setBorder(new LineBorder(Color.BLACK));

		// Adicionando o evento para o botão de salvar categorias
		btnSalvarCarroceria.addActionListener(e -> {
			// Se os campos passarem na validação
			if (validarCarroceriasSelecionado()) {
				// Se a categoria for igual a nula
				if (carroceria == null) {
					// Instanciando um Objeto da Carroceria
					carroceria = new Carroceria();
				}

				// Pegando o elemento selecionado na comboBox e colocando no objeto
				carroceria.setCarroceria((CarroceriasVeiculo) cmbCarrocerias.getSelectedItem());
				// Resultado do persistencia dos dados;
				int resultado;

				try {
					// Se o ID for igual a zero então deve inserir uma carroceria nova
					if (carroceria.getId() == 0) {
						resultado = dao.inserir(carroceria);

						// Se o dado for persistido
						if (resultado == 1) {
							JOptionPane.showMessageDialog(FrameCarroceria.this, "Categoria salva com sucesso",
									"Cadastrar Carroceria", JOptionPane.INFORMATION_MESSAGE);

							// Se o dado não for persistido
						} else {
							JOptionPane.showMessageDialog(FrameCarroceria.this,
									"Não foi possível cadastrar a carroceria, verifique se a Categoria já está cadastrada ou se ainda não foi cadastrada",
									"Cadastrar Carroceria", JOptionPane.WARNING_MESSAGE);
						}

						// Se o ID não for igual a zero então devera alterar a carroceria atual
					} else {
						resultado = dao.alterar(carroceria);

						// Se o dado for persistido
						if (resultado == 1) {
							JOptionPane.showMessageDialog(FrameCarroceria.this, "A Categoria foi alterada com sucesso",
									"Alterar Carroceria", JOptionPane.INFORMATION_MESSAGE);

							// Se o dado não for persistido
						} else {
							JOptionPane.showMessageDialog(FrameCarroceria.this, "A Categoria não foi alterada",
									"Alterar Carroceria", JOptionPane.WARNING_MESSAGE);
						}
					}
					// Listando as carrocerias;
					carrocerias = dao.listar();
					// Mudando a lista que será usada para exibir os dados na tela
					tableModel.setLista(carrocerias);
					// Para atualizar os dados dentro da tabela
					tableModel.fireTableDataChanged();

					// Limpar os campos
					limparCampos();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(FrameCarroceria.this, "Erro: " + e1.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(FrameCarroceria.this,
						"Nenhuma opção selecionada para o campo da categoria", "Mensagem do Sistema",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		contentPane.add(btnSalvarCarroceria);

		// Button Limpar Campos
		btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.setMnemonic('L');
		btnLimparCampos.setFont(new Font("Arial", Font.BOLD, 12));
		btnLimparCampos.setBounds(10, 67, 137, 30);
		btnLimparCampos.setBackground(Color.DARK_GRAY);
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setBorder(new LineBorder(Color.BLACK));

		// Adicionando o evento para o botão de limpar os campos
		btnLimparCampos.addActionListener(e -> {
			limparCampos();
		});
		contentPane.add(btnLimparCampos);

		// Button Voltar Menu
		btnVoltarMenu = new JButton("Voltar para o Menu");
		btnVoltarMenu.setMnemonic('M');
		btnVoltarMenu.setFont(new Font("Arial", Font.BOLD, 12));
		btnVoltarMenu.setBounds(337, 67, 139, 30);
		btnVoltarMenu.setBackground(Color.DARK_GRAY);
		btnVoltarMenu.setForeground(Color.WHITE);
		btnVoltarMenu.setBorder(new LineBorder(Color.BLACK));

		// Adicionando o evento para o botão de voltar para o menu
		btnVoltarMenu.addActionListener(e -> {
			new FrameInicial();
			dispose();
		});
		contentPane.add(btnVoltarMenu);

		// Button Excluir Carroceria
		btnExcluirCarroceria = new JButton("Excluir Carroceria");
		btnExcluirCarroceria.setMnemonic('E');
		btnExcluirCarroceria.setFont(new Font("Arial", Font.BOLD, 12));
		btnExcluirCarroceria.setBounds(175, 67, 139, 30);
		btnExcluirCarroceria.setBackground(Color.DARK_GRAY);
		btnExcluirCarroceria.setForeground(Color.WHITE);
		btnExcluirCarroceria.setBorder(new LineBorder(Color.BLACK));

		// Adicionando o evento para excluir a carroceria
		btnExcluirCarroceria.addActionListener(e -> {
			// Se os campos passarem na validação
			if (validarCarroceriasSelecionado()) {
				// Se a carroceria não for nula
				if (carroceria != null) {
					// Perguntado ao usuário se ele realmente deseja excluir essa carroceria
					if (JOptionPane.showConfirmDialog(FrameCarroceria.this,
							"Deseja excluir a Carroceria " + carroceria.getCarroceria().getTipo(), "Excluir Carroceria",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						try {
							// Pegando o resultado da persistencia
							int resultado = dao.excluir(carroceria.getId());

							// Se os dados forem persistidos
							if (resultado == 1) {
								JOptionPane.showMessageDialog(FrameCarroceria.this,
										"Carroceria foi excluída com sucesso", "Excluir Carroceria",
										JOptionPane.INFORMATION_MESSAGE);

								// Se os dados não forem persistidos
							} else {
								JOptionPane.showMessageDialog(FrameCarroceria.this, "A Carroceria não foi excluída",
										"Cadastrar Carroceria", JOptionPane.WARNING_MESSAGE);
							}

							// Listando as carrocerias;
							carrocerias = dao.listar();
							// Mudando a lista que será usada para exibir os dados na tela
							tableModel.setLista(carrocerias);
							// Para atualizar os dados dentro da tabela
							tableModel.fireTableDataChanged();

							// Limpar os campos
							limparCampos();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(FrameCarroceria.this,
									"Erro ao excluir a carroceria. Erro: " + e1.getMessage(), "Mensagem do Sistema",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(FrameCarroceria.this, "Busque uma carroceria antes",
							"Mensagem do Sistema", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(FrameCarroceria.this,
						"Nenhuma opção selecionada para o campo da carroceria", "Mensagem do Sistema",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		contentPane.add(btnExcluirCarroceria);

		// Criando um scrollPane, para que a tabela tenha uma rolagem
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 175, 466, 283);
		contentPane.add(scrollPane);

		// Tabela das Carrocerias
		tbCarrocerias = new JTable(tableModel);
		tbCarrocerias.setToolTipText("Selecione um item para alterar ou excluir");
		tbCarrocerias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Adicionando um evento para a tabela
		tbCarrocerias.getSelectionModel().addListSelectionListener(e -> {
			// Pegando a linha selecionado
			int linha = tbCarrocerias.getSelectedRow();

			// Se a linha selecionada for maior que 0
			if (linha >= 0) {
				// Adicionado os elementos na carroceria através da linha selecionada
				carroceria = carrocerias.get(linha);
				// Trocando os textos do ID e o elemento selecionado na comboBox
				tfId.setText("" + carroceria.getId());
				cmbCarrocerias.setSelectedItem(carroceria.getCarroceria());
			}
		});

		scrollPane.setViewportView(tbCarrocerias);

		setResizable(false);
		setVisible(true);
	}

	/**
	 * Métodos para validar os campos;
	 * 
	 * @return Retorna verdadeiro se os campos passarem pela validação, retorna
	 *         falso se eles não passarem pela validação
	 */
	private boolean validarCarroceriasSelecionado() {
		if (cmbCarrocerias == null || cmbCarrocerias.getSelectedItem() == null) {
			return false;
		}
		return true;
	}

	/**
	 * Método para limpar os campos;
	 * 
	 */
	private void limparCampos() {
		tfId.setText("");
		carroceria = null;
		cmbCarrocerias.setSelectedIndex(0);
	}

	/**
	 * Método para pegar o dado passado pelo usuário na busca
	 * 
	 * @return - Retorna a enum referente ao elemento que foi passado na busca
	 * 
	 * @throws NullPointerException - Caso o elemento passado na busca não
	 *                              corresponda a nenhuma enum
	 */
	private CarroceriasVeiculo pegarDadoCampoBusca() throws NullPointerException {
		// Cria um atributo da enum para pegar o elemento passado na busca
		CarroceriasVeiculo carroceriaBusca = null;

		// Verificando se o elemento corresponde a alguma enum
		for (CarroceriasVeiculo i : CarroceriasVeiculo.values()) {
			if (i.getTipo().equals(tfBusca.getText().trim())) {
				carroceriaBusca = i;
				break;
			}
		}

		// Se o elemento não corresponder a uma enum retorna uma NullPointerException
		if (carroceriaBusca == null) {
			throw new NullPointerException(
					"A carroceria " + tfBusca.getText().trim() + " não existe, por favor insira um carroceria válida");
		}

		return carroceriaBusca;
	}

	/**
	 * Método para verificar a o dado passado para a busca foi criado e está na
	 * lista das Carrocerias criadas
	 * 
	 * @param carroceriaBusca - Recebe o dado da busca, passado pelo usuário
	 * 
	 * @throws NoResultException - Caso esse dado não exista na lista, ou seja ainda
	 *                           não foi criada
	 */
	private void verificarSeDadoBuscaFoiCriado(CarroceriasVeiculo carroceriaBusca) throws NoResultException {
		// Variável para verificar se o elemento já foi criado
		int resultado = 0;

		// Verificando se o elemento já foi criado
		for (Carroceria i : carrocerias) {
			if (!i.getCarroceria().equals(carroceriaBusca)) {
				continue;
			} else {
				resultado = 1;
				break;
			}
		}

		// Se o resultado for igual a zero, significa que a carroceria passada pelo
		// usuário ainda não foi criada
		if (resultado == 0) {
			throw new NoResultException(
					"A busca não pode ser realizada, pois a carroceria " + carroceriaBusca + " ainda não foi criada");
		}
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
}
