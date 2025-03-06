package br.senac.sp.projetopoo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.senac.sp.projetopoo.util.FrameUtil;

/**
 * Classe do Frame do menu do Sistema.
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public class FrameInicial extends JFrame {
	// Atributos da Frame
	private static final long serialVersionUID = 1L;
	
	// Atributos do Frame
	private JPanel contentPane;
	
	// Atributo para a barra do menu
	private JMenuBar mnbBarraMenu;
	
	// Atributos para as opções do menu
	private JMenu mnuVeículo;
	private JMenu mnuMarca;
	private JMenu mnuCategoria;

	// Atributos para as opções do menu
	private JMenuItem itmVeiculo;
	private JMenuItem itmListagemVeiculo;
	private JMenuItem itmMarca;
	private JMenuItem itmCategoria;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameInicial frame = new FrameInicial();
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
	public FrameInicial() {
		// Atributos do Frame
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		// Centralizando a tela
		FrameUtil.centralizarTela(this);

		// Menu
		mnbBarraMenu = new JMenuBar();
		mnbBarraMenu.setForeground(Color.WHITE);
		mnbBarraMenu.setBackground(Color.DARK_GRAY);
		mnbBarraMenu.setBorder(new LineBorder(Color.BLACK, 1));
		setJMenuBar(mnbBarraMenu);

		// Opções do Menu (Veiculo)
		mnuVeículo = new JMenu("Veículo");
		mnuVeículo.setForeground(Color.WHITE);
		mnbBarraMenu.add(mnuVeículo);

		// Item do Menu (Manipulação do Veículo)
		itmVeiculo = new JMenuItem("Manipulação do Véiculo");
		itmVeiculo.addActionListener(e -> {
			new FrameManipulacaoVeiculo();
			dispose();
		});

		// Adicionando o item a opção do menu;
		mnuVeículo.add(itmVeiculo);

		// Item do Menu (Listagem dos Veículos)
		itmListagemVeiculo = new JMenuItem("Listagem dos Veículos");
		itmListagemVeiculo.addActionListener(e -> {
			new FrameListagemVeiculo();
			dispose();
		});

		// Adicionando o item a opção do menu;
		mnuVeículo.add(itmListagemVeiculo);

		// Opções do Menu (Marca)
		mnuMarca = new JMenu("Marca");
		mnuMarca.setForeground(Color.WHITE);
		mnbBarraMenu.add(mnuMarca);

		// Item do Menu (Manipulação das Marcas)
		itmMarca = new JMenuItem("Manipulação das Marcas");
		itmMarca.addActionListener(e -> {
			new FrameMarca();
			dispose();
		});

		// Adicionando o item a opção do menu;
		mnuMarca.add(itmMarca);

		// Opções do Menu (Categoria)
		mnuCategoria = new JMenu("Categoria");
		mnuCategoria.setForeground(Color.WHITE);
		mnbBarraMenu.add(mnuCategoria);

		// Item do Menu (Manipulação das Categorias)
		itmCategoria = new JMenuItem("Manipulação das Categorias");
		itmCategoria.addActionListener(e -> {
			new FrameCarroceria();
			dispose();
		});

		// Adicionando o item a opção do menu;
		mnuCategoria.add(itmCategoria);

		// Painel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Atributos do Frame
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setResizable(false);
		setVisible(true);
	}
}
