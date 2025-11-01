package model;

public class User {
    private int id;
    private String name;
    private UserGender gender;
    private String email;
    private String password;
    private String cpf;
    private String role;
    private Farm farm;

    public User(int id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserGender getGender() {
		return gender;
	}

	public void setGender(UserGender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public int getId() {
		return id;
	}

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O primeiro nome do usuário não pode ser vazio.");
        }

        if (email == null || email.isBlank() ||
                !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("O email do usuário é inválido.");
        }

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF não pode ser vazio.");
        }
    }

    @Override
    public String toString() {
        return name + " | " + email;
    }
}
