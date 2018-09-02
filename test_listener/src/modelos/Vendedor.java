package modelos;

import java.math.BigDecimal;

public class Vendedor {
	
	private Integer id;
	
	private String cpf;
	
	private String nome;
	
	private BigDecimal salario;
	
	public Vendedor(Integer id, String cpf, String nome, BigDecimal salario) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.salario = salario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		return id + ", " + nome + ", " + cpf + ", " + salario;
	}
	
}
