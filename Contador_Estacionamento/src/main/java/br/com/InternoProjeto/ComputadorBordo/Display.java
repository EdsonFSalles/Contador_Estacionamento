package br.com.InternoProjeto.ComputadorBordo;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {

	public Display() {
		setTitle("Computador de Bordo");
		setBounds(200, 200, 250, 260);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		Controles controles = new Controles();
		add(controles);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Display();

	}

}
