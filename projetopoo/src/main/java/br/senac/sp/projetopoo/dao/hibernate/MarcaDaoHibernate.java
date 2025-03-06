package br.senac.sp.projetopoo.dao.hibernate;

import java.util.List;

import br.senac.sp.projetopoo.modelo.Marca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * Classe voltada para a manipulação do CRUD (Create - Read - Update - Delete)
 * para os objetos do tipo Marca, usando o hibernate juntamente com o JPA para
 * os métodos do CRUD
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public class MarcaDaoHibernate implements InterfaceDao<Marca> {
	// Atributo para relizar a conexão do Hibernate
	private EntityManager manager;

	/**
	 * Método construtor
	 * 
	 * @param manager - Recebe um novo EntityManager para fazer o Hibernate
	 *                funcionar e estabelecer a conexão com o banco de dados
	 */
	public MarcaDaoHibernate(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public int inserir(Marca objeto) throws Exception {
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
			// retorna 0;
			return 0;
		}
		// Retorna o resultado da persistencia nos dados
		return resultado;
	}

	@Override
	public int alterar(Marca objeto) throws Exception {
		/*
		 * Resultado da transação. Se for igual a 1 os dados foram persistidos com
		 * sucesso, se o resultado for igual a 0 os dados não foram persistidos.
		 */
		int resultado = 0;

		try {
			// Início da transação
			this.manager.getTransaction().begin();
			// Mudando os dados dentro do banco de dados do objeto passado pelo método
			this.manager.merge(objeto);
			// Realizando as alterações, caso não haja erros
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
	public Marca buscar(int id) throws Exception {
		/*
		 * Buscando um objeto do tipo marca dentro do banco de dados, passando o seu ID
		 * para realizar essa busca. Depois esse objeto é retornado para a sua
		 * manipulação dentro do sistema
		 */
		return this.manager.find(Marca.class, id);
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
			 * Criando um novo objeto do tipo marca, e realizando um busca dentro do banco
			 * de dados, passando um id. Esse objeto tem que ser primeiro encontrado para
			 * poder ser excluído
			 */
			Marca marca = buscar(id);

			// Início da transação
			this.manager.getTransaction().begin();
			/*
			 * Removendo os dados da marca de dentro do banco de dados, passando o objeto
			 * que contém os dados da busca realizada anteriormente
			 */
			this.manager.remove(marca);
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
	public List<Marca> listar() throws Exception {
		/*
		 * Query para listar todas as marcas salvas dentro do banco de dados usando o
		 * JPQL (Java Persistence Query Language). Os dados são buscados no banco de
		 * dados dentro do TypedQuery, onde posteriormente será retornado uma lista de
		 * todos os elementos do tipo marca salvos.
		 */
		TypedQuery<Marca> query = this.manager.createQuery("select m from Marca m order by m.nome", Marca.class);

		// Retorna a lista com todos as marcas salvas, ordenadas pelo nome da marca
		return query.getResultList();
	}

}
