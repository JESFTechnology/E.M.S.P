package model;
import java.sql.Timestamp;

import model.data.ConverterData;

public class Sensors {
	private int id;
	private String name;
	private SensorType sensorType;
	private Float last_value;
	private Timestamp last_update;
	private String device_model;
	private Float battery_level;
	private String status;
	private Timestamp installed_at;
	private Farm farm;
	
	public Sensors(int id){
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SensorType getSensorType() {
		return sensorType;
	}
	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}
	public Float getLast_value() {
		return last_value;
	}
	public void setLast_value(Float last_value) {
		this.last_value = last_value;
	}
	public Timestamp getLast_update() {
		return last_update;
	}
	public String getLast_updateFormated() {
		ConverterData converterData = new ConverterData();
		return converterData.DateToString(last_update);
	}
	public void setLast_update(Timestamp last_update) {
		this.last_update = last_update;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public Float getBattery_level() {
		return battery_level;
	}
	public void setBattery_level(Float battery_level) {
		this.battery_level = battery_level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getInstalled_at() {
		return installed_at;
	}
	public String getInstalled_atFormated() {
		ConverterData converterData = new ConverterData();
		return converterData.DateToString(installed_at);
	}
	public void setInstalled_at(Timestamp installed_at) {
		this.installed_at = installed_at;
	}
	public Farm getFarm() {
		return farm;
	}
	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	public int getId() {
		return id;
	}
	
	public void validate() {
		if (id <= -1) {
	        throw new IllegalArgumentException("O ID da fazenda deve ser maior que -1.");
	    }

	    if (name == null || name.isBlank()) {
	        throw new IllegalArgumentException("O nome do sensor não pode ser vazio.");
	    }
	    
	    if (status == null || name.isBlank()) {
			throw new IllegalArgumentException("O status não pode ser vazio.");
	    }
	    
	    if (farm == null) {
	    		throw new IllegalArgumentException("A fazenda não pode ser vazia.");
	    }
	}

	@Override
	public String toString() {
		return getDevice_model()+" | "+getSensorType().getUnit()+" | "+getFarm().getName();
	}
	
	
}
