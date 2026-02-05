package br.com.InternoProjeto.Logica;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class logicaPagamento implements ObserverCB {
	public float valorTotal = 0;
	private float taxa;
	private boolean chamada = false;
	private String textoCode;
	private displayLogica display;
	public boolean confirmaPagamento;
	private boolean codigoCerto = false;

	@Override
	public void UpdateAction(displayLogica display, tipoPagamento TPpage) throws WriterException, IOException {
		chamada = true;
		this.display = display;
		selecaoPagamento(TPpage);
	}

	private String entrada() {
		System.out.println("Inserir codigo do tiket:");
		if (textoCode == null || textoCode.trim().isEmpty()) {
			textoCode = JOptionPane.showInputDialog("Inserir codigo do tiket:");
		}
		textoCode = textoCode.trim();
		return textoCode;

	}

	private void selecaoPagamento(tipoPagamento TPpage) throws WriterException, IOException {
		chamada = true;
		switch (TPpage) {
		case CREDITO:
		case DEBITO:
			verificarCodigo();
			break;
		case PIX:
			gerarQRCode();
			break;
		default:
			System.out.println("Pagamento Invalido!");
			break;

		}
	}

	private void verificarCodigo() {
		// iniciar metodos com valores null para cada nova chamada
		codigoCerto = false;
		textoCode = null;
		chamada = true;
		int cont = 0;
		int tent = 4;

		entrada();
		while (!codigoCerto) {
			try {
				if (display.valorCadastro.containsKey(textoCode)) {
					if (valorTotal == 0) {
						System.out.println("Não existe valor para ser pago");
					} else {
						System.out.printf(
								"O valor pago foi de R$: %.2f \nCadatro: %s liberado! " + "\nMuito Obrigado! ",
								valorTotal, textoCode);

						Thread contador = display.contadores.get(textoCode);
						display.contadores.remove(textoCode, contador);
						display.tempContador.remove(textoCode, display.tempContador.get(textoCode));
						display.valorCadastro.remove(textoCode, valorTotal);

						if (!display.contadores.containsKey(textoCode) && !display.contadores.containsValue(contador)) {
							System.out.println("\nValor removido: "+ textoCode);
						} else {
							System.out.println("Valores existem dentro de contadores");
						}
					}
					codigoCerto = true;
					return;
				}
			} catch (Exception e) {
				System.out.println("\n" + "!".repeat(35) + "\nEsse codigo não exite no sistema!\n" + "!".repeat(35));
			}
			cont++;
			if (cont < 4) {
				System.out.println("Código inválido");
				tent--;
				System.out.printf("Insira novamente: ( %d - Tentativas )\n", tent);

				textoCode = JOptionPane.showInputDialog("Insira novamente: (" + tent + "- Tentativas )");
			}
			if (cont == 3) {
				System.out.println("Tentativas excedidas!");
				codigoCerto = true;
				break;

			}
		}
	}

	private void gerarQRCode() throws WriterException, IOException {
		verificarCodigo();
		if (codigoCerto) {
			String texto = "https://www.instagram.com/coder_salles/";
			BitMatrix matrix = new QRCodeWriter().encode(texto, BarcodeFormat.QR_CODE, 200, 200);
			BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < 200; x++) {
				for (int y = 0; y < 200; y++) {
					image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
				}
			}
			ImageIO.write(image, "PNG", new File("qrcode.png"));
			System.out.println("✅ QRCode gerado: qrcode.png");
		}
	}

	/**
	 * Para fins de teste: MINUTOS >> RefSegundo: 30min >> 10seg, 60min >> 20seg,
	 * +120min >> 30seg
	 * 
	 */

	public float calcTempValor(float tempo) {
		if (tempo <= 10) {
			taxa = 0.3F;
			valorTotal = taxa * tempo;
		} else if (tempo <= 20) {
			taxa = 0.5F;
			valorTotal = taxa * tempo;
		} else if (tempo <= 30) {
			taxa = 1.2F;
			valorTotal = taxa * tempo;
		} else if (tempo > 30) {
			taxa = 1.8F;
			valorTotal = taxa * tempo;
		}
		chamada = true;
		return valorTotal;

	}

	public boolean isChamada() {
		return chamada;
	}

}
