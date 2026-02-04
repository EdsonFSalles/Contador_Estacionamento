package br.com.InternoProjeto.Logica;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class novoCadastro {
	private static final AtomicInteger contID = new AtomicInteger(0);
	private static final AtomicInteger codigoCadastro = new AtomicInteger(001);
	private int ID;
	private int codigo;
	
	
	public novoCadastro() {
		ID = gerarID();
		gerarCodigo();
	}
	
	private String gerarCodigo(){
		codigo = ThreadLocalRandom.current().nextInt(001, 9999);
		codigoCadastro.set(codigo);
		return String.valueOf(codigo);
	}
	
	private int gerarID() {
		return contID.incrementAndGet();
	}

	public int getID() {
		return ID;
	}

	public int getCodigo() {
		return codigo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		novoCadastro other = (novoCadastro) obj;
		return ID == other.ID && codigo == other.codigo;
	}
	
	
	
	
}
