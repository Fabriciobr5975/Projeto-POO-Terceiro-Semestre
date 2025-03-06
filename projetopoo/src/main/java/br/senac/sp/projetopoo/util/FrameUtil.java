package br.senac.sp.projetopoo.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtil {

	/**
	 * Método para colocar icones nos frames
	 * 
	 * @return Retorna o icone que foi passado para o frame
	 */
	public static Image receberImagemLogoFrame() {
		Image icon = Toolkit.getDefaultToolkit().getImage("");

		return icon;
	}

	/**
	 * Método para centralizar o frame na tela do dispositivo, pegando o tamanho e
	 * resolução da tela e realizando o ajuste
	 * 
	 * @param frame - Recebe o frame que está sendo usado para deixá-lo no centro da
	 *              tela
	 */
	public static void centralizarTela(JFrame frame) {
		// Pegando o tamanho da tela do dispositivo
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		// Pegando o tamanho do frame
		Dimension janela = frame.getSize();

		// Ajustando o tamanho do frame para se ajustar na tela
		if (janela.height > screen.height) {
			frame.setSize(janela.height, screen.height);
		}
		if (janela.width > screen.width) {
			frame.setSize(janela.width, screen.width);
		}
		
		// Colocando a posição para o frame ficar, no caso no centro da tela
		frame.setLocation((screen.width - janela.width) / 2, (screen.height - janela.height) / 2);
	}

}
