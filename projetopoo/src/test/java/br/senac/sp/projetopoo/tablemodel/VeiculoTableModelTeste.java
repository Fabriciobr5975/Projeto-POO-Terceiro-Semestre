package br.senac.sp.projetopoo.tablemodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.senac.sp.projetopoo.modelo.Carroceria;
import br.senac.sp.projetopoo.modelo.Marca;
import br.senac.sp.projetopoo.modelo.Veiculo;
import br.senac.sp.projetopoo.modelo.enums.CambioVeiculo;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import br.senac.sp.projetopoo.modelo.enums.DirecaoVeiculo;

class VeiculoTableModelTeste {

	private static VeiculoTableModel veiculoTableModel;
	private static List<Veiculo> lista;
	private static Veiculo veiculo;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		veiculo = new Veiculo();

		veiculo.setNome("Veículo A");
		veiculo.setInformacoes("Veículo com ótimo custo-benefício");
		veiculo.setPreco(50000.00);
		veiculo.setFabricacao(LocalDate.of(2022, 5, 10));
		veiculo.setImportacao(false);
		veiculo.setCambio(CambioVeiculo.MANUAL);
		veiculo.setDirecao(DirecaoVeiculo.HIDRAULICA);

		// Criando objetos relacionados (Marca e Carroceria)
		Marca marca = new Marca();
		marca.setId(1);
		marca.setNome("Marca A");

		Carroceria carroceria = new Carroceria();
		carroceria.setId(1);
		carroceria.setCarroceria(CarroceriasVeiculo.HATCH_COMPACTO);

		veiculo.setMarca(marca);
		veiculo.setCarroceria(carroceria);
		veiculo.setImagemVeiculo(new byte[] { 1, 2, 3, 4, 5 });
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		veiculo = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		lista = new ArrayList<Veiculo>(Arrays.asList(veiculo));
		veiculoTableModel = new VeiculoTableModel(lista);

	}

	@AfterEach
	void tearDown() throws Exception {
		veiculoTableModel = null;
		lista = null;
	}

	@Test
	void deveRetornarValorNullQuandoBuscarVeiculoEmUmaPosicaoInvalida() {
		assertNull(veiculoTableModel.getValueAt(0, 9));
	}

	@Test
	void deveVerificarOMatchDosIdDosVeiculos() {
		assertEquals(lista.get(0).getId(), veiculoTableModel.getValueAt(0, 0));
	}

	@Test
	void deveVerificarOMatchDosNomesDeVeiculos() {
		assertEquals(lista.get(0).getNome(), veiculoTableModel.getValueAt(0, 1));
	}

	@Test
	void deveVerificarOMatchDosPrecosDeVeiculos() {
		assertEquals(lista.get(0).getPreco(), veiculoTableModel.getValueAt(0, 2));
	}

	@Test
	void deveVerificarOMatchDasDatasDeFabricacaoDeVeiculos() {
		DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = formatadorDeData.format(lista.get(0).getFabricacao());

		assertEquals(dataFormatada, veiculoTableModel.getValueAt(0, 3));
	}

	@Test
	void deveVerificarOMatchSeOVeiculoEImportado() {
		String importacao = lista.get(0).isImportacao() ? "Importado" : "Nacional";

		assertEquals(importacao, veiculoTableModel.getValueAt(0, 4));
	}

	@Test
	void deveVerificarOMatchDosCambiosDoVeiculos() {
		assertEquals(lista.get(0).getCambio().getTipo(), veiculoTableModel.getValueAt(0, 5));
	}

	@Test
	void deveVerificarOMatchDasDirecoesDeVeiculos() {
		assertEquals(lista.get(0).getDirecao().getTipo(), veiculoTableModel.getValueAt(0, 6));
	}

	@Test
	void deveVerificarOMatchDasMarcasDosVeiculos() {
		assertEquals(lista.get(0).getMarca().getNome(), veiculoTableModel.getValueAt(0, 7));
	}

	@Test
	void deveVerificarOMatchDasCarroceriasDosVeiculos() {
		assertEquals(lista.get(0).getCarroceria(), veiculoTableModel.getValueAt(0, 8));
	}
}
