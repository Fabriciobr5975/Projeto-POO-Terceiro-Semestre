package br.senac.sp.projetopoo.dao.hibernate;

import java.time.LocalDate;

import java.util.List;

import br.senac.sp.projetopoo.modelo.Veiculo;
import br.senac.sp.projetopoo.modelo.enums.CambioVeiculo;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import br.senac.sp.projetopoo.modelo.enums.DirecaoVeiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class VeiculoDaoHibernate implements InterfaceDao<Veiculo> {
	// Atributo para relizar a conexão do Hibernate
	private EntityManager manager;

	/**
	 * Método construtor
	 * 
	 * @param manager - Recebe um novo EntityManager para fazer o Hibernate
	 *                funcionar e estabelecer a conexão com o banco de dados
	 */
	public VeiculoDaoHibernate(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public int inserir(Veiculo objeto) throws Exception {
		/*
		 * Resultado da transação. Se for igual a 1 os dados foram persistidos com
		 * sucesso, se o resultado for igual a 0 os dados não foram persistidos.
		 */
		int resultado = 0;

		try {
			// Início da transação
			this.manager.getTransaction().begin();
			// Persistindo no banco de dados o objeto que o método recebeu
			this.manager.persist(objeto);
			// Realizando a inserção, caso não haja erros
			this.manager.getTransaction().commit();

			// Mudando o resultado para 1
			resultado = 1;

		} catch (Exception ex) {
			// Se a transação não foi concluida, então os dados não foram persistidos
			if (this.manager.getTransaction().isActive()) {
				this.manager.getTransaction().rollback();
			}
			// retorna 0
			return 0;
		}
		// Retorna o resultado da persistencia nos dados
		return resultado;
	}

	@Override
	public int alterar(Veiculo objeto) throws Exception {
		/*
		 * Resultado da transação. Se for igual a 1 os dados foram persistidos com
		 * sucesso, se o resultado for igual a 0 os dados não foram persistidos.
		 */
		int resultado = 0;

		try {
			// Início da transação
			this.manager.getTransaction().begin();
			// Persistindo no banco de dados o objeto que o método recebeu
			this.manager.merge(objeto);
			// Realizando a inserção, caso não haja erros
			this.manager.getTransaction().commit();

			// Mudando o resultado para 1
			resultado = 1;

		} catch (Exception ex) {
			// Se a transação não foi concluida, então os dados não foram persistidos
			if (this.manager.getTransaction().isActive()) {
				this.manager.getTransaction().rollback();
			}
			// retorna 0
			return 0;
		}
		// Retorna o resultado da persistencia nos dados
		return resultado;
	}

	@Override
	public Veiculo buscar(int id) throws Exception {
		/*
		 * Buscando um objeto do tipo Veiculo dentro do banco de dados, passando o seu
		 * ID para realizar essa busca. Depois esse objeto é retornado para a sua
		 * manipulação dentro do sistema
		 */
		return this.manager.find(Veiculo.class, id);
	}

	/**
	 * Método para buscar um Objeto do tipo Veiculo dentro do banco de dados,
	 * passando uma String referente ao seu nome
	 * 
	 * @param elemento - Recebe a String que contem o nome do objeto a ser buscado
	 * 
	 * @return Retorna o Objeto encontrado dentro do banco dados
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public Veiculo buscar(String elemento) throws Exception {
		/*
		 * Buscando um objeto do tipo Veiculo dentro do banco de dados, criando uma nova
		 * query e passando nessa query o parâmetro do método para buscar esse objeto
		 */
		TypedQuery<Veiculo> query = this.manager
				.createQuery("select v from Veiculo v where v.nome = : elemento order by v.nome", Veiculo.class);
		query.setParameter("elemento", elemento);

		return query.getSingleResult();
	}

	@Override
	public int excluir(int id) throws Exception {
		/*
		 * Resultado da transação. Se for igual a 1 os dados foram persistidos com
		 * sucesso, se o resultado for igual a 0 os dados não foram persistidos.
		 */
		int resultado = 0;

		try {
			/*
			 * Criando um novo objeto do tipo Veiculo, e realizando um busca dentro do banco
			 * de dados, passando um id. Esse objeto tem que ser primeiro encontrado para
			 * poder ser excluído
			 */
			Veiculo produto = buscar(id);

			// Início da transação
			this.manager.getTransaction().begin();
			/*
			 * Removendo os dados do Veiculo de dentro do banco de dados, passando o objeto
			 * que contém os dados da busca realizada anteriormente
			 */
			this.manager.remove(produto);
			// Excluindo a marca, caso não haja erros
			this.manager.getTransaction().commit();

			// Mudando o resultado para 1
			resultado = 1;

		} catch (Exception ex) {
			// Se a transação não foi concluida, então os dados não foram persistidos
			if (this.manager.getTransaction().isActive()) {
				this.manager.getTransaction().rollback();
			}
			// retorna 0
			return 0;
		}
		// Retorna o resultado da persistencia nos dados
		return resultado;
	}

	@Override
	public List<Veiculo> listar() throws Exception {
		/*
		 * Query para listar todas os veiculos salvos dentro do banco de dados usando o
		 * JPQL (Java Persistence Query Language). Os dados são buscados no banco de
		 * dados dentro do TypedQuery, onde posteriormente será retornado uma lista de
		 * todos os elementos do tipo marca salvos.
		 */
		TypedQuery<Veiculo> query = this.manager.createQuery("select v from Veiculo v order by v.nome", Veiculo.class);

		// Retorna o resultado da query
		return query.getResultList();
	}

	/**
	 * Método que faz a listagem dos objetos Veiculos salvos pelo id do Veículo
	 * 
	 * @param id - Recebe o id do Veiculo que deseja fazer a listagem
	 * 
	 * @return Retorna uma lista de Veiculos a partir do id passado
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public List<Veiculo> listarPorId(Long id) throws Exception {
		// Criando uma query para realizar a listagem a partir do id do Veículo
		TypedQuery<Veiculo> query = this.manager.createQuery("select v from Veiculo v where v.id = :id order by v.nome",
				Veiculo.class);
		// Colocando o parâmetro na query
		query.setParameter("id", id);

		// Retorna o resultado da query
		return query.getResultList();
	}

	/**
	 * Método que faz a listagem dos objetos Veiculos salvos pelo nome do Veículo
	 * 
	 * @param nome - Recebe o nome do Veiculo que deseja fazer a listagem
	 * 
	 * @return Retorna uma lista de Veiculos a partir do nome passado
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public List<Veiculo> listarPorNome(String nome) throws Exception {
		// Criando uma query para realizar a listagem a partir do nome do Veículo
		TypedQuery<Veiculo> query = this.manager
				.createQuery("select v from Veiculo v where v.nome = :nome order by v.nome", Veiculo.class);
		// Colocando o parâmetro na query
		query.setParameter("nome", nome);

		// Retorna o resultado da query
		return query.getResultList();
	}

	/**
	 * Método que faz a listagem dos objetos Veiculos salvos pela marca do Veículo
	 * 
	 * @param marca - Recebe a nome da marca do Veiculo que deseja fazer a listagem
	 * 
	 * @return Retorna uma lista de Veiculos a partir da marca passada
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public List<Veiculo> listarPorMarca(String marca) throws Exception {
		// Criando uma query para realizar a listagem a partir da marca do Veículo
		TypedQuery<Veiculo> query = this.manager
				.createQuery("select v from Veiculo v where v.marca.nome = :marca order by v.nome", Veiculo.class);
		// Colocando o parâmetro na query
		query.setParameter("marca", marca);

		// Retorna o resultado da query
		return query.getResultList();
	}

	/**
	 * Método que faz a listagem dos objetos Veiculos salvos pelo câmbio do Veículo
	 * 
	 * @param cambio - Recebe o câmbio do Veiculo que deseja fazer a listagem
	 * 
	 * @return Retorna uma lista de Veiculos a partir do câmbio passado
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public List<Veiculo> listarPorCambio(CambioVeiculo cambio) throws Exception {
		// Criando uma query para realizar a listagem a partir do câmbio do Veículo
		TypedQuery<Veiculo> query = this.manager
				.createQuery("select v from Veiculo v where v.cambio = :cambio order by v.nome", Veiculo.class);
		// Colocando o parâmetro na query
		query.setParameter("cambio", cambio);

		// Retorna o resultado da query
		return query.getResultList();
	}

	/**
	 * Método que faz a listagem dos objetos Veiculos salvos pela carroceria do
	 * Veículo
	 * 
	 * @param carroceria - Recebe a carroceria do Veiculo que deseja fazer a
	 *                   listagem
	 * 
	 * @return Retorna uma lista de Veiculos a partir da carroceria passado
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public List<Veiculo> listarPorCategoria(CarroceriasVeiculo carroceria) throws Exception {
		// Criando uma query para realizar a listagem a partir da carroceria do Veículo
		TypedQuery<Veiculo> query = this.manager
				.createQuery("select v from Veiculo v where v.carroceria.carroceria = :carroceria order by v.nome", Veiculo.class);
		// Colocando o parâmetro na query
		query.setParameter("carroceria", carroceria);

		// Retorna o resultado da query
		return query.getResultList();
	}

	/**
	 * Método que faz a listagem dos objetos Veiculos salvos pela direção do Veículo
	 * 
	 * @param direcao - Recebe a direção do Veiculo que deseja fazer a listagem
	 * 
	 * @return Retorna uma lista de Veiculos a partir da direção passado
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public List<Veiculo> listarPorDirecao(DirecaoVeiculo direcao) throws Exception {
		// Criando uma query para realizar a listagem a partir da direção do Veículo
		TypedQuery<Veiculo> query = this.manager
				.createQuery("select v from Veiculo v where v.direcao = :direcao order by v.nome", Veiculo.class);
		// Colocando o parâmetro na query
		query.setParameter("direcao", direcao);

		// Retorna o resultado da query
		return query.getResultList();
	}

	/**
	 * Método que faz a listagem dos objetos Veiculos salvos pelo preço do Veículo
	 * 
	 * @param preco - Recebe o preço do Veiculo que deseja fazer a listagem
	 * 
	 * @return Retorna uma lista de Veiculos a partir do preço passado
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public List<Veiculo> listarPorPreco(Double preco) throws Exception {
		// Criando uma query para realizar a listagem a partir do preço do Veículo
		TypedQuery<Veiculo> query = this.manager
				.createQuery("select v from Veiculo v where v.preco = :preco order by v.nome", Veiculo.class);
		// Colocando o parâmetro na query
		query.setParameter("preco", preco);

		// Retorna o resultado da query
		return query.getResultList();
	}

	/**
	 * Método que faz a listagem dos objetos Veiculos salvos pela data de fabricação
	 * do Veículo
	 * 
	 * @param dataFabricacao - Recebe a data de fabricação do Veiculo que deseja
	 *                       fazer a listagem
	 * 
	 * @return Retorna uma lista de Veiculos a partir da data de fabricação passado
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public List<Veiculo> listarPorDataFabricacao(LocalDate dataFabricacao) throws Exception {
		// Criando uma query para realizar a listagem a partir da data de fabricação do Veículo
		TypedQuery<Veiculo> query = this.manager.createQuery(
				"select v from Veiculo v where v.fabricacao = :dataFabricacao order by v.nome", Veiculo.class);
		// Colocando o parâmetro na query
		query.setParameter("dataFabricacao", dataFabricacao);

		// Retorna o resultado da query
		return query.getResultList();
	}

}
