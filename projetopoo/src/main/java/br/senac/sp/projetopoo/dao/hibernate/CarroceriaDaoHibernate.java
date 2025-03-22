package br.senac.sp.projetopoo.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;

import br.senac.sp.projetopoo.modelo.Carroceria;
import br.senac.sp.projetopoo.modelo.enums.CarroceriasVeiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class CarroceriaDaoHibernate implements InterfaceDao<Carroceria> {
	// Atributo para relizar a conexão do Hibernate
	private EntityManager manager;

	/**
	 * Método construtor
	 * 
	 * @param manager - Recebe um novo EntityManager para fazer o Hibernate
	 *                funcionar e estabelecer a conexão com o banco de dados
	 */
	public CarroceriaDaoHibernate(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public int inserir(Carroceria objeto) throws Exception {
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
	public int alterar(Carroceria objeto) throws Exception {
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

		} catch (HibernateException ex) {
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
	public Carroceria buscar(int id) throws Exception {
		/*
		 * Buscando um objeto do tipo Carroceria dentro do banco de dados, passando o
		 * seu ID para realizar essa busca. Depois esse objeto é retornado para a sua
		 * manipulação dentro do sistema
		 */
		return this.manager.find(Carroceria.class, id);
	}
	
	public Carroceria buscar(CarroceriasVeiculo elemento) throws Exception {
		// Criando uma query para realizar a listagem a partir do nome de alguma carroceria
		TypedQuery<Carroceria> query = this.manager.createQuery(
				"select c from Carroceria c where c.carroceria = : elemento order by c.carroceria",
				Carroceria.class);
		// Colocando o parâmetro na query
		query.setParameter("elemento", elemento);

		// Retorna o resultado da query
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
			 * Criando um novo objeto do tipo Carroceria, e realizando um busca dentro do
			 * banco de dados, passando um id. Esse objeto tem que ser primeiro encontrado
			 * para poder ser excluído
			 */
			Carroceria categoria = buscar(id);

			// Início da transação
			this.manager.getTransaction().begin();
			/*
			 * Removendo os dados da Carroceria de dentro do banco de dados, passando o
			 * objeto que contém os dados da busca realizada anteriormente
			 */
			this.manager.remove(categoria);
			// Excluindo a marca, caso não haja erros
			this.manager.getTransaction().commit();

			// Mudando o resultado para 1
			resultado = 1;

		} catch (HibernateException ex) {
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
	public List<Carroceria> listar() throws Exception {
		/*
		 * Query para listar todas as carrocerias salvos dentro do banco de dados usando o
		 * JPQL (Java Persistence Query Language). Os dados são buscados no banco de
		 * dados dentro do TypedQuery, onde posteriormente será retornado uma lista de
		 * todos os elementos do tipo marca salvos.
		 */
		TypedQuery<Carroceria> query = this.manager.createQuery("select c from Carroceria c order by c.id",
				Carroceria.class);

		// Retorna o resultado da query
		return query.getResultList();
	}
}
