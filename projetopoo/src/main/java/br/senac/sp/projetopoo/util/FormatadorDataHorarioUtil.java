package br.senac.sp.projetopoo.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para o tratameto da formatação de datas e dados referentes
 * a horários (horas, minutos e segundos). Essa classe deverá ser usada para
 * formatar a saida dos elementos de data no padrão (Dia: Mês: Ano) e na parte
 * dos horários no padrão (Hora: Minuto: Segundo).
 * 
 * @author Fabrício de Araújo Santana
 * @author Marcus Vinícius Pereira Rocha
 * @author Ruan Lopes Viana
 * 
 */
public class FormatadorDataHorarioUtil {
	
	/**
	 * Método que pega a hora atual do sistema e formata no padrão (HH:mm:ss) que
	 * significa (hora:minutos:segundos)
	 * 
	 * @return Retorna uma String contendo o horário atual do sistema formatado
	 */
	public static String lerHoraAtualDoSistema() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(0);
		return dateFormat.format(date);
	}

	/**
	 * Método que pegar a data atual do sistema e a formata no padrõa (dd/MM/yyyy)
	 * que significa (dia:mês:ano)
	 * 
	 * @return Retorna uma String contendo a data atual do sistema formatada
	 */
	public static String lerDataAtualDoSistema() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date(0);
		return dateFormat.format(date);
	}

	/**
	 * Método para formatar uma data passada por parâmetro, afim de melhorar a
	 * visualização da data, quando a mesma for impressa. A data é formatada no
	 * padrão (dd:MM:yyy) que significa (dia:mês:ano)
	 * 
	 * @param data - Recebe uma data que será formatada
	 * 
	 * @return Retorna uma String contendo a data passada por parâmetro formatada
	 * 
	 * @throws Exception - Caso haja alguma exceção
	 */
	public static String formatarData(LocalDate data) throws Exception {
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
		return dataFormatada;
	}

}
