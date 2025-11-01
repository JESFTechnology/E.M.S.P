package model;

import java.sql.Timestamp;

import model.data.ConverterData;

public class Alerts {
	private int id;
	private Sensors sensor;
	private String message;
	private String level;
	private Timestamp created_at;
	private boolean resolved;
	
	public Alerts(int id) {
		this.id = id;
	}

	public Sensors getSensor() {
		return sensor;
	}

	public void setSensor(Sensors sensor) {
		this.sensor = sensor;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}
	
	public String getCreated_at_Formated() {
		ConverterData converterData = new ConverterData();
		return converterData.DateToString(created_at);
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public int getId() {
		return id;
	}
	
	public void validate() {
	    if (id <= -1) {
	        throw new IllegalArgumentException("O ID do alerta deve ser maior que zero.");
	    }

	    if (sensor == null || sensor.getId() <= -1) {
	        throw new IllegalArgumentException("Sensor inválido ou não informado para o alerta.");
	    }

	    if (message == null || message.isBlank()) {
	        throw new IllegalArgumentException("A mensagem do alerta não pode ser vazia.");
	    }

	    if (level == null || level.isBlank()) {
	        throw new IllegalArgumentException("O nível do alerta não pode ser vazio.");
	    }

	    // Níveis esperados (pode ajustar conforme teu sistema)
	    String[] validLevels = {"Baixo", "Médio", "Alto", "Evacuação"};
	    boolean valid = false;
	    for (String l : validLevels) {
	        if (l.equalsIgnoreCase(level)) {
	            valid = true;
	            break;
	        }
	    }
	    if (!valid) {
	        throw new IllegalArgumentException("Nível de alerta inválido. Use: INFO, WARNING, CRITICAL ou ERROR.");
	    }

	    // Nenhuma validação sobre 'created_at' pois pode ser definido automaticamente no banco
	}

	
	public String toString(){
		return "Sensor "+getId()+" registrou -> "+getMessage()+" em "+DateConverter.getConvertDatetime(getCreated_at());
	}
}
