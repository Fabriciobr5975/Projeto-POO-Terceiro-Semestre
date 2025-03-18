package br.senac.sp.projetopoo.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.senac.sp.projetopoo.modelo.Veiculo;

@SuppressWarnings("serial")
public final class ValidacaoTelaProduto extends JFrame {

	public static LocalDate pegarDataFabricacao(JFormattedTextField campo) throws Exception {
		String data = campo.getText().replaceAll("/", "");
		int dia, mes, ano;

		dia = Integer.parseInt(data.substring(1, 3));
		mes = Integer.parseInt(data.substring(3, 5));
		ano = Integer.parseInt(data.substring(5, 9));

		if (dia > 30 || dia < 1) {
			throw new Exception("Coloque um dia válido");
		}

		if (mes > 12 || mes < 1) {
			throw new Exception("Coloque um mês válido");
		}

		if (ano < 1885 || ano > LocalDate.now().getYear()) {
			throw new Exception("Coloque um ano válido");
		}

		return LocalDate.of(ano, mes, dia);
	}

	public static double pegarPreco(JFormattedTextField campo) throws Exception {
		String preco = "";

		try {
			preco = campo.getText().replaceAll("[R$ .]", "").replaceAll("[,]", ".");

			return (Double.parseDouble(preco));
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static void colocarImagemNoCampo(JLabel label, Veiculo produto) throws Exception {
		byte[] imagemByte = produto.getImagemVeiculo();

		try {
			if (imagemByte != null) {
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));

				if (bufferedImage != null) {
					Image image = bufferedImage.getScaledInstance(label.getWidth(), label.getHeight(),
							Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(image);
					label.setIcon(icon);

				} else {
					throw new Exception("Formato de imagem não suportado");
				}

			} else {
				throw new Exception("Imagem não encontrada");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void formatarDataFabricacaoBusca(JFormattedTextField campo, Veiculo produto) throws Exception {
		String data = produto.getFabricacao().toString().replaceAll("[-]", "");

		String dia = data.substring(6, 8);
		String mes = data.substring(4, 6);
		String ano = data.substring(0, 4);

		campo.setText(dia + mes + ano);
	}

	public static void formatarPrecoBusca(JFormattedTextField campo, Veiculo produto) throws Exception {
		String preco = Double.toString(produto.getPreco());
		String precoFormatadoCampo = "";

		while (precoFormatadoCampo.length() != (11 - preco.length())) {
			precoFormatadoCampo += "0";
		}

		precoFormatadoCampo += preco;

		campo.setText(precoFormatadoCampo);
	}
}
