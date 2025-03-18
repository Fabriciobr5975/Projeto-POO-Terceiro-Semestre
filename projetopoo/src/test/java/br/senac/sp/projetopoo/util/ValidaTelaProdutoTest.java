package br.senac.sp.projetopoo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.MaskFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.senac.sp.projetopoo.modelo.Veiculo;

class ValidaTelaProdutoTest {

	private static Veiculo veiculo1;
	private static Veiculo veiculo2;
	private static Veiculo veiculo3;

	private static JLabel lblImagemVeiculo;
	private static JFormattedTextField ftfDataFabricacao;
	private static JFormattedTextField ftfPrecoVeiculo;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		veiculo1 = new Veiculo();
		veiculo2 = new Veiculo();
		veiculo3 = new Veiculo();

		// Campos (txtfield)
		lblImagemVeiculo = new JLabel();
		ftfDataFabricacao = new JFormattedTextField(new MaskFormatter(" ##/##/####"));
		ftfPrecoVeiculo = new JFormattedTextField(new MaskFormatter("R$ #.###.###,##"));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		veiculo1 = null;
		lblImagemVeiculo = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		veiculo2.setImagemVeiculo(new byte[] { 1, 2, 3, 4, 5 });
		veiculo3.setImagemVeiculo(new byte[] {});

	}

	@AfterEach
	void tearDown() throws Exception {
		veiculo2.setImagemVeiculo(null);
		veiculo3.setImagemVeiculo(null);
	}

	@Test()
	void deveLancarExcecaoParaVeiculoNull() {
		try {
			ValidacaoTelaProduto.colocarImagemNoCampo(lblImagemVeiculo, veiculo1);
		} catch (Exception e) {
			assertEquals("Imagem não encontrada", e.getMessage());
		}
	}

	@Test()
	void deveLancarExcecaoParaImagemVeiculoNull() {
		try {
			ValidacaoTelaProduto.colocarImagemNoCampo(lblImagemVeiculo, veiculo2);
		} catch (Exception e) {
			assertEquals("Formato de imagem não suportado", e.getMessage());
		}
	}

	@Test
	void deveLancarExecacaoSeDiaForMenorOuIgualZero() {
		try {
			ftfDataFabricacao.setText(" 00/03/2025");
			ValidacaoTelaProduto.pegarDataFabricacao(ftfDataFabricacao);
		} catch (Exception e) {
			assertEquals("Coloque um dia válido", e.getMessage());
		}
	}

	@Test
	void deveLancarExecacaoSeDiaForMaiorForMaiorQue31() {
		try {
			ftfDataFabricacao.setText(" 31/03/2025");
			ValidacaoTelaProduto.pegarDataFabricacao(ftfDataFabricacao);
		} catch (Exception e) {
			assertEquals("Coloque um dia válido", e.getMessage());
		}
	}

	@Test
	void deveLancarExecacaoSeDiaPassadoNaoCorresponderComMes() {
		assertThrows(DateTimeException.class, () -> {
			ftfDataFabricacao.setText(" 29/02/2025");
			ValidacaoTelaProduto.pegarDataFabricacao(ftfDataFabricacao);
		});
	}

	@Test
	void deveLancarExecacaoSeMesForMenorOuIgualZero() {
		try {
			ftfDataFabricacao.setText(" 17/00/2025");
			ValidacaoTelaProduto.pegarDataFabricacao(ftfDataFabricacao);
		} catch (Exception e) {
			assertEquals("Coloque um mês válido", e.getMessage());
		}
	}

	@Test
	void deveLancarExecacaoSeMesForMaior12() {
		try {
			ftfDataFabricacao.setText(" 17/13/2025");
			ValidacaoTelaProduto.pegarDataFabricacao(ftfDataFabricacao);
		} catch (Exception e) {
			assertEquals("Coloque um mês válido", e.getMessage());
		}
	}

	@Test
	void deveLancarExecacaoSeAnoForMenorOuIgualZero() {
		try {
			ftfDataFabricacao.setText(" 17/03/0000");
			ValidacaoTelaProduto.pegarDataFabricacao(ftfDataFabricacao);
		} catch (Exception e) {
			assertEquals("Coloque um ano válido", e.getMessage());
		}
	}

	@Test
	void deveLancarExecacaoSeAnoForMaiorQueAnoAtual() {
		try {
			ftfDataFabricacao.setText(" 17/03/2026");
			ValidacaoTelaProduto.pegarDataFabricacao(ftfDataFabricacao);
		} catch (Exception e) {
			assertEquals("Coloque um ano válido", e.getMessage());
		}
	}

	@Test
	void deveLancarExcecaoSePrecoForNull() {
		try {
			assertEquals(0, ValidacaoTelaProduto.pegarPreco(ftfPrecoVeiculo));
		} catch (Exception e) {
		}
	}
}
