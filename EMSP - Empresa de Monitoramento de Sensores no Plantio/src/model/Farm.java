package model;

public class Farm {
	private int id;
	private String name;
	private String cnpj;
	private String location;
	
	public Farm(int id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getId() {
		return id;
	}
	
	public void validate() {
	    if (id <= -1) {
	        throw new IllegalArgumentException("O ID da fazenda deve ser maior que -1.");
	    }

	    if (name == null || name.isBlank()) {
	        throw new IllegalArgumentException("O nome da fazenda não pode ser vazio.");
	    }

	    if (cnpj == null || cnpj.isBlank()) {
	        throw new IllegalArgumentException("O CNPJ da fazenda não pode ser vazio.");
	    }

	    // Remove todos os caracteres não numéricos e verifica se restam exatamente 14 dígitos
	    if (cnpj == null || cnpj.isBlank()) {
	        throw new IllegalArgumentException("O CNPJ deve conter exatamente 14 dígitos numéricos.");
	    }

	    if (location == null || location.isBlank()) {
	        throw new IllegalArgumentException("A localização da fazenda não pode ser vazia.");
	    }
	}
	
	@Override
    public String toString() {
        return name;
    }
}
