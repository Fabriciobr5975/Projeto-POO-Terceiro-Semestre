package br.senac.sp.projetopoo.modelo.enums;

/**
 * Enum contendo as carrocerias que um veículo pode ter, ou seja o tipo de
 * carroceria que o veículo tem, com por exemplo Sedan, Hatch ou SUV
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public enum CarroceriasVeiculo {
	// Enums
	AVENTUREIRO_COMPACTO("Aventureiro Compacto"), 
	HATCH_SUBCOMPACTO("Hatch Subcompacto"),
	HATCH_COMPACTO("Hatch Compacto"), 
	HATCH_MEDIO("Hatch Médio"), 
	SEDA_COMPACTO("Sedã Compacto"),
	SEDA_MEDIO("Sedã Médio"), 
	SEDA_GRANDE("Sedã Grande"), 
	FAMILIAR_COMPACTO("Familiar Compacto"),
	FAMILIAR_MEDIO("Familiar Médio"), 
	FAMILIA_GRANDE("Familiar Grande"), 
	PICAPE_COMPACTO("Picape Compacta"),
	PICAPE_MEDIO("Picape Média"), 
	PICAPE_GRANDE("Picape Grande"), 
	SUV_COMPACTO("SUV Compacto"), 
	SUV_MEDIO("SUV Médio"),
	SUV_GRANDE("SUV Grande"), 
	ESPORTIVO_COMPACTO("Esportivo Compacto"), 
	ESPORIVO_MEDIO("Esportivo Médio"),
	ESPORTIVO_GRANDE("Esportivo Grande"), 
	CONVERSIVEL_COMPACTO("Conversível Compacto"),
	CONVERSIVEL_MEDIO("Conversível Médio"), 
	CONVERSIVEL_GRANDE("Conversível Grande"), 
	VAN_MEDIA("Van Média"),
	VAN_GRANDE("Van Grande"), 
	JIPE("Jipe"), 
	FURGAO_COMPACTO("Furgão Compacto"),
	FURGAO_MEDIO("Furgão Médio"),
	CAMINHAO_URBANO("Caminhão Urbano");

	// Atributo para o tipo da categoria
	private String tipo;

	/**
	 * Método construtor
	 * 
	 * @param tipo - Recebe o tipo da categoria do veículo
	 */
	private CarroceriasVeiculo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método para pegar a categoria do veículo
	 * 
	 * @return Retorna a cetegoria do veículo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para mudar o tipo da categoria do veículo
	 * 
	 * @param tipo - Recebe a nova categoria do veículo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
