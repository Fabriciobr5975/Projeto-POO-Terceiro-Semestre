package br.senac.sp.projetopoo.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
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
import br.senac.sp.projetopoo.dao.hibernate.MarcaDaoHibernate;
import br.senac.sp.projetopoo.dao.hibernate.VeiculoDaoHibernate;
import br.senac.sp.projetopoo.modelo.Carroceria;
import br.senac.sp.projetopoo.modelo.Marca;
import br.senac.sp.projetopoo.modelo.Veiculo;
import br.senac.sp.projetopoo.modelo.enums.CambioVeiculo;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import br.senac.sp.projetopoo.modelo.enums.DirecaoVeiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

class VeiculoDaoHibernateTest {

	private static VeiculoDaoHibernate veiculoDaoHibernate;
	private static MarcaDaoHibernate marcaDaoHibernate;
	private static CarroceriaDaoHibernate carroceriaDaoHibernate;

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	private Veiculo veiculo;
	private Marca marca;
	private Carroceria carroceria;
	private List<Veiculo> lista;

	private boolean veiculoInserido = false;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("senac-test");
		entityManager = entityManagerFactory.createEntityManager();
		veiculoDaoHibernate = Mockito.spy(new VeiculoDaoHibernate(entityManager));
		marcaDaoHibernate = Mockito.spy(new MarcaDaoHibernate(entityManager));
		carroceriaDaoHibernate = Mockito.spy(new CarroceriaDaoHibernate(entityManager));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		veiculoDaoHibernate = null;
		marcaDaoHibernate = null;
		carroceriaDaoHibernate = null;
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

		carroceria = new Carroceria();
		carroceria.setCarroceria(CarroceriasVeiculo.HATCH_COMPACTO);

		veiculo = new Veiculo();

		veiculo.setNome("Veículo A");
		veiculo.setInformacoes("Veículo com ótimo custo-benefício");
		veiculo.setPreco(50000.00);
		veiculo.setFabricacao(LocalDate.of(2022, 5, 10));
		veiculo.setImportacao(false);
		veiculo.setCambio(CambioVeiculo.MANUAL);
		veiculo.setDirecao(DirecaoVeiculo.HIDRAULICA);

		veiculo.setMarca(marca);
		veiculo.setCarroceria(carroceria);

		veiculo.setImagemVeiculo(new byte[] { 1, 2, 3, 4, 5 });

