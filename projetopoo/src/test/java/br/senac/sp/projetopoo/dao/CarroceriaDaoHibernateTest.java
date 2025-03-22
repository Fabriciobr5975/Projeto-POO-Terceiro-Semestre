package br.senac.sp.projetopoo.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import br.senac.sp.projetopoo.dao.hibernate.CarroceriaDaoHibernate;
import br.senac.sp.projetopoo.modelo.Carroceria;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

class CarroceriaDaoHibernateTest {

	private static CarroceriaDaoHibernate carroceriaDaoHibernate;
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private Carroceria carroceria;
	private List<Carroceria> lista;

	private boolean carroceriaInserida;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("senac-test");
		entityManager = entityManagerFactory.createEntityManager();
		carroceriaDaoHibernate = Mockito.spy(new CarroceriaDaoHibernate(entityManager));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		carroceriaDaoHibernate = null;
		entityManager.clear();
		entityManager.close();
		entityManager = null;
		entityManagerFactory.close();
		entityManagerFactory = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		carroceria = new Carroceria();
		carroceria.setCarroceria(CarroceriasVeiculo.HATCH_COMPACTO);

		lista = new ArrayList<Carroceria>(Arrays.asList(carroceria));
		carroceriaInserida = false;
	}

	@AfterEach
	void tearDown() throws Exception {
		carroceria = null;
		lista = null;
		carroceriaInserida = false;
	}

	private void inserirCarroceriaParaTestes() throws Exception {
		if (!carroceriaInserida) {
			carroceriaDaoHibernate.inserir(carroceria);
		}
		carroceriaInserida = true;	
	}

	@Test
	void deveInserirCarroceriaValidaEntaoRetorna1() throws Exception {
		carroceria.setCarroceria(CarroceriasVeiculo.SEDA_COMPACTO);
		
		int resultado = carroceriaDaoHibernate.inserir(carroceria);
		assertEquals(1, resultado);

		verify(carroceriaDaoHibernate).inserir(carroceria);
	}

	@Test
	void naoDeveInserirCarroceriaInvalidaEntaoRetorna0() throws Exception {
		carroceria.setCarroceria(CarroceriasVeiculo.SEDA_COMPACTO);

		int resultado = carroceriaDaoHibernate.inserir(carroceria);
		assertEquals(0, resultado);

		verify(carroceriaDaoHibernate).inserir(carroceria);
	}

	@Test
	void naoDeveInserirCarroceriaNullEntaoRetorna0() throws Exception {
		int resultado = carroceriaDaoHibernate.inserir(null);
		assertEquals(0, resultado);

		verify(carroceriaDaoHibernate).inserir(null);
	}

	@Test
	void deveBuscarUmaCarroceriaExistentePorUmId() throws Exception {
		inserirCarroceriaParaTestes();

		carroceria = carroceriaDaoHibernate.buscar(1);
		assertNotNull(carroceria);

		verify(carroceriaDaoHibernate).buscar(1);
	}

	@Test
	void deveBuscarUmaCarroceriaExistentePorUmTipoDeCarroceria() throws Exception {
		inserirCarroceriaParaTestes();
		
		carroceria = carroceriaDaoHibernate.buscar(CarroceriasVeiculo.HATCH_COMPACTO);
		assertNotNull(carroceria);
		
		verify(carroceriaDaoHibernate).buscar(CarroceriasVeiculo.HATCH_COMPACTO);
	}

	@Test
	void deveRetornarNullQuandoBuscarUmaCarroceriaNãoExistentePorUmTipoDeCarroceria() throws Exception {
		inserirCarroceriaParaTestes();

		assertThrows(NoResultException.class, () -> {
			carroceria = carroceriaDaoHibernate.buscar(CarroceriasVeiculo.HATCH_MEDIO);
		});

		verify(carroceriaDaoHibernate).buscar(CarroceriasVeiculo.HATCH_MEDIO);
	}

	@Test
	void deveRetornarNullQuandoBuscarUmaCarroceriaPorUmTipoDeCarroceriaNull() throws Exception {
		assertThrows(NoResultException.class, () -> {
			carroceria = carroceriaDaoHibernate.buscar(null);
		});

		verify(carroceriaDaoHibernate).buscar(null);
	}

	@Test
	void deveRetornarNullQuandoBuscarUmaCarroceriaNãoExistentePorUmId() throws Exception {
		inserirCarroceriaParaTestes();

		carroceria = carroceriaDaoHibernate.buscar(0);
		assertNull(carroceria);

		verify(carroceriaDaoHibernate).buscar(0);
	}

	@Test
	void deveRetornar1QuandoAlterarUmaCarroceria() throws Exception {
		inserirCarroceriaParaTestes();
		carroceria.setCarroceria(CarroceriasVeiculo.AVENTUREIRO_COMPACTO);

		int resultado = carroceriaDaoHibernate.alterar(carroceria);
		assertEquals(1, resultado);
		
		verify(carroceriaDaoHibernate).alterar(carroceria);
	}

	@Test
	void deveRetornar0QuandoNãoAlterarUmaCarroceriaPorFaltaDosParametros() throws Exception {
		inserirCarroceriaParaTestes();
		carroceria.setCarroceria(null);

		int resultado = carroceriaDaoHibernate.alterar(carroceria);
		assertEquals(0, resultado);
		
		verify(carroceriaDaoHibernate).alterar(carroceria);
	}
	
	@Test
	void deveRetornar0QuandoNãoAlterarUmaCarroceria() throws Exception {
		int resultado = carroceriaDaoHibernate.alterar(carroceria);
		assertEquals(0, resultado);
	}

	@Test
	void deveRemoverUmaCarroceriaComIdValidoERetornar1() throws Exception {
		inserirCarroceriaParaTestes();

		carroceria = carroceriaDaoHibernate.buscar(1);
		assertNotNull(carroceria);
		
		int resultado = carroceriaDaoHibernate.excluir(1);
		assertEquals(1, resultado);
		
		verify(carroceriaDaoHibernate).excluir(1);
	}

	@Test
	void naoDeveRemoverUmaCarroceriaInvalida() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			carroceria = carroceriaDaoHibernate.buscar(0);
			assertNull(carroceria);
			
			int resultado = carroceriaDaoHibernate.excluir(0);
			assertEquals(0, resultado);
		});

		verify(carroceriaDaoHibernate).excluir(0);
	}

	@Test
	void deveTrazerUmaListaDeCarroceriasPersistidas() throws Exception {
		inserirCarroceriaParaTestes();
		List<Carroceria> lista2 = carroceriaDaoHibernate.listar();

		assertEquals(lista2, lista);
	}

	@Test
	void deveRetornarNullSeNenhumaCarroceriaForEncontradoNaListagem() throws Exception {
		List<Carroceria> lista2 = carroceriaDaoHibernate.listar();

		assertTrue(lista2.isEmpty());
		verify(carroceriaDaoHibernate).listar();
	}
}
