package br.senac.sp.projetopoo.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
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

class VeiculoDaoHibernateTest {

	// Atributos para os testes da dao do Veículo
	private VeiculoDaoHibernate veiculoDaoHibernate;
	private Veiculo veiculo;
	private List<Veiculo> lista;

	/*
	 * @BeforeAll static void setUpBeforeClass() throws Exception { }
	 * 
	 * @AfterAll static void tearDownAfterClass() throws Exception { }
	 */

	@BeforeEach
	void setUp() throws Exception {
		veiculoDaoHibernate = Mockito.mock(VeiculoDaoHibernate.class);
		veiculo = new Veiculo();
		lista = new ArrayList<Veiculo>();
		
		// Atributos
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

	@AfterEach
	void tearDown() throws Exception {
		veiculoDaoHibernate = null;
		veiculo = null;
		lista = null;
	}

	@Test
	void deveRetornar1QuandoPersistirUmVeiculoValido() throws Exception {
		Mockito.when(veiculoDaoHibernate.inserir(veiculo)).thenReturn(1);
		int resultado = veiculoDaoHibernate.inserir(veiculo);
		assertEquals(1, resultado);
		Mockito.verify(veiculoDaoHibernate).inserir(veiculo);
	}

	@Test
	void naoDeveInserirVeiculoInvalidoEntaoRetorna0() throws Exception {
		Mockito.when(veiculoDaoHibernate.inserir(null)).thenReturn(0);
		int resultado = veiculoDaoHibernate.inserir(null);
		assertEquals(0, resultado);
		Mockito.verify(veiculoDaoHibernate).inserir(null);
	}

	@Test
	void deveRetornar1QuandoBuscarUmVeiculoExistentePorUmId() throws Exception {
		Mockito.when(veiculoDaoHibernate.buscar(1)).thenReturn(veiculo);
		assertNotNull(veiculoDaoHibernate.buscar(1));
		Mockito.verify(veiculoDaoHibernate).buscar(1);
	}

	@Test
	void deveRetornar0QuandoBuscarUmVeiculoNãoExistentePorUmId() throws Exception {
		Mockito.when(veiculoDaoHibernate.buscar(0)).thenReturn(null);
		assertNull(veiculoDaoHibernate.buscar(0));
		Mockito.verify(veiculoDaoHibernate).buscar(0);
	}
	
	@Test
	void deveRetornarUmVeiculoQuandoBuscarUmVeiculoNãoExistentePorNome() throws Exception {
		Mockito.when(veiculoDaoHibernate.buscar("Veiculo A")).thenReturn(veiculo);
		assertEquals(veiculo, veiculoDaoHibernate.buscar("Veiculo A"));
		Mockito.verify(veiculoDaoHibernate).buscar("Veiculo A");
	}
	
	@Test
	void deveRetornarNullQuandoBuscarUmVeiculoNãoExistentePorNome() throws Exception {
		Mockito.when(veiculoDaoHibernate.buscar("Veiculo B")).thenReturn(null);
		assertNull(veiculoDaoHibernate.buscar("Veiculo B"));
		Mockito.verify(veiculoDaoHibernate).buscar("Veiculo B");
	}
	
	@Test
	void deveRetornar1QuandoAlterarUmVeiculo() throws Exception {
		Mockito.when(veiculoDaoHibernate.alterar(veiculo)).thenReturn(1);
		assertEquals(1, veiculoDaoHibernate.alterar(veiculo));
		Mockito.verify(veiculoDaoHibernate).alterar(veiculo);
	}
	
	@Test
	void deveRetornar0QuandoNãoAlterarUmVeiculo() throws Exception {
		Mockito.when(veiculoDaoHibernate.alterar(null)).thenReturn(0);
		assertEquals(0, veiculoDaoHibernate.alterar(null));
		Mockito.verify(veiculoDaoHibernate).alterar(null);
	}
	
	@Test
	void deveRemoverUmVeiculoComIdValidoERetornar1() throws Exception {
		Mockito.when(veiculoDaoHibernate.excluir(1)).thenReturn(1);
		assertEquals(1, veiculoDaoHibernate.excluir(1));
		Mockito.verify(veiculoDaoHibernate).excluir(1);
	}
	
	@Test
	void naoDeveRemoverUmVeiculoERetorna0() throws Exception {
		Mockito.when(veiculoDaoHibernate.excluir(0)).thenReturn(0);
		assertEquals(0, veiculoDaoHibernate.excluir(0));
		Mockito.verify(veiculoDaoHibernate).excluir(0);
	}
	
	@Test
	void deveTrazerUmaListaDeVeiculosPersistidos() throws Exception {
		lista.add(veiculo);
		
		Mockito.when(veiculoDaoHibernate.listar()).thenReturn(lista);
		assertEquals(lista, veiculoDaoHibernate.listar());
		Mockito.verify(veiculoDaoHibernate).listar();
	}
	
	@Test
	void deveRetornarNullSeNenhumVeiculoForEncontradoNaListagem() throws Exception {
		Mockito.when(veiculoDaoHibernate.listar()).thenReturn(null);
		assertEquals(null, veiculoDaoHibernate.listar());
		Mockito.verify(veiculoDaoHibernate).listar();
	}
}
