package modelos;

public class Cliente {
	
	private Integer id;
	
	private String cnpj;
	
	private String name;
	
	private String area;

	public Cliente(Integer id, String cnpj, String name, String area) {
		super();
		this.id = id;
		this.cnpj = cnpj;
		this.name = name;
		this.area = area;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return id + ", " + name + ", " + cnpj + ", " + area;
	}

}
