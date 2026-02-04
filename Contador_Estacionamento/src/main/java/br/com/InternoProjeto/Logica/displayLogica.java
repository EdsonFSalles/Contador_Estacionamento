package br.com.InternoProjeto.Logica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

import com.google.zxing.WriterException;

public class displayLogica {
	private logicaPagamento logicPag;
	private List<ObserverCB> observadores = new ArrayList<>();
	public ConcurrentHashMap<String, Thread> contadores = new ConcurrentHashMap<>();
	public Map<String, Float> tempContador = new HashMap<>();
	public Map<String, Float> valorCadastro = new HashMap<>();

	private boolean chamada = false;

	public displayLogica() {
		this.logicPag = new logicaPagamento();
		this.addObservador(logicPag);
	}

	private void addObservador(ObserverCB observador) {
		observadores.add(observador);
	}

	public void notificarObservador(tipoPagamento TPpage) {
		observadores.stream().forEach(ob -> {
			try {
				ob.UpdateAction(this, TPpage);
			} catch (WriterException | IOException e) {
				e.printStackTrace();
			}
		});
		chamada = true;
	}

	public void fazerPagamento(tipoPagamento TpPag) throws IOException, WriterException {
		notificarObservador(TpPag);
	}

	public List<ObserverCB> getObservadores() {
		return observadores;
	}

	public void setObservadores(List<ObserverCB> observadores) {
		this.observadores = observadores;
	}

	private Thread startContador(String codigo) {
		return new Thread(() -> {
			float timer = 0;
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(1000);
					timer++;
					System.out.println("Cont = " + timer);// Observar comportamento do timer
					tempContador.put(codigo, timer);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					tempContador.put(codigo, timer);
					break;
				}
			}
		});
	}

	public void iniciarTemp() {
		addCadastro();
	}

	private void addCadastro() {
		novoCadastro novoUsuario = new novoCadastro();
		String codigo = String.valueOf(novoUsuario.getCodigo());

		Thread contador = startContador(codigo);
		contadores.put(codigo, contador);
		contador.start();
		imprimirTiket(novoUsuario);
		chamada = true;
	}

	public void finalizarTemp() {
		String codigo = JOptionPane.showInputDialog("Insira codigo do Tiket");
		chamada = false;
		if (codigo == null || codigo.trim().isEmpty()) {
			return;
		}
		codigo = codigo.trim();
		boolean validou = false;
		while (!validou) {

			if (contadores.containsKey(codigo)) {
				Thread contador = contadores.get(codigo);
				if (contador != null) {
					imprimirValor(codigo);
					contador.interrupt();
					validou = true;
					chamada = true;
				}
			} else {
				System.out.println("Codigo invalido!");
				codigo = JOptionPane.showInputDialog("Insira codigo do Tiket");
				if (codigo == null || codigo.trim().isEmpty()) {
					return;
				}
				codigo = codigo.trim();
			}
		}
	}

	public void imprimirValor(String valor) {
		Float chaveValor = tempContador.get(valor);
		logicPag.calcTempValor(chaveValor);
		String valorFormat = String.format("%.2f", chaveValor);
		valorCadastro.put(valor, chaveValor);
		String hifens = "-".repeat(22);

		String textoValor = hifens + "\nUsuario cadastrado: " + valor + "\nTempo estacionado: " + valorFormat
				+ "\nValor total: " + logicPag.valorTotal + "\n" + "Selecione forma de \npagamento \n" + hifens;
		System.out.println(textoValor);

	}

	private void imprimirTiket(novoCadastro cadastro) {
		String idCode = String.valueOf(cadastro.getCodigo());
		String hifens = "-".repeat(22);

		String textoTiket = hifens + "\n  Cadastro realizado!" + "\n         " + idCode + "\n" + hifens
				+ "\n  !!!! ATENÇÃO !!!!" + "\n Use esse codigo para \n" + "finalizar seu passeio \n" + hifens;
		System.out.println(textoTiket);

	}

	public boolean isChamada() {
		return chamada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contadores, observadores, valorCadastro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		displayLogica other = (displayLogica) obj;
		return Objects.equals(contadores, other.contadores) && Objects.equals(valorCadastro, other.valorCadastro)
				&& Objects.equals(observadores, other.observadores);
	}

}
