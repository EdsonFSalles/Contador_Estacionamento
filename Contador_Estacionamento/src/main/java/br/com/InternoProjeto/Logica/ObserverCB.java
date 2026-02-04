package br.com.InternoProjeto.Logica;

import java.io.IOException;

import com.google.zxing.WriterException;

@FunctionalInterface
public interface ObserverCB {
	
	public void UpdateAction(displayLogica display, tipoPagamento TPpage ) throws WriterException, IOException;
}
