package br.senac.sp.projetopoo.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import br.senac.sp.projetopoo.dao.hibernate.CarroceriaDaoHibernate;
import br.senac.sp.projetopoo.dao.hibernate.EMFactory;
import br.senac.sp.projetopoo.dao.hibernate.InterfaceDao;
import br.senac.sp.projetopoo.dao.hibernate.MarcaDaoHibernate;
import br.senac.sp.projetopoo.dao.hibernate.VeiculoDaoHibernate;
import br.senac.sp.projetopoo.modelo.Carroceria;
import br.senac.sp.projetopoo.modelo.Marca;
import br.senac.sp.projetopoo.modelo.Veiculo;
import br.senac.sp.projetopoo.modelo.enums.CambioVeiculo;
import br.senac.sp.projetopoo.modelo.enums.DirecaoVeiculo;
import br.senac.sp.projetopoo.util.FrameUtil;
import jakarta.persistence.NoResultException;

/**
 * Classe do Frame do CRUD do Veiculo.
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public class FrameManipulacaoVeiculo extends JFrame {
	// Atributos do Veiculo
	private Veiculo veiculo;
	private InterfaceDao<Veiculo> daoVeiculo;
	private InterfaceDao<Marca> daoMarca;
	private InterfaceDao<Carroceria> daoCarroceria;
	private List<Marca> marcas;
	private List<Carroceria> carrocerias;
	private LocalDate dataFabricacao;

	// Atributos para a manipulação da imagem da marca
	private JFileChooser chooser;
	private FileFilter imageFilter;
	private File selecionado;

	// Atributos do Frame
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// Atributos para a Busca
	private JLabel lblBuscar;
	private JTextField tfBuscar;
	private JComboBox<String> cmbFiltroBusca;
	private JButton btnBuscar;

	// Atributos do ID
	private JLabel lblId;
	private JTextField tfId;

	// Atributo da Imagem
	private JLabel lblImagemVeiculo;

	// Atributos do Nome
	private JLabel lblNome;
	private JTextField tfNome;

	// Atributos do Preço
	private JLabel lblPreco;
	private JFormattedTextField ftfPreco;
	private MaskFormatter formatadorPreco;

	// Atributos da Fabricação
	private JLabel lblFabricacao;
	private JFormattedTextField ftfFabricacao;
	private MaskFormatter formatadorFabricacao;

	// Atributos do Câmbio
	private JLabel lblCambio;
	private JComboBox<CambioVeiculo> cmbCambio;

	// Atributos do Campo de Informações
	private JTextArea taInformacoes;
	private JLabel lblInformacoes;

	// Atributos da Direção
	private JLabel lblDirecao;
	private JComboBox<DirecaoVeiculo> cmbDirecao;

	// Atributos da Marca
	private JLabel lblMarca;
	private JComboBox<Marca> cmbMarca;

	// Atributos da Categoria
	private JLabel lblCarroceria;
	private JComboBox<Carroceria> cmbCarroceria;

	// Atributo se o veículo é ou não Importado
	private JCheckBox ckbImportado;

	// Botões
	private JButton btnSalvarVeiculo;
	private JButton btnAbriTelaListagem;
	private JButton btnLimparCampos;
	private JButton btnVoltarMenu;
	private JButton btnExcluirVeiculo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameManipulacaoVeiculo frame = new FrameManipulacaoVeiculo();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameManipulacaoVeiculo() {
		// Instanciando os objetos da dao para possibilitar o CRUD dos Objetos
		daoVeiculo = new VeiculoDaoHibernate(EMFactory.getEntityManager());
		daoMarca = new MarcaDaoHibernate(EMFactory.getEntityManager());
		daoCarroceria = new CarroceriaDaoHibernate(EMFactory.getEntityManager());

		try {
			// Pegando todas as marcas criadas e salvando em uma lista de marcas
			marcas = daoMarca.listar();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "Erro ao carregar as marcas", "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		try {
			// Pegando todas as categorias criadas e salvando em uma lista de categorias
			carrocerias = daoCarroceria.listar();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "Erro ao carregar as marcas", "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		// Instanciando o chooser para abrir a tela de seleção de arquivos
		chooser = new JFileChooser();
		// Filtrando o tipo de arquivo que aparece quando o chooser for ativado
		imageFilter = new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes());

		// Atributos do Frame
		setTitle("Veículo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Centralizando a tela
		FrameUtil.centralizarTela(this);

		setContentPane(contentPane);

		// Label Buscar
		lblBuscar = new JLabel("Buscar:");
		lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
		lblBuscar.setBounds(10, 10, 54, 30);
		contentPane.add(lblBuscar);

		// TextField Buscar
		tfBuscar = new JTextField();
		tfBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
		tfBuscar.setBounds(74, 10, 497, 30);
		contentPane.add(tfBuscar);
		tfBuscar.setColumns(10);

		// ComboBox tipo Busca
		cmbFiltroBusca = new JComboBox<String>();
		cmbFiltroBusca.addItem("Nome");
		cmbFiltroBusca.addItem("ID");
		cmbFiltroBusca.setFont(new Font("Arial", Font.BOLD, 12));
		cmbFiltroBusca.setBounds(581, 10, 85, 30);
		contentPane.add(cmbFiltroBusca);

		// Button Buscar
		btnBuscar = new JButton("Buscar");
		btnBuscar.setMnemonic('B');
		btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
		btnBuscar.setBounds(678, 10, 98, 30);
		btnBuscar.setBorder(new LineBorder(Color.BLACK));
		btnBuscar.setBackground(Color.DARK_GRAY);
		btnBuscar.setForeground(Color.WHITE);

		contentPane.add(btnBuscar);

		// Adicionando evento ao botão de buscar
		btnBuscar.addActionListener(e -> {
			// Se o ComboxBox estiver selecionado o nome
			if (cmbFiltroBusca.getSelectedItem().equals("Nome")) {
				try {
					// Verificando se o campo não está vazia
					verificarSeCampoBuscaEstaVazia(tfBuscar.getText().trim());

					/*
					 * Criando uma dao a partir da classe concreta, pois precisamos usar um método
					 * de busca presente somenta nesta classes, como a dao é criada a partir da
					 * interface não conseguimos utilizar os métodos criados dentro da classe
					 * concretas, somente os métodos criados e implementados pelas classes que
					 * realizaram um "contrato" com a interface
					 */
					VeiculoDaoHibernate daoBuscaVeiculo = (VeiculoDaoHibernate) daoVeiculo;

					// Buscando um veículo pelo nome
					veiculo = daoBuscaVeiculo.buscar(tfBuscar.getText().trim());

					// Se o veiculo for nulo
					if (veiculo == null) {
						limparCampos();
					}

					// Adicionando os elementos da busca nos campo
					adicionarElementosNosCampos();

				} catch (Exception e1) {
					if (e1 instanceof NoResultException) {
						JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
								"O Veículo " + tfBuscar.getText().trim() + " não foi encontrado", "Erro",
								JOptionPane.WARNING_MESSAGE);

					} else if (e1 instanceof IllegalArgumentException) {
						JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, e1.getMessage(), "Erro",
								JOptionPane.WARNING_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
								"Ocorreu um erro ao buscar o veículo", "Mensagem do Sistema",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}

				// Se o ComboBox não estiver selecionando o nome
			} else {
				try {
					// Verificando se o campo não está vazia
					verificarSeCampoBuscaEstaVazia(tfBuscar.getText().trim());

					// Buscando um veículo pelo ID
					veiculo = daoVeiculo.buscar(Integer.parseInt(tfBuscar.getText().trim()));

					// Se o veiculo for nulo
					if (veiculo == null) {
						limparCampos();
					}

					// Adicionando os elementos da busca nos campo
					adicionarElementosNosCampos();
				} catch (NullPointerException | NumberFormatException e1) {
					if (e1 instanceof NullPointerException) {
						JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
								"O Veículo com ID " + tfBuscar.getText().trim() + " não foi encontrado", "Erro",
								JOptionPane.WARNING_MESSAGE);

					} else if (e1 instanceof NumberFormatException) {
						JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
								"Digite apenas números inteiros na busca pelo ID", "Mensagem do Sistema",
								JOptionPane.WARNING_MESSAGE);
					}

				} catch (Exception e1) {
					if (e1 instanceof IllegalArgumentException) {
						JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, e1.getMessage(), "Erro",
								JOptionPane.WARNING_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
								"Ocorreu um erro ao buscar o veículo", "Mensagem do Sistema",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
		});

		// Label ID
		lblId = new JLabel("ID:");
		lblId.setFont(new Font("Arial", Font.BOLD, 12));
		lblId.setBounds(222, 76, 21, 30);
		contentPane.add(lblId);

		// TextField ID
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setFont(new Font("Arial", Font.PLAIN, 12));
		tfId.setBounds(253, 76, 90, 30);
		contentPane.add(tfId);
		tfId.setColumns(10);

		// Label Imagem do Veículo
		lblImagemVeiculo = new JLabel("");
		lblImagemVeiculo.setToolTipText("Clique duas vezes para colocar a imagem");
		lblImagemVeiculo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 9));
		lblImagemVeiculo.setOpaque(true);
		lblImagemVeiculo.setBounds(10, 76, 190, 190);
		lblImagemVeiculo.setBackground(Color.LIGHT_GRAY);
		lblImagemVeiculo.setForeground(Color.BLUE);

		// Adicionando evento para a imagem
		lblImagemVeiculo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Se a quantidade de clicks for igual a 2
				if (e.getClickCount() == 2) {
					// Adicionando o filtro ao chooser
					chooser.setFileFilter(imageFilter);
					String diretorioChooser = System.getProperty("user.home") + "/Downloads";
					chooser.setCurrentDirectory(new File(diretorioChooser));

					// Abrindo e pegando o imagem do chooser
					if (chooser.showOpenDialog(FrameManipulacaoVeiculo.this) == JFileChooser.APPROVE_OPTION) {
						selecionado = chooser.getSelectedFile();

						try {
							/*
							 * Lê a imagem que foi pega pelo selecionado e a converte em um BufferedImage
							 * (Imagem carregada na memória)
							 */
							BufferedImage bufferedImage = ImageIO.read(selecionado);

							/*
							 * Redimensiona o bufImg (BufferedImage) para que a imagem fique na proporção
							 * certa dentro da lblLogo
							 */
							Image imagem = bufferedImage.getScaledInstance(lblImagemVeiculo.getWidth(),
									lblImagemVeiculo.getHeight(), Image.SCALE_SMOOTH);

							/*
							 * Criando uma icon para colocar na label, pegando a imagem que foi
							 * redimensionada
							 */
							ImageIcon icon = new ImageIcon(imagem);

							// Adicionado imgLabel na lblLogo
							lblImagemVeiculo.setIcon(icon);

						} catch (IOException e1) {
							JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
									"Não foi possível adicionar a imagem", "Erro ao adicionar a imagem",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
				}
			}
		});

		contentPane.add(lblImagemVeiculo);

		// Label Nome
		lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 12));
		lblNome.setBounds(365, 76, 45, 30);
		contentPane.add(lblNome);

		// TextField Nome
		tfNome = new JTextField();
		tfNome.setFont(new Font("Arial", Font.PLAIN, 12));
		tfNome.setColumns(10);
		tfNome.setBounds(420, 76, 356, 30);
		contentPane.add(tfNome);

		// Label Preco
		lblPreco = new JLabel("Preço:");
		lblPreco.setFont(new Font("Arial", Font.BOLD, 12));
		lblPreco.setBounds(222, 129, 45, 30);
		contentPane.add(lblPreco);

		// Colocando uma MaskFormatter para o campo Preco que é um JFormattedTextField
		try {
			formatadorPreco = new MaskFormatter("R$ #.###.###,##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Adicionado o número zero nos lugares de entrada de dados
		formatadorPreco.setPlaceholderCharacter('0');

		// FormattedTextField Preco
		ftfPreco = new JFormattedTextField(formatadorPreco);
		ftfPreco.setToolTipText("O preço máximo que é possível atribuir a um veículo é R$ 9.999.99,99");
		ftfPreco.setFont(new Font("Arial", Font.PLAIN, 12));
		ftfPreco.setColumns(10);
		ftfPreco.setBounds(277, 129, 106, 30);
		contentPane.add(ftfPreco);

		// Label Fabricação
		lblFabricacao = new JLabel("Fabricação:");
		lblFabricacao.setFont(new Font("Arial", Font.BOLD, 12));
		lblFabricacao.setBounds(393, 129, 72, 30);
		contentPane.add(lblFabricacao);

		// Colocando uma MaskFormatter para o campo Fabricação que é um
		// JFormattedTextField
		try {
			formatadorFabricacao = new MaskFormatter(" ##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Adicionado underline nos lugares de entrada de dados
		formatadorFabricacao.setPlaceholderCharacter('_');

		// FormattedTextField Fabricação
		ftfFabricacao = new JFormattedTextField(formatadorFabricacao);
		ftfFabricacao.setFont(new Font("Arial", Font.PLAIN, 12));
		ftfFabricacao.setBounds(475, 129, 72, 30);
		contentPane.add(ftfFabricacao);

		// Label Câmbio
		lblCambio = new JLabel("Câmbio:");
		lblCambio.setFont(new Font("Arial", Font.BOLD, 12));
		lblCambio.setBounds(557, 129, 54, 30);
		contentPane.add(lblCambio);

		// ComboBox Enum Câmbio
		cmbCambio = new JComboBox<CambioVeiculo>(CambioVeiculo.values());
		cmbCambio.setFont(new Font("Arial", Font.PLAIN, 12));
		cmbCambio.setBounds(617, 129, 159, 30);
		contentPane.add(cmbCambio);

		// Label Direção
		lblDirecao = new JLabel("Direção:");
		lblDirecao.setFont(new Font("Arial", Font.BOLD, 12));
		lblDirecao.setBounds(222, 184, 60, 30);
		contentPane.add(lblDirecao);

		// ComboBox Enum Direção
		cmbDirecao = new JComboBox<DirecaoVeiculo>(DirecaoVeiculo.values());
		cmbDirecao.setFont(new Font("Arial", Font.PLAIN, 12));
		cmbDirecao.setBounds(281, 184, 165, 30);
		contentPane.add(cmbDirecao);

		// Label Marca
		lblMarca = new JLabel("Marca:");
		lblMarca.setFont(new Font("Arial", Font.BOLD, 12));
		lblMarca.setBounds(222, 236, 45, 30);
		contentPane.add(lblMarca);

		// Adicionando a ComboBox para as marcas
		cmbMarca = new JComboBox(marcas.toArray());
		cmbMarca.setFont(new Font("Arial", Font.PLAIN, 12));
		cmbMarca.setBounds(277, 236, 334, 30);
		cmbMarca.setToolTipText("Selecione a Marca do veículo");
		contentPane.add(cmbMarca);

		// Label Categoria
		lblCarroceria = new JLabel("Carroceria:");
		lblCarroceria.setFont(new Font("Arial", Font.BOLD, 12));
		lblCarroceria.setBounds(465, 184, 72, 30);
		contentPane.add(lblCarroceria);

		// Adicionando a ComboBox para as categorias
		cmbCarroceria = new JComboBox(carrocerias.toArray());
		cmbCarroceria.setFont(new Font("Arial", Font.PLAIN, 12));
		cmbCarroceria.setBounds(538, 184, 238, 30);
		cmbCarroceria.setToolTipText("Selecione a carroceria do veículo");
		contentPane.add(cmbCarroceria);

		// CheckBox Veículo Importado
		ckbImportado = new JCheckBox("O veículo é importado");
		ckbImportado.setFont(new Font("Arial", Font.BOLD, 12));
		ckbImportado.setBounds(625, 236, 151, 30);
		ckbImportado.setToolTipText("Se o veículo for importado clique na caixinha");

		contentPane.add(ckbImportado);

		// Label Informações
		lblInformacoes = new JLabel("Informações Adicionais:");
		lblInformacoes.setFont(new Font("Arial", Font.BOLD, 12));
		lblInformacoes.setBounds(10, 276, 151, 30);

		contentPane.add(lblInformacoes);

		// TextArea Informações do Véiculo
		taInformacoes = new JTextArea();
		taInformacoes.setToolTipText("Insira informações mais detalhadas do veículo aqui");
		taInformacoes.setLineWrap(true);
		taInformacoes.setFont(new Font("Arial", Font.PLAIN, 12));
		taInformacoes.setBounds(10, 316, 766, 124);
		taInformacoes.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(taInformacoes);

		// Button Salvar Veículo
		btnSalvarVeiculo = new JButton("Salvar");
		btnSalvarVeiculo.setMnemonic('S');
		btnSalvarVeiculo.setFont(new Font("Arial", Font.BOLD, 12));
		btnSalvarVeiculo.setBounds(10, 473, 144, 30);
		btnSalvarVeiculo.setBorder(new LineBorder(Color.BLACK));
		btnSalvarVeiculo.setBackground(Color.DARK_GRAY);
		btnSalvarVeiculo.setForeground(Color.WHITE);

		// Adicionando evento ao botão de salvar
		btnSalvarVeiculo.addActionListener(e -> {
			// Variável para pegar o preço que será formatado
			double preco = 0;
			// Resultado da persistencia
			int resultado = 0;

			try {
				// Validando os campos
				validarCampos();

				// Pegando a data de fabricação
				pegarDataFabricacao();

				// Se o veículo for igual a nulo
				if (veiculo == null) {
					// Criando um novo objeto do tipo Veiculo
					veiculo = new Veiculo();
				}
				// Pegando o preço
				preco = pegarPreco();

				// Se o preço for igual a zero
				if (preco == 0) {
					JOptionPane.showMessageDialog(null, "Erro ao salvar o preço, por favor tente novamente", "Erro",
							JOptionPane.ERROR_MESSAGE);

				} else {
					// Atribuindo os dados do campos, o preço e a data de fabricação ao objeto
					// veiculo
					veiculo.setNome(tfNome.getText());
					veiculo.setPreco(preco);
					veiculo.setCambio((CambioVeiculo) cmbCambio.getSelectedItem());
					veiculo.setDirecao((DirecaoVeiculo) cmbDirecao.getSelectedItem());
					veiculo.setCarroceria((Carroceria) cmbCarroceria.getSelectedItem());
					veiculo.setMarca((Marca) cmbMarca.getSelectedItem());
					veiculo.setFabricacao(dataFabricacao);
					veiculo.setImportacao(ckbImportado.isSelected());
					veiculo.setInformacoes(taInformacoes.getText());

					// Se o file da imagem não for nulo
					if (selecionado != null) {
						// Convertento a imagem em um vetor de bytes
						byte[] imagemBytes = Files.readAllBytes(selecionado.toPath());
						// Mudando a logo da marca
						veiculo.setImagemVeiculo(imagemBytes);
					}
					// Se o id for igual a 0, ou seja, se não existir veículo salvo no sistema
					if (veiculo.getId() == 0) {
						// Criando no banco uma nova marca
						resultado = daoVeiculo.inserir(veiculo);

						// Se foi feita a persistencia dos dados no banco de dados
						if (resultado == 1) {
							JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "Veículo salvo com sucesso",
									"Cadastrar Veículo", JOptionPane.INFORMATION_MESSAGE);

							// Limpar os campos
							limparCampos();

							// Se os dados não foram persistidos
						} else {
							JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
									"Não foi possível cadastrar o veículo, verifique se a Veículo já está cadastrado ou se ainda não foi cadastrado",
									"Cadastrar Veículo", JOptionPane.WARNING_MESSAGE);
						}
						// Caso o ID não seja igual a zero, então deverá alterar o veículo atual
					} else {
						// Alterando os dados de algum veículo
						resultado = daoVeiculo.alterar(veiculo);

						// Se foi feita a persistencia dos dados no banco de dados
						if (resultado == 1) {
							JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
									"O Veículo foi alterada com sucesso", "Alterar Veículo",
									JOptionPane.INFORMATION_MESSAGE);

							// Limpar os campos
							limparCampos();

							// Se os dados não foram persistidos
						} else {
							JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "O Veículo não foi alterado",
									"Alterar Veículo", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "Erro: " + e1.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		});

		contentPane.add(btnSalvarVeiculo);

		// Botão Abrir tela de listagem
		btnAbriTelaListagem = new JButton("Listagem de Veículos");
		btnAbriTelaListagem.setMnemonic('V');
		btnAbriTelaListagem.setFont(new Font("Arial", Font.BOLD, 12));
		btnAbriTelaListagem.setBounds(318, 473, 144, 30);
		btnAbriTelaListagem.setBorder(new LineBorder(Color.BLACK));
		btnAbriTelaListagem.setBackground(Color.DARK_GRAY);
		btnAbriTelaListagem.setForeground(Color.WHITE);

		// Adicionando evento ao botão de abrir a tela de listagem
		btnAbriTelaListagem.addActionListener(e -> {
			new FrameListagemVeiculo();
			dispose();
		});
		contentPane.add(btnAbriTelaListagem);

		// Button Limpar Campos
		btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.setMnemonic('L');
		btnLimparCampos.setFont(new Font("Arial", Font.BOLD, 12));
		btnLimparCampos.setBounds(472, 473, 147, 30);
		btnLimparCampos.setBorder(new LineBorder(Color.BLACK));
		btnLimparCampos.setBackground(Color.DARK_GRAY);
		btnLimparCampos.setForeground(Color.WHITE);

		// Adicionando evento ao botão de limpar os campos
		btnLimparCampos.addActionListener(e -> {
			limparCampos();
		});

		contentPane.add(btnLimparCampos);

		// Button de voltar para o Menu
		btnVoltarMenu = new JButton("Voltar para o Menu");
		btnVoltarMenu.setMnemonic('M');
		btnVoltarMenu.setFont(new Font("Arial", Font.BOLD, 12));
		btnVoltarMenu.setBounds(629, 473, 147, 30);
		btnVoltarMenu.setBorder(new LineBorder(Color.BLACK));
		btnVoltarMenu.setBackground(Color.DARK_GRAY);
		btnVoltarMenu.setForeground(Color.WHITE);

		// Adicionado evento para o botão de voltar ao menu
		btnVoltarMenu.addActionListener(e -> {
			new FrameInicial();
			dispose();
		});

		contentPane.add(btnVoltarMenu);

		// Button Excluir Veículo
		btnExcluirVeiculo = new JButton("Excluir Veículo");
		btnExcluirVeiculo.setMnemonic('E');
		btnExcluirVeiculo.setForeground(Color.WHITE);
		btnExcluirVeiculo.setFont(new Font("Arial", Font.BOLD, 12));
		btnExcluirVeiculo.setBorder(new LineBorder(Color.BLACK));
		btnExcluirVeiculo.setBackground(Color.DARK_GRAY);
		btnExcluirVeiculo.setBounds(164, 473, 144, 30);

		// Adicionando evento ao botão de excluir o veículo
		btnExcluirVeiculo.addActionListener(e -> {
			// Se o veículo não for null
			if (veiculo != null) {
				// Peguntando se ele realmente deseja excluir esse veículo
				if (JOptionPane.showConfirmDialog(FrameManipulacaoVeiculo.this,
						"Deseja excluir o veículo " + veiculo.getNome(), "Excluir Veículo", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					try {
						// Chamando o método de excluir uma marca
						int resultado = daoVeiculo.excluir(veiculo.getId());

						// Se foi feita a persistencia dos dados no banco de dados
						if (resultado == 1) {
							JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
									"O Veículo foi excluído com sucesso", "Excluir Veículo",
									JOptionPane.INFORMATION_MESSAGE);

							// Se os dados não foram persistidos
						} else {
							JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "O Veículo não foi excluído",
									"Excluir Veículo", JOptionPane.WARNING_MESSAGE);
						}

						// Limpando os campos;
						limparCampos();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "Erro ao excluir o veículo", "Erro",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
				// Caso o veículo seja null
			} else {
				JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "Selecione uma veículo para excluí-lo");
			}
		});

		contentPane.add(btnExcluirVeiculo);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Método para adicionar todos os dados da busca do veículo nos campos, assim o
	 * usuário pode visualizar os dados ou até mesmo alterar algo se for necessário
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	private void adicionarElementosNosCampos() throws Exception {
		tfId.setText("" + veiculo.getId());
		tfNome.setText(veiculo.getNome());
		ftfPreco.setText("" + veiculo.getPreco());
		ftfFabricacao.setText("" + veiculo.getFabricacao());
		cmbCambio.setSelectedItem(veiculo.getCambio());
		cmbDirecao.setSelectedItem(veiculo.getDirecao());
		cmbCarroceria.setSelectedItem(veiculo.getCarroceria());
		cmbMarca.setSelectedItem(veiculo.getMarca());
		ckbImportado.setSelected(veiculo.isImportacao());
		taInformacoes.setText(veiculo.getInformacoes());

		// Métodos para formatar a data, preço e imagem
		formatarDataFabricacaoBusca();
		formatarPrecoBusca();
		colocarImagemNoCampo();
	}

	/**
	 * Método para formatar a data que vem da busca do veículo
	 * 
	 * @throws Exception - Caso haja exceções
	 */
	private void formatarDataFabricacaoBusca() throws Exception {
		// Pegando a data que veio da busca
		String data = veiculo.getFabricacao().toString().replaceAll("[-]", "");

		// Pegando as datas
		String dia = data.substring(6, 8);
		String mes = data.substring(4, 6);
		String ano = data.substring(0, 4);

		// Colando as datas no campo de data
		ftfFabricacao.setText(dia + mes + ano);
	}

	/**
	 * Método para formatar o preço que vem da busca do veículo
	 * 
	 * @throws Exception - Caso haja exceções
	 */
	private void formatarPrecoBusca() throws Exception {
		// Variável para pegar o preço
		String preco = Double.toString(veiculo.getPreco()).replaceAll("[.]", "");
		String precoFormatadoCampo = "";

		/*
		 * Adicionando os zeros na frente dos números existentes, pois se não os números
		 * podem ocupar posições diferentes, então antes adicionamos os zeros na frente
		 * para os números ocuparem a mesma posição que foram cadastrados
		 */
		while (precoFormatadoCampo.length() != (8 - preco.length())) {
			precoFormatadoCampo += "0";
		}

		// Adicionando o preço ao preço formatado
		precoFormatadoCampo += preco;

		// Colocando no campo de preço o preço formatado
		ftfPreco.setText(precoFormatadoCampo);
	}

	/**
	 * Método para validar todos os campos de manipulação do Objeto Veiculo. Ele
	 * verifica se algum dos campos está vazio e retorna uma
	 * IllegalArgumentException para ser tratada quando o usuário apertar o botão de
	 * salvar
	 * 
	 * @throws IllegalArgumentException - Caso algum dos campos estejam vazios, pois
	 *                                  esses campos são essenciais para cadastrar
	 *                                  ou alterar um veículo, logo eles não podem
	 *                                  ser nulos
	 */
	private void validarCampos() throws IllegalArgumentException {
		if (tfNome.getText().trim().isEmpty()) {
			throw new IllegalArgumentException("O campo Nome não pode ficar vazio");

		} else if (cmbCambio.getSelectedItem() == null || cmbCambio.getSelectedItem().equals("")) {
			throw new IllegalArgumentException("O campo Câmbio não pode ficar vazio");

		} else if (cmbDirecao.getSelectedItem() == null || cmbDirecao.getSelectedItem().equals("")) {
			throw new IllegalArgumentException("O campo Direção não pode ficar vazio");

		} else if (cmbCarroceria.getSelectedItem() == null || cmbCarroceria.getSelectedItem().equals("")) {
			throw new IllegalArgumentException("O campo Categoria não pode ficar vazio");

		} else if (cmbMarca.getSelectedItem() == null || cmbMarca.getSelectedItem().equals("")) {
			throw new IllegalArgumentException("O campo Marca não pode ficar vazio");
		}
	}

	/**
	 * Método para limpar os campos
	 */
	private void limparCampos() {
		tfId.setText("");
		tfNome.setText("");
		ftfPreco.setText("");
		ftfFabricacao.setText("");
		cmbCambio.setSelectedIndex(0);
		cmbDirecao.setSelectedIndex(0);
		cmbCarroceria.setSelectedIndex(0);
		cmbMarca.setSelectedIndex(0);
		selecionado = null;
		lblImagemVeiculo.setIcon(null);
		veiculo = null;
		taInformacoes.setText("");
	}

	/**
	 * Método para pegar o data de fabricacao do campo de data e formatar, tirando
	 * os caracteres especiais e convertendo de String para LocalDate a data. Esse
	 * valor convertido será colocando dentro do objeto LocalDate e depois e
	 * colocado no Objeto veiculo.
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
	private void pegarDataFabricacao() throws IllegalArgumentException, NumberFormatException, Exception {
		// Variável que pega a data e Limpa os dados do campo data
		String data = ftfFabricacao.getText().replaceAll("[ /]", "");
		// Variáveis para a data
		int dia, mes, ano;

		/*
		 * Verificando se o valor passado no campo tem o tamanho correto para a data e
		 * contenha apenas valores numéricos
		 */
		if (data.length() != 8) {
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

		// Colando no atributo de data (LocalDate) as variáveis de data
		dataFabricacao = LocalDate.of(ano, mes, dia);
	}

	/**
	 * Método para pegar o preço do campo de preço e formatar, tirando os caracteres
	 * especiais e convertendo de String para Double o preço. Esse valor convertido
	 * será colocando dentro do objeto veículo.
	 * 
	 * @return Retorna o preço formatado
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	private double pegarPreco() throws Exception {
		// Variáveis;
		String preco = "";

		try {
			/*
			 * Tirando os caracteres especiais
			 * 
			 * Observação: O caractere "," não é totalmente removido, ele é substituido pelo
			 * ".", pois como o preço é do tipo double, precisamos manter o valor após a
			 * virgula, então, a virgula é substituida pelo ponto para manter os valores
			 * certos na hora da formatação
			 */
			preco = ftfPreco.getText().replaceAll("[R$ .]", "").replaceAll("[,]", ".");

			// Retornando o valor transformando ele em double (cast)
			return Double.parseDouble(preco);

		} catch (Exception ex) {

			JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this,
					"Erro ao salvar o preço, por favor, verifique se o valor passado é valido", "Erro",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * Método para colocar a imagem dentro da label da logo
	 */
	private void colocarImagemNoCampo() {
		// Criando um vetor de bytes e pegando a imagem salva no Objeto veiculo
		byte[] imagemByte = veiculo.getImagemVeiculo();

		try {
			// Se o vetor de bytes não for nulo
			if (imagemByte != null) {
				/*
				 * Faz a leitura da imagem que está dentro do vetor de bytes e salva na memória
				 * (BufferedImage)
				 */
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));

				// Se o bufferedImage não fo nulo
				if (bufferedImage != null) {
					/*
					 * Cria uma image a partir da imagem salva na memória e redimensiona ela de
					 * acordo com o tamanho da label logo
					 */
					Image image = bufferedImage.getScaledInstance(lblImagemVeiculo.getWidth(),
							lblImagemVeiculo.getHeight(), Image.SCALE_SMOOTH);

					// Criando um novo objeto do tipo icon para colocar dentro da label
					ImageIcon icon = new ImageIcon(image);

					// Colocando o icon dentro da label
					lblImagemVeiculo.setIcon(icon);

				} else { // Caso a imagem não tenha uma formato suportado
					lblImagemVeiculo.setText("Formato de imagem não suportado");
				}

			} else { // Caso essa imagem não seja encontrada
				lblImagemVeiculo.setText("Imagem não encontrada");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(FrameManipulacaoVeiculo.this, "Falha ao carregar a imagem", "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
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
