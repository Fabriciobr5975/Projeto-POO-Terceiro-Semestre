package br.senac.sp.projetopoo.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.senac.sp.projetopoo.dao.hibernate.MarcaDaoHibernate;
import br.senac.sp.projetopoo.modelo.Marca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class MarcaDaoHibernateTest {

	private static MarcaDaoHibernate marcaDaoHibernate;
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private Marca marca;
	private List<Marca> lista;

	private boolean carroceriaInserida;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("senac-test");
		entityManager = entityManagerFactory.createEntityManager();
		marcaDaoHibernate = Mockito.spy(new MarcaDaoHibernate(entityManager));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		marcaDaoHibernate = null;
		entityManager.clear();
		entityManager.close();
		entityManager = null;
		entityManagerFactory.close();
		entityManagerFactory = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		marca = new Marca();
		marca.setNome("Marca A");
		marca.setLogo(new byte[] { 1, 2, 3, 4, 5 });

		lista = new ArrayList<Marca>(Arrays.asList(marca));
	}

	@AfterEach
	void tearDown() throws Exception {
		marca = null;
		lista = null;
		carroceriaInserida = false;
	}

	private void inserirMarcasParaTestes() throws Exception {
		if (!carroceriaInserida) {
			marcaDaoHibernate.inserir(marca);
		}
		carroceriaInserida = true;
	}

	@Test
	void deveInserirMarcaValidaEntaoRetorna1() throws Exception {
		marca.setNome("Marca A+");
		marca.setLogo(new byte[] { 5, 4, 3, 2, 1 });

		int resultado = marcaDaoHibernate.inserir(marca);
		assertEquals(1, resultado);

		verify(marcaDaoHibernate).inserir(marca);
	}

	@Test
	void naoDeveInserirMarcaInvalidaEntaoRetorna0() throws Exception {
		marca.setNome("Marca A+");
		marca.setLogo(new byte[] { 5, 4, 3, 2, 1 });

		int resultado = marcaDaoHibernate.inserir(marca);
		assertEquals(0, resultado);

		verify(marcaDaoHibernate).inserir(marca);
	}

	@Test
	void naoDeveInserirMarcaNullEntaoRetorna0() throws Exception {
		int resultado = marcaDaoHibernate.inserir(null);

		assertEquals(0, resultado);
		verify(marcaDaoHibernate).inserir(null);
	}

	@Test
	void deveBuscarUmaMarcaExistentePorUmId() throws Exception {
		inserirMarcasParaTestes();
		
		Marca marca2 = marcaDaoHibernate.buscar(2);
		assertEquals(marca, marca2);
	}

	@Test
	void deveRetornarNullQuandoBuscarUmaMarcaNãoExistentePorUmId() throws Exception {
		inserirMarcasParaTestes();

		marca = marcaDaoHibernate.buscar(0);
		assertNull(marca);
	}

	@Test
	void deveRetornar1QuandoAlterarUmaMarca() throws Exception {
		inserirMarcasParaTestes();
		marca.setNome("Marca Teste");

		int resultado = marcaDaoHibernate.alterar(marca);
		assertEquals(1, resultado);
	}

	@Test
	void deveRetornar0QuandoNãoAlterarUmaMarcaPorFaltaDoNome() throws Exception {
		marca.setNome(null);

		int resultado = marcaDaoHibernate.alterar(marca);
		assertEquals(0, resultado);

		verify(marcaDaoHibernate).alterar(marca);
	}
	
	@Test
	void deveRetornar0QuandoNãoAlterarUmaMarcaPorFaltaDaImagem() throws Exception {
		marca.setLogo(null);

		int resultado = marcaDaoHibernate.alterar(marca);
		assertEquals(0, resultado);

		verify(marcaDaoHibernate).alterar(marca);
	}
	
	@Test
	void deveRetornar0QuandoNãoAlterarUmaMarca() throws Exception {
		marca = null;

		int resultado = marcaDaoHibernate.alterar(marca);
		assertEquals(0, resultado);

		verify(marcaDaoHibernate).alterar(marca);
	}

	@Test
	void deveRemoverUmaMarcaComIdValidoERetornar1() throws Exception {
		inserirMarcasParaTestes();

		Marca marca2 = marcaDaoHibernate.buscar(1);
		assertNotNull(marca2);
		
		int resultado = marcaDaoHibernate.excluir(1);
		assertEquals(1, resultado);

		verify(marcaDaoHibernate).excluir(1);
	}

	@Test
	void naoDeveRemoverUmaMarcaInvalida() throws Exception {
		inserirMarcasParaTestes();

		Marca marca2 = marcaDaoHibernate.buscar(0);
		assertNull(marca2);
		
		int resultado = marcaDaoHibernate.excluir(0);
		assertEquals(0, resultado);

		verify(marcaDaoHibernate).excluir(0);
	}

	@Test
	void deveTrazerUmaListaDeMarcasPersistidas() throws Exception {
		inserirMarcasParaTestes();
		List<Marca> lista2 = marcaDaoHibernate.listar();

		assertEquals(lista2.get(0).getNome(), lista.get(0).getNome());
	}

	@Test
	void deveRetornarNullSeNenhumaMarcaForEncontradoNaListagem() throws Exception {
		List<Marca> lista2 = marcaDaoHibernate.listar();

		assertTrue(lista2.isEmpty());
		verify(marcaDaoHibernate).listar();
	}
}
