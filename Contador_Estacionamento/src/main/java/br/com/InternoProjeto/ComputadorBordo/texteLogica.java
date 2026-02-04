package br.com.InternoProjeto.ComputadorBordo;

import java.io.IOException;

import com.google.zxing.WriterException;

import br.com.InternoProjeto.Logica.displayLogica;
import br.com.InternoProjeto.Logica.tipoPagamento;

public class texteLogica {
	public static void main(String[] args) throws InterruptedException, IOException, WriterException {
		displayLogica logica = new displayLogica();
		logica.iniciarTemp();
		logica.iniciarTemp();
		
		//testar sessoes
		Thread.sleep(3000);
		logica.finalizarTemp();// fecha Thread em 3 segundos
		logica.fazerPagamento(tipoPagamento.DEBITO);
		
		Thread.sleep(10000);
		logica.finalizarTemp();//fecha Thread em 10 segundos
		logica.fazerPagamento(tipoPagamento.PIX);
	}
}
