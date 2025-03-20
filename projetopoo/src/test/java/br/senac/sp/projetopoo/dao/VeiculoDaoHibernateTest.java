package br.senac.sp.projetopoo.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.senac.sp.projetopoo.dao.hibernate.VeiculoDaoHibernate;
import br.senac.sp.projetopoo.modelo.Carroceria;
import br.senac.sp.projetopoo.modelo.Marca;
import br.senac.sp.projetopoo.modelo.Veiculo;
import br.senac.sp.projetopoo.modelo.enums.CambioVeiculo;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import br.senac.sp.projetopoo.modelo.enums.DirecaoVeiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class VeiculoDaoHibernateTest {

	// Atributos para os testes da dao do Veículo
	private VeiculoDaoHibernate veiculoDaoHibernate;
	private Veiculo veiculo;
	private List<Veiculo> lista;
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
		veiculoDaoHibernate = Mockito.spy(new VeiculoDaoHibernate(entityManager));
		veiculo = new Veiculo();
		lista = new ArrayList<Veiculo>();

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

		lista.add(veiculo);
	}

	@AfterEach
	void tearDown() throws Exception {
		veiculoDaoHibernate = null;
		veiculo = null;
		lista = null;
	}

	@Test
	void deveInserirVeiculoValidoEntaoRetorna1() throws Exception {
		doReturn(1).when(veiculoDaoHibernate).inserir(veiculo);

		assertEquals(1, veiculoDaoHibernate.inserir(veiculo));

		verify(veiculoDaoHibernate).inserir(veiculo);
	}

	@Test
	void naoDeveInserirVeiculoInvalidoEntaoRetorna0() throws Exception {
		doReturn(0).when(veiculoDaoHibernate).inserir(veiculo);

		assertEquals(0, veiculoDaoHibernate.inserir(veiculo));

		verify(veiculoDaoHibernate).inserir(veiculo);
	}

	@Test
	void deveBuscarUmVeiculoExistentePorUmId() throws Exception {
		doReturn(veiculo).when(veiculoDaoHibernate).buscar(1);

		assertNotNull(veiculoDaoHibernate.buscar(1));

		verify(veiculoDaoHibernate).buscar(1);
	}

	@Test
	void deveRetornarNullQuandoBuscarUmVeiculoNãoExistentePorUmId() throws Exception {
		doReturn(null).when(veiculoDaoHibernate).buscar(0);

		assertNull(veiculoDaoHibernate.buscar(0));

		verify(veiculoDaoHibernate).buscar(0);
	}

	@Test
	void deveRetornarUmVeiculoQuandoBuscarPorNomeExistente() throws Exception {
		doReturn(veiculo).when(veiculoDaoHibernate).buscar("Veiculo A");

		assertNotNull(veiculoDaoHibernate.buscar("Veiculo A"));

		verify(veiculoDaoHibernate).buscar("Veiculo A");
	}

	@Test
	void deveRetornarNullQuandoBuscarUmVeiculoNãoExistentePorNome() throws Exception {
		doReturn(null).when(veiculoDaoHibernate).buscar("Veiculo B");

		assertNull(veiculoDaoHibernate.buscar("Veiculo B"));

		verify(veiculoDaoHibernate).buscar("Veiculo B");
	}

	@Test
	void deveRetornar1QuandoAlterarUmVeiculo() throws Exception {
		doReturn(1).when(veiculoDaoHibernate).alterar(veiculo);

		assertEquals(1, veiculoDaoHibernate.alterar(veiculo));

		verify(veiculoDaoHibernate).alterar(veiculo);
	}

	@Test
	void deveRetornar0QuandoNãoAlterarUmVeiculo() throws Exception {
		doReturn(0).when(veiculoDaoHibernate).alterar(veiculo);

		assertEquals(0, veiculoDaoHibernate.alterar(veiculo));

		verify(veiculoDaoHibernate).alterar(veiculo);
	}

	@Test
	void deveRemoverUmVeiculoComIdValidoERetornar1() throws Exception {
		doReturn(1).when(veiculoDaoHibernate).excluir(1);

		assertEquals(1, veiculoDaoHibernate.excluir(1));

		verify(veiculoDaoHibernate).excluir(1);
	}

	@Test
	void naoDeveRemoverUmVeiculoInvalido() throws Exception {
		doReturn(0).when(veiculoDaoHibernate).excluir(0);

		assertEquals(0, veiculoDaoHibernate.excluir(0));

		verify(veiculoDaoHibernate).excluir(0);
	}

	@Test
	void deveTrazerUmaListaDeVeiculosPersistidos() throws Exception {
		doReturn(lista).when(veiculoDaoHibernate).listar();

		assertEquals(lista, veiculoDaoHibernate.listar());

		verify(veiculoDaoHibernate).listar();
	}

	@Test
	void deveRetornarNullSeNenhumVeiculoForEncontradoNaListagem() throws Exception {
		doReturn(null).when(veiculoDaoHibernate).listar();

		assertNull(veiculoDaoHibernate.listar());

		verify(veiculoDaoHibernate).listar();
	}
}
