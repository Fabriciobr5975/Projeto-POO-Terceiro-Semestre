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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.senac.sp.projetopoo.dao.hibernate.EMFactory;
import br.senac.sp.projetopoo.dao.hibernate.InterfaceDao;
import br.senac.sp.projetopoo.dao.hibernate.MarcaDaoHibernate;
import br.senac.sp.projetopoo.modelo.Marca;
import br.senac.sp.projetopoo.tablemodel.MarcaTableModel;
import br.senac.sp.projetopoo.util.FrameUtil;

/**
 * Classe do Frame do CRUD da marca.
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public class FrameMarca extends JFrame {
	// Atributo da marca
	private Marca marca;
	private InterfaceDao<Marca> dao;
	private List<Marca> marcas;

	// Atributos para a manipulação da imagem da marca
	private JFileChooser chooser;
	private FileFilter imageFilter;
	private File selecionado;

	// Atributos do Frame
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// Atributos do ID
	private JLabel lblId;
	private JTextField tfId;

	// Atributos do Nome
	private JLabel lblNome;
	private JTextField tfNome;

	// Atributo da Logo
	private JLabel lblLogo;
	
	// Atributo do painel de rolagem
	private JScrollPane scrollPane;
	private JButton btnVoltarMenu;
	
	// Atributo da tabela
	private JTable tbMarca;
	
	// Atributo da tableModel
	private MarcaTableModel tableModel;

	// Botões
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMarca frame = new FrameMarca();
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
	public FrameMarca() {
		// Instanciando o objeto dao para possibilitar o CRUD
		dao = new MarcaDaoHibernate(EMFactory.getEntityManager());

		try {
			// Pegando todas as marcas criadas e salvando em uma lista de marcas
			marcas = dao.listar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(FrameMarca.this, "Erro: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		// Instanciando o tableModel para criar a tabela com todas as marcas criadas
		tableModel = new MarcaTableModel(marcas);

		// Instanciando o chooser para abrir a tela de seleção de arquivos
		chooser = new JFileChooser();
		// Filtrando o tipo de arquivo que aparece quando o chooser for ativado
		imageFilter = new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes());

		// Atributos do Frame
		setTitle("Cadastro de Marcas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 429, 590);

		// Centralizando a tela
		FrameUtil.centralizarTela(this);

		// Painel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Label ID
		lblId = new JLabel("ID:");
		lblId.setFont(new Font("Arial", Font.BOLD, 12));
		lblId.setBounds(10, 8, 46, 30);
		contentPane.add(lblId);

		// Label Nome
		lblNome = new JLabel("NOME:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 12));
		lblNome.setBounds(10, 63, 46, 30);
		contentPane.add(lblNome);

		// TextField ID
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setFont(new Font("Arial", Font.PLAIN, 12));
		tfId.setBounds(66, 10, 86, 30);
		contentPane.add(tfId);
		tfId.setColumns(10);

		// TextField Nome
		tfNome = new JTextField();
		tfNome.setFont(new Font("Arial", Font.PLAIN, 12));
		tfNome.setColumns(10);
		tfNome.setBounds(65, 65, 246, 30);
		contentPane.add(tfNome);

		// Label Logo
		lblLogo = new JLabel("");

		// Adicionando o evento de Mouse para a Label da Logo
		lblLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Se a quantidade de clicks for igual a 2
				if (e.getClickCount() == 2) {
					// Adicionando o filtro ao chooser
					chooser.setFileFilter(imageFilter);
					String diretorioChooser = System.getProperty("user.home") + "/Downloads";
					chooser.setCurrentDirectory(new File(diretorioChooser));

					// Abrindo e pegando o imagem do chooser
					if (chooser.showOpenDialog(FrameMarca.this) == JFileChooser.APPROVE_OPTION) {
						// Pegando a imagem selecionada
						selecionado = chooser.getSelectedFile();
						try {
							/*
							 * Lê a imagem que foi pega pelo selecionado e a converte em um BufferedImage
							 * (Imagem carregada na memória)
							 */
							BufferedImage bufImg = ImageIO.read(selecionado);

							/*
							 * Redimensiona o bufImg (BufferedImage) para que a imagem fique na proporção
							 * certa dentro da lblLogo
							 */
							Image imagem = bufImg.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(),
									Image.SCALE_SMOOTH);

							/*
							 * Criando uma icon para colocar na label, pegando a imagem que foi
							 * redimensionada
							 */
							ImageIcon imgLabel = new ImageIcon(imagem);

							// Adicionado imgLabel na lblLogo
							lblLogo.setIcon(imgLabel);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(FrameMarca.this, "Não foi possível adicionar a imagem",
									"Erro ao adicionar a imagem", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
				}
			}
		});

		// Label Logo
		lblLogo.setBackground(Color.LIGHT_GRAY);
		lblLogo.setBounds(321, 8, 85, 85);
		lblLogo.setOpaque(true);
		contentPane.add(lblLogo);

		// Button Salvar
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBorder(new LineBorder(Color.BLACK));
		btnSalvar.setBackground(Color.DARK_GRAY);
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("Arial", Font.BOLD, 12));

		// Adicionando um evento para o botão
		btnSalvar.addActionListener(e -> {
			int resultado = 0;

			// Se o nome estiver vazio
			if (tfNome.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(FrameMarca.this, "Informe o nome", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				tfNome.requestFocus();
				return;
			} else {
				// Se a marca for null, então deverá ser criada uma nova marca
				if (marca == null) {
					marca = new Marca();
				}
				// Caso a marca já tenha sido criada
				marca.setNome(tfNome.getText().trim());

			}
			try {
				// Se a imagem pega pelo chooser for diferente de null
				if (selecionado != null) {

					// Convertento a imagem em um vetor de bytes
					byte[] imagemBytes = Files.readAllBytes(selecionado.toPath());
					// Mudando a logo da marca
					marca.setLogo(imagemBytes);
				}
				// Se o id for igual a 0, ou seja, se não existir marca salva no sistema
				if (marca.getId() == 0) {
					// Criando no banco uma nova marca
					resultado = dao.inserir(marca);

					// Se foi feita a persistencia dos dados no banco de dados
					if (resultado == 1) {
						JOptionPane.showMessageDialog(FrameMarca.this, "Marca salva com sucesso", "Cadastrar Marca",
								JOptionPane.INFORMATION_MESSAGE);

						// Se os dados não foram persistidos
					} else {
						JOptionPane.showMessageDialog(FrameMarca.this,
								"Não foi possível cadastrar a marca, verifique se a Marca já está cadastrada ou se ainda não foi cadastrada",
								"Cadastrar Marcas", JOptionPane.WARNING_MESSAGE);
					}

				} else {
					// Alterando os dados de alguma marca
					resultado = dao.alterar(marca);

					// Se foi feita a persistencia dos dados no banco de dados
					if (resultado == 1) {
						JOptionPane.showMessageDialog(FrameMarca.this, "A Marca foi alterada com sucesso",
								"Alterar Marca", JOptionPane.INFORMATION_MESSAGE);

						// Se os dados não foram persistidos
					} else {
						JOptionPane.showMessageDialog(FrameMarca.this, "A Marca não foi alterada", "Alterar Marca",
								JOptionPane.WARNING_MESSAGE);
					}

				}

				// Listando todas as marcas salvas
				marcas = dao.listar();

				// Mudando a lista que será usada para exibir os dados na tela
				tableModel.setLista(marcas);

				// Para atualizar os dados dentro da tabela
				tableModel.fireTableDataChanged();

				// Limpando os dados quando um elemento for cadastrado
				limpar();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(FrameMarca.this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		});

		// Button Salvar
		btnSalvar.setMnemonic('s');
		btnSalvar.setBounds(10, 112, 89, 30);
		contentPane.add(btnSalvar);

		// Button Excluir
		btnExcluir = new JButton("Excluir");
		btnExcluir.setBorder(new LineBorder(Color.BLACK));
		btnExcluir.setBackground(Color.DARK_GRAY);
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("Arial", Font.BOLD, 12));

		// Adicionando o evento para excluir uma marca
		btnExcluir.addActionListener(e -> {
			// Se a marca não for null
			if (marca != null) {
				// Peguntando se ele realmente deseja excluir essa marca
				if (JOptionPane.showConfirmDialog(FrameMarca.this,
						"Deseja excluir a marca " + marca.getNome()) == JOptionPane.YES_OPTION) {
					try {
						// Chamando o método de excluir uma marca
						int resultado = dao.excluir(marca.getId());

						// Se foi feita a persistencia dos dados no banco de dados
						if (resultado == 1) {
							JOptionPane.showMessageDialog(FrameMarca.this, "Marca excluída com sucesso",
									"Excluir Marca", JOptionPane.INFORMATION_MESSAGE);

							// Se os dados não foram persistidos
						} else {
							JOptionPane.showMessageDialog(FrameMarca.this, "A Marca não foi excluída", "Excluir Marca",
									JOptionPane.WARNING_MESSAGE);
						}
						//
						marcas = dao.listar();

						// Mudando a lista que será usada para exibir os dados na tela
						tableModel.setLista(marcas);

						// Para atualizar os dados dentro da tabela
						tableModel.fireTableDataChanged();

						// Limpando os campos após a exclusão
						limpar();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} else {
				// Caso o usuário não tenha colocado nenhuma marca para excluir
				JOptionPane.showMessageDialog(FrameMarca.this, "Selecione uma marca para excluí-lá",
						"Mensagem do Sistema", JOptionPane.WARNING_MESSAGE);
			}
		});

		// Button Excluir
		btnExcluir.setMnemonic('e');
		btnExcluir.setBounds(109, 112, 89, 30);
		contentPane.add(btnExcluir);

		// Button Limpar
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBorder(new LineBorder(Color.BLACK));
		btnLimpar.setBackground(Color.DARK_GRAY);
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 12));
		// Adicionando o evento de limpar os campos
		btnLimpar.addActionListener(e -> {
			// Chamando o método que limpa os campos
			limpar();
		});

		// Button Limpar
		btnLimpar.setMnemonic('l');
		btnLimpar.setBounds(211, 112, 89, 30);
		contentPane.add(btnLimpar);

		// Criando um scrollPane, para que a tabela tenha uma rolagem
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 164, 396, 371);
		contentPane.add(scrollPane);

		// Tabela de Marca
		tbMarca = new JTable(tableModel);
		tbMarca.setToolTipText("Selecione um item para alterar ou excluir");
		tbMarca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Adicionando um evento para a tabela
		tbMarca.getSelectionModel().addListSelectionListener(e -> {
			// Pegando a linha selecionado
			int linha = tbMarca.getSelectedRow();

			// Se a linha selecionada for maior que 0
			if (linha >= 0) {
				// Adicionado os elementos na marca através da linha selecionada
				marca = marcas.get(linha);
				// Trocando os textos do ID e do Nome das Labels
				tfId.setText("" + marca.getId());
				tfNome.setText(marca.getNome());
				colocarImagemNoCampo();
			}
		});

		// Adicionando a tabela dentro do scrollPane, assim a tabela tem uma rolagem
		scrollPane.setViewportView(tbMarca);

		// Button Voltar para o Menu
		btnVoltarMenu = new JButton("Menu");
		btnVoltarMenu.setMnemonic('M');
		btnVoltarMenu.setForeground(Color.WHITE);
		btnVoltarMenu.setFont(new Font("Arial", Font.BOLD, 12));
		btnVoltarMenu.setBorder(new LineBorder(Color.BLACK));
		btnVoltarMenu.setBackground(Color.DARK_GRAY);
		btnVoltarMenu.setBounds(317, 112, 89, 30);

		// Adicionando o evento para o botão de voltar para o menu
		btnVoltarMenu.addActionListener(e -> {
			new FrameInicial();
			dispose();
		});

		contentPane.add(btnVoltarMenu);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Método para limpar os campos
	 */
	private void limpar() {
		tfId.setText("");
		tfNome.setText("");
		marca = null;
		tfNome.requestFocus();
		lblLogo.setIcon(null);
	}

	/**
	 * Método para colocar a imagem dentro da label da logo
	 */
	private void colocarImagemNoCampo() {
		// Criando um vetor de bytes e pegando a imagem salva no Objeto marca
		byte[] imagemByte = marca.getLogo();

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
					Image image = bufferedImage.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(),
							Image.SCALE_SMOOTH);

					// Criando um novo objeto do tipo icon para colocar dentro da label
					ImageIcon icon = new ImageIcon(image);
					// Colocando o icon dentro da label
					lblLogo.setIcon(icon);

				} else { // Caso a imagem não tenha uma formato suportado
					lblLogo.setText("Formato de imagem não suportado");
				}

			} else { // Caso essa imagem não seja encontrada
				lblLogo.setText("Imagem não encontrada");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(FrameMarca.this, "Falha ao carregar a imagem", "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
