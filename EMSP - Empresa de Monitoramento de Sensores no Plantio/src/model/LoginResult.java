package model;

public class LoginResult {
	private int id;
	private int farmId;
	private String name;
	private String farmName;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getFarmId() {
		return farmId;
	}
	
	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}
}
