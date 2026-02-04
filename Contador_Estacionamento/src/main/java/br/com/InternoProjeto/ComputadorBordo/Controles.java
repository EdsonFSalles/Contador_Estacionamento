package br.com.InternoProjeto.ComputadorBordo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.zxing.WriterException;

import br.com.InternoProjeto.Logica.displayLogica;
import br.com.InternoProjeto.Logica.tipoPagamento;

@SuppressWarnings("serial")
public class Controles extends JPanel {
	private displayLogica logica = new displayLogica();

	private final Color visor_fundo = new Color(99, 94, 100);
	private final Color font_visor = Color.WHITE;
	private final Color BT_Verd = new Color(13, 94, 0);
	private final Color BT_Verm = new Color(76, 0, 4);
	private final Color BT_Rodape = new Color(25, 24, 26);

	public Controles() {
		setLayout(null);
		setBounds(0, 0, 500, 225);
		setBackground(visor_fundo);
		iniciar();
	}

	private void iniciar() {
		JPanel principal = new JPanel();
		principal.setLayout(null);
		principal.setBounds(0, 0, 250, 225);
		principal.setBackground(visor_fundo);
		add(principal);

		addBotao("ENTRAR", 13, 0, 210, 100, BT_Verd, e -> {
			logica.iniciarTemp();
		}, principal, null);

		addBotao("SAIR", 13, 100, 210, 100, BT_Verm, e -> {
			logica.finalizarTemp();
			if(logica.isChamada()) {
				chamarPagamento();
			}
		}, principal, null);

		criarRodape(principal, null);

		setVisible(true);
	}

	private void chamarPagamento() {
		JDialog telaPag = new JDialog();
		telaPag.setLayout(null);
		telaPag.setBounds(560, 230, 250, 263); 
		telaPag.getContentPane().setBackground(visor_fundo);

		addBotao("PIX", 0, 100, 235, 100, BT_Rodape, e -> {
			try {
				logica.fazerPagamento(tipoPagamento.PIX);
				telaPag.dispose();
			} catch (IOException | WriterException e1) {
				e1.printStackTrace();
			}
		}, null, telaPag);

		addBotao("CREDITO", 0, 0, 117, 100, BT_Verm, e -> {
			try {
				logica.fazerPagamento(tipoPagamento.CREDITO);
				telaPag.dispose();
			} catch (IOException | WriterException e1) {
				e1.printStackTrace();
			}
		}, null, telaPag);

		addBotao("DEBITO", 117, 0, 117, 100, BT_Verd, e -> {
			try {
				logica.fazerPagamento(tipoPagamento.DEBITO);
				telaPag.dispose();
			} catch (IOException | WriterException e1) {
				e1.printStackTrace();
			}
		}, null, telaPag);

		criarRodape(null, telaPag);

		telaPag.setVisible(true);
	}

	private void addBotao(String nome, int x, int y, int largura, int altura, Color color, ActionListener a,
			JPanel page01, JDialog page02) {
		JButton botao = new JButton(nome);
		botao.setLayout(null);
		botao.setBounds(x, y, largura, altura);
		botao.setBackground(color);
		botao.addActionListener(a);
		if (page01 != null) {
			page01.add(botao);
		}
		if (page02 != null) {
			page02.add(botao);
		}
	}
	
	private void criarRodape(JPanel tela, JDialog sobTela) {
		JLabel rodape = new JLabel("@EdsonSalles - JAVA");
		rodape.setHorizontalAlignment(JLabel.CENTER);
		rodape.setBounds(0, 200, 250, 25);
		rodape.setFont(new Font("Arial", Font.BOLD, 16));
		rodape.setBackground(BT_Rodape);
		rodape.setOpaque(true);
		rodape.setForeground(font_visor);
		if(tela != null) {
			tela.add(rodape);
		}
		if(sobTela != null) {
			sobTela.add(rodape);
		}
	}

}
