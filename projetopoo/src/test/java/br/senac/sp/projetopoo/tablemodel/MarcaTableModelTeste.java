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

import br.senac.sp.projetopoo.modelo.Marca;

class MarcaTableModelTeste {
	
	private static MarcaTableModel marcaTableModel;
	private static List<Marca> lista;
	private static Marca marca;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		marca = new Marca();
		marca.setNome("Marca A");
		marca.setLogo(new byte[] { 1, 2, 3, 4, 5 });
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		marca = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		lista = new ArrayList<Marca>(Arrays.asList(marca));
		marcaTableModel = new MarcaTableModel(lista);
	}

	@AfterEach
	void tearDown() throws Exception {
		marcaTableModel = null;
		lista = null;
	}

	
	@Test
	void deveRetornarValorNullQuandoBuscarMarcaEmUmaPosicaoInvalida() {
		assertNull(marcaTableModel.getValueAt(0, 2));
	}

	@Test
	void deveVerificarOMatchDosIdDasMarcas() {
		assertEquals(lista.get(0).getId(), marcaTableModel.getValueAt(0, 0));
	}

	@Test
	void deveVerificarOMatchDosNomesDasMarcas() {
		assertEquals(lista.get(0).getNome(), marcaTableModel.getValueAt(0, 1));
	}
}
