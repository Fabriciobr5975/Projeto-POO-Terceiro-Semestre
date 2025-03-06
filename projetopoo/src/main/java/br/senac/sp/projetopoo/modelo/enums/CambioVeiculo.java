package br.senac.sp.projetopoo.modelo.enums;


/**
 * Enum contendo os tipos de câmbios que os carros podem ter
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 */
public enum CambioVeiculo {
	// Enums
	MANUAL("Manual"),
	AUTOMATICO("Automático"),
	AUTOMATIZADO("Automatizado"),
	CVT("CVT");
	
	// Atributo para o tipo do câmbio
	private String tipo;
	
	/**
	 * Método contrutor
	 * 
	 * @param tipo - Recebe o tipo do câmbio
	 */
	private CambioVeiculo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método para pegar o tipo do câmbio
	 * 
	 * @return Retorna o tipo do câmbio
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para mudar o tipo do câmbio
	 * 
	 * @param tipo - Recebe o novo tipo do câmbio
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
