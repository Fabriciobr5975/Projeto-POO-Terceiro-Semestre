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

import br.senac.sp.projetopoo.dao.hibernate.MarcaDaoHibernate;
import br.senac.sp.projetopoo.modelo.Marca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class MarcaDaoHibernateTest {

	private MarcaDaoHibernate marcaDaoHibernate;
	private Marca marca;
	private List<Marca> lista;
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
		marcaDaoHibernate = Mockito.spy(new MarcaDaoHibernate(entityManager));
		marca = new Marca();
		marca.setNome("Marca A");
		marca.setLogo(new byte[] { 1, 2, 3, 4, 5 });
		
		lista = new ArrayList<Marca>();
		lista.add(marca);
	}

	@AfterEach
	void tearDown() throws Exception {
		marcaDaoHibernate = null;
		marca = null;
		lista = null;
	}

	@Test
	void deveInserirMarcaValidaEntaoRetorna1() throws Exception {
		doReturn(1).when(marcaDaoHibernate).inserir(marca);

		assertEquals(1, marcaDaoHibernate.inserir(marca));

		verify(marcaDaoHibernate).inserir(marca);
	}

	@Test
	void naoDeveInserirMarcaInvalidaEntaoRetorna0() throws Exception {
		doReturn(0).when(marcaDaoHibernate).inserir(marca);

		assertEquals(0, marcaDaoHibernate.inserir(marca));

		verify(marcaDaoHibernate).inserir(marca);
	}

	@Test
	void deveBuscarUmaMarcaExistentePorUmId() throws Exception {
		doReturn(marca).when(marcaDaoHibernate).buscar(1);

		assertNotNull(marcaDaoHibernate.buscar(1));

		verify(marcaDaoHibernate).buscar(1);
	}

	@Test
	void deveRetornarNullQuandoBuscarUmaMarcaNãoExistentePorUmId() throws Exception {
		doReturn(null).when(marcaDaoHibernate).buscar(0);

		assertNull(marcaDaoHibernate.buscar(0));

		verify(marcaDaoHibernate).buscar(0);
	}

	@Test
	void deveRetornar1QuandoAlterarUmaMarca() throws Exception {
		doReturn(1).when(marcaDaoHibernate).alterar(marca);

		assertEquals(1, marcaDaoHibernate.alterar(marca));

		verify(marcaDaoHibernate).alterar(marca);
	}

	@Test
	void deveRetornar0QuandoNãoAlterarUmaMarca() throws Exception {
		doReturn(0).when(marcaDaoHibernate).alterar(marca);

		assertEquals(0, marcaDaoHibernate.alterar(marca));

		verify(marcaDaoHibernate).alterar(marca);
	}

	@Test
	void deveRemoverUmaMarcaComIdValidoERetornar1() throws Exception {
		doReturn(1).when(marcaDaoHibernate).excluir(1);

		assertEquals(1, marcaDaoHibernate.excluir(1));

		verify(marcaDaoHibernate).excluir(1);
	}

	@Test
	void naoDeveRemoverUmaMarcaInvalida() throws Exception {
		doReturn(0).when(marcaDaoHibernate).excluir(0);

		assertEquals(0, marcaDaoHibernate.excluir(0));

		verify(marcaDaoHibernate).excluir(0);
	}

	@Test
	void deveTrazerUmaListaDeMarcasPersistidas() throws Exception {
		doReturn(lista).when(marcaDaoHibernate).listar();

		assertEquals(lista, marcaDaoHibernate.listar());

		verify(marcaDaoHibernate).listar();
	}

	@Test
	void deveRetornarNullSeNenhumaMarcaForEncontradoNaListagem() throws Exception {
		doReturn(null).when(marcaDaoHibernate).listar();

		assertNull(marcaDaoHibernate.listar());
	}
}
