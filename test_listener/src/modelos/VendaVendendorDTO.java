package modelos;

import java.math.BigDecimal;

public class VendaVendendorDTO {

	private BigDecimal valor;
	
	private String nomeVendedor;

	public VendaVendendorDTO(BigDecimal valor, String name) {
		super();
		this.valor = valor;
		this.nomeVendedor = name;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String name) {
		this.nomeVendedor = name;
	}
	
	
}
