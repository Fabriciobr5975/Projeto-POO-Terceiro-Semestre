package br.senac.sp.projetopoo.tablemodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.senac.sp.projetopoo.modelo.Carroceria;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;

class CarroceriaTableModelTest {

	private static CarroceriaTableModel carroceriaTableModel;
	private static List<Carroceria> lista;
	private static Carroceria carroceria;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		carroceria = new Carroceria();
		carroceria.setCarroceria(CarroceriasVeiculo.HATCH_COMPACTO);
		carroceria.setId(0);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		carroceria = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		lista = new ArrayList<Carroceria>(Arrays.asList(carroceria));
		carroceriaTableModel = new CarroceriaTableModel(lista);
	}

	@AfterEach
	void tearDown() throws Exception {
		lista = null;
		carroceriaTableModel = null;
	}

	@Test
	void deveRetornarValorNullQuandoBuscarCarroceriaEmUmaPosicaoInvalida() {
		assertNull(carroceriaTableModel.getValueAt(0, 2));
	}

	@Test
	void deveVerificarOMatchDosIdDasCarrocerias() {
		assertEquals(lista.get(0).getId(), carroceriaTableModel.getValueAt(0, 0));
	}

	@Test
	void deveVerificarOMatchDosTiposDeCarrocerias() {
		assertEquals(lista.get(0).getCarroceria().getTipo(), carroceriaTableModel.getValueAt(0, 1));
	}
}
