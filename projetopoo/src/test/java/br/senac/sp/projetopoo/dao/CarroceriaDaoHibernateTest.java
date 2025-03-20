package br.senac.sp.projetopoo.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
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
import jakarta.persistence.Persistence;

class CarroceriaDaoHibernateTest {
	
	private CarroceriaDaoHibernate carroceriaDaoHibernate;
	private Carroceria carroceria;
	private List<Carroceria> lista;
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("senac-test");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		entityManagerFactory = null;
		entityManager = null;
	}

	@BeforeEach
	void setUp() throws Exception {
		carroceriaDaoHibernate = Mockito.spy(new CarroceriaDaoHibernate(entityManager));
		carroceria = new Carroceria();
		carroceria.setCarroceria(CarroceriasVeiculo.HATCH_COMPACTO);
		
		lista = new ArrayList<Carroceria>();
		lista.add(carroceria);
	}

	@AfterEach
	void tearDown() throws Exception {
		carroceriaDaoHibernate = null;
		carroceria = null;
		lista = null;
	}
	
	@Test
	void deveInserirCarroceriaValidaEntaoRetorna1() throws Exception {
		doReturn(1).when(carroceriaDaoHibernate).inserir(carroceria);

		assertEquals(1, carroceriaDaoHibernate.inserir(carroceria));

		verify(carroceriaDaoHibernate).inserir(carroceria);
	}

	@Test
	void naoDeveInserirCarroceriaInvalidaEntaoRetorna0() throws Exception {
		doReturn(0).when(carroceriaDaoHibernate).inserir(carroceria);

		assertEquals(0, carroceriaDaoHibernate.inserir(carroceria));

		verify(carroceriaDaoHibernate).inserir(carroceria);
	}

	@Test
	void deveBuscarUmaCarroceriaExistentePorUmId() throws Exception {
		doReturn(carroceria).when(carroceriaDaoHibernate).buscar(1);

		assertNotNull(carroceriaDaoHibernate.buscar(1));

		verify(carroceriaDaoHibernate).buscar(1);
	}
	
	@Test
	void deveBuscarUmaCarroceriaExistentePorUmTipoDeCarroceria() throws Exception {
		doReturn(carroceria).when(carroceriaDaoHibernate).buscar(CarroceriasVeiculo.HATCH_COMPACTO);

		assertNotNull(carroceriaDaoHibernate.buscar(CarroceriasVeiculo.HATCH_COMPACTO));

		verify(carroceriaDaoHibernate).buscar(CarroceriasVeiculo.HATCH_COMPACTO);
	}
	
	@Test
	void deveRetornarNullQuandoBuscarUmaCarroceriaNãoExistentePorUmTipoDeCarroceria() throws Exception {
		doReturn(null).when(carroceriaDaoHibernate).buscar(CarroceriasVeiculo.SEDA_COMPACTO);

		assertNull(carroceriaDaoHibernate.buscar(CarroceriasVeiculo.SEDA_COMPACTO));

		verify(carroceriaDaoHibernate).buscar(CarroceriasVeiculo.SEDA_COMPACTO);
	}
	
	@Test
	void deveRetornarNullQuandoBuscarUmaCarroceriaPorUmTipoDeCarroceriaNull() throws Exception {
		doReturn(null).when(carroceriaDaoHibernate).buscar(null);

		assertNull(carroceriaDaoHibernate.buscar(null));

		verify(carroceriaDaoHibernate).buscar(null);
	}

	@Test
	void deveRetornarNullQuandoBuscarUmaCarroceriaNãoExistentePorUmId() throws Exception {
		doReturn(null).when(carroceriaDaoHibernate).buscar(0);

		assertNull(carroceriaDaoHibernate.buscar(0));

		verify(carroceriaDaoHibernate).buscar(0);
	}

	@Test
	void deveRetornar1QuandoAlterarUmaCarroceria() throws Exception {
		doReturn(1).when(carroceriaDaoHibernate).alterar(carroceria);

		assertEquals(1, carroceriaDaoHibernate.alterar(carroceria));

		verify(carroceriaDaoHibernate).alterar(carroceria);
	}

	@Test
	void deveRetornar0QuandoNãoAlterarUmaCarroceria() throws Exception {
		doReturn(0).when(carroceriaDaoHibernate).alterar(carroceria);

		assertEquals(0, carroceriaDaoHibernate.alterar(carroceria));

		verify(carroceriaDaoHibernate).alterar(carroceria);
	}

	@Test
	void deveRemoverUmaCarroceriaComIdValidoERetornar1() throws Exception {
		doReturn(1).when(carroceriaDaoHibernate).excluir(1);

		assertEquals(1, carroceriaDaoHibernate.excluir(1));

		verify(carroceriaDaoHibernate).excluir(1);
	}

	@Test
	void naoDeveRemoverUmaCarroceriaInvalida() throws Exception {
		doReturn(0).when(carroceriaDaoHibernate).excluir(0);

		assertEquals(0, carroceriaDaoHibernate.excluir(0));

		verify(carroceriaDaoHibernate).excluir(0);
	}

	@Test
	void deveTrazerUmaListaDeCarroceriasPersistidas() throws Exception {
		doReturn(lista).when(carroceriaDaoHibernate).listar();

		assertEquals(lista, carroceriaDaoHibernate.listar());

		verify(carroceriaDaoHibernate).listar();
	}

	@Test
	void deveRetornarNullSeNenhumaCarroceriaForEncontradoNaListagem() throws Exception {
		doReturn(null).when(carroceriaDaoHibernate).listar();

		assertNull(carroceriaDaoHibernate.listar());
	}
}
