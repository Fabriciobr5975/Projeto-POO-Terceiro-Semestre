package br.senac.sp.projetopoo.modelo.enums;

/**
 * Enum contendo os tipos de direção que os carros podem ter, por exemplo a
 * Direção Elétrica
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public enum DirecaoVeiculo {
	// Enums
	MECANICA("Mecânica"), 
	HIDRAULICA("Hidráulica"), 
	ELETRICA("Elétrica"), 
	ELETRO_HIDRAULICA("Eletro-Hidráulica");

	// Atributo para o tipo de direção do véiculo
	private String tipo;

	/**
	 * Método contrutor
	 * 
	 * @param tipo - Recebe o tipo da direção do veículo
	 */
	private DirecaoVeiculo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método para pegar a direção do veículo
	 * 
	 * @return Retorna a direção do veículo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para mudar o tipo da direção do veículo
	 * 
	 * @param tipo - Recebe a nova direção do veículo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
