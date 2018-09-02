package modelos;

import java.math.BigDecimal;
import java.util.List;

public class Venda {

	private Integer id;
	
	private List<Item> items;
	
	private String nomeVendedor;

	public Venda(Integer id, List<Item> items, String nomeVendedor) {
		super();
		this.id = id;
		this.items = items;
		this.nomeVendedor = nomeVendedor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}
	
	public BigDecimal getValorTotal() {
		return items.get(0).getPreco().add(items.get(1).getPreco()).add(items.get(2).getPreco());
	}
	
	@Override
	public String toString() {
		return id + ", " + items.get(0).getPreco().add(items.get(1).getPreco()).add(items.get(2).getPreco()) + ", " + nomeVendedor;
	}
	
}