		lista = new ArrayList<Veiculo>(Arrays.asList(veiculo));
	}

	@AfterEach
	void tearDown() throws Exception {
		veiculo = null;
		marca = null;
		carroceria = null;
		lista = null;
	}

	private void inserirVeiculosParaTestes() throws Exception {
		if (!veiculoInserido) {
			veiculoDaoHibernate.inserir(veiculo);
		}

		veiculoInserido = true;
	}

	private void criarRegistroDeMarca() throws Exception {
		if (marcaDaoHibernate.listar().isEmpty()) {
			marcaDaoHibernate.inserir(marca);
		}
	}

	private void criarRegistroDeCarroceria() throws Exception {
		if (carroceriaDaoHibernate.listar().isEmpty()) {
			carroceriaDaoHibernate.inserir(carroceria);
		}
	}

	@Test
	void deveInserirVeiculoValidoEntaoRetorna1() throws Exception {
		criarRegistroDeMarca();
		criarRegistroDeCarroceria();

		int resultado = veiculoDaoHibernate.inserir(veiculo);
		assertEquals(1, resultado);

		verify(veiculoDaoHibernate).inserir(veiculo);
	}

	@Test
	void naoDeveInserirVeiculoInvalidoEntaoRetorna0() throws Exception {
		veiculo.setNome("Carro Teste");

		int resultado = veiculoDaoHibernate.inserir(veiculo);
		assertEquals(0, resultado);

		verify(veiculoDaoHibernate).inserir(veiculo);
	}

	@Test
	void naoDeveInserirVeiculoNullEntaoRetorna0() throws Exception {
		int resultado = veiculoDaoHibernate.inserir(null);
		assertEquals(0, resultado);

		verify(veiculoDaoHibernate).inserir(null);
	}

	@Test
	void naoDeveInserirVeiculoFaltandoParametrosEntaoRetorna0() throws Exception {
		veiculo.setNome(null);

		int resultado = veiculoDaoHibernate.inserir(veiculo);
		assertEquals(0, resultado);

		verify(veiculoDaoHibernate).inserir(veiculo);
	}

	@Test
	void deveBuscarUmVeiculoExistentePorUmId() throws Exception {
		inserirVeiculosParaTestes();

		Veiculo veiculo2 = veiculoDaoHibernate.buscar(1);
		assertNotNull(veiculo2);

		verify(veiculoDaoHibernate).buscar(1);
	}

	@Test
	void deveRetornarNullQuandoBuscarUmVeiculoNãoExistentePorUmId() throws Exception {
		inserirVeiculosParaTestes();

		veiculo = veiculoDaoHibernate.buscar(0);
		assertNull(veiculoDaoHibernate.buscar(0));
	}

	@Test
	void deveRetornarUmVeiculoQuandoBuscarPorNomeExistente() throws Exception {
		inserirVeiculosParaTestes();

		assertThrows(NoResultException.class, () -> {
			veiculo = veiculoDaoHibernate.buscar("Veiculo A");
			assertNotNull(veiculoDaoHibernate.buscar("Veiculo A"));
		});

		verify(veiculoDaoHibernate).buscar("Veiculo A");
	}

	@Test
	void deveRetornarNullQuandoBuscarUmVeiculoNãoExistentePorNome() throws Exception {
		inserirVeiculosParaTestes();

		assertThrows(NoResultException.class, () -> {
			veiculo = veiculoDaoHibernate.buscar("Veiculo B");
			assertNotNull(veiculoDaoHibernate.buscar("Veiculo B"));
		});

		verify(veiculoDaoHibernate).buscar("Veiculo B");
	}

	@Test
	void deveRetornar1QuandoAlterarUmVeiculo() throws Exception {
		criarRegistroDeMarca();
		criarRegistroDeCarroceria();

		inserirVeiculosParaTestes();

		int resultado = veiculoDaoHibernate.alterar(veiculo);
		assertEquals(1, resultado);

		verify(veiculoDaoHibernate).alterar(veiculo);
	}

	@Test
	void deveRetornar0QuandoNãoAlterarUmVeiculo() throws Exception {
		inserirVeiculosParaTestes();

		int resultado = veiculoDaoHibernate.alterar(veiculo);
		assertEquals(0, resultado);
	}

	@Test
	void deveRemoverUmVeiculoComIdValidoERetornar1() throws Exception {
		inserirVeiculosParaTestes();

		int resultado = veiculoDaoHibernate.excluir(1);
		assertEquals(1, resultado);

		verify(veiculoDaoHibernate).excluir(1);
	}

	@Test
	void naoDeveRemoverUmVeiculoInvalido() throws Exception {
		inserirVeiculosParaTestes();

		int resultado = veiculoDaoHibernate.excluir(0);
		assertEquals(0, resultado);

		verify(veiculoDaoHibernate).excluir(0);
	}

	@Test
	void deveTrazerUmaListaDeVeiculosPersistidos() throws Exception {
		inserirVeiculosParaTestes();

		List<Veiculo> lista2 = veiculoDaoHibernate.listar();
		assertEquals(lista2, lista);

		verify(veiculoDaoHibernate).listar();
	}

	@Test
	void deveRetornarNullSeNenhumVeiculoForEncontradoNaListagem() throws Exception {
		List<Veiculo> lista2 = veiculoDaoHibernate.listar();
		assertTrue(lista2.isEmpty());

		verify(veiculoDaoHibernate).listar();
	}
}
