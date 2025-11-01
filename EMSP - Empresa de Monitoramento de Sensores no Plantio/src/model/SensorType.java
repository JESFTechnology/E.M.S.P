package model;

public class SensorType {
	private int id;
	private String name;
	private String unit;
	
	public SensorType(int id){
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getId() {
		return id;
	}

	public String getFormatText(float value){
		return value+unit;
	}
	
	public void validate() {
	    if (id <= -1) {
	        throw new IllegalArgumentException("O ID do tipo de sensor deve ser maior que -1.");
	    }

	    if (name == null || name.isBlank()) {
	        throw new IllegalArgumentException("O nome do tipo de sensor não pode ser vazio.");
	    }

	    if (unit == null || unit.isBlank()) {
	        throw new IllegalArgumentException("A unidade de medida do sensor não pode ser vazia.");
	    }

	    // Validação básica para unidade (aceita letras, °C, %, etc.)
	    if (!unit.matches("[a-zA-Z%°/]+")) {
	        throw new IllegalArgumentException("A unidade contém caracteres inválidos. Use apenas letras ou símbolos simples como %, °, /.");
	    }
	}

	@Override
	public String toString() {
		return name + " | " + unit;
	}
	
	
}
